preventMultSubmit(jQuery);
/**
	劫持jquery.event.dispatch方法
	给event添加一个lock和unlock方法用来防止重复提交
*/
function preventMultSubmit(jQuery) {
	var busyMap = {},
		originAdd = jQuery.event.add,
	// originDispatch = jQuery.event.dispatch,
		originHandles = jQuery.event.handlers;
	
	/*
	jQuery.event.dispatch = function(event) {
		var args = [].slice.call(arguments);
		
		event = args[0] = jQuery.event.fix( event );
		args.push(event);
		
		event.lock = function() { 
			var key = getJqKey(event.lock);
			busyMap[key] = true; 
			event.unlock = function() { busyMap[key] = false; }
		}
		originDispatch.apply(this, args)
	}
	*/
	
	jQuery.event.add = function( elem, types, handler, data, selector ) {
		var args = [].slice.call(arguments);
		var finalHanlder = handler;
		
		var newHandler = function() {
			var args = [].slice.call(arguments);
			var event = $.extend({}, args[0]);
			event.lock = function() { busyMap[newHandler.guid] = true; }
			event.unlock = function() { busyMap[newHandler.guid] = false; }
			args[0] = event;
			(handler.handler || handler).apply(this, args);
		}
		
		if (!handler.handler) {
			finalHanlder = newHandler;
		} else {
			finalHanlder.handler = newHandler;
		}
		
		args[2] = finalHanlder;
		originAdd.apply(this, args);
	}
	
	// 筛选缓存的handlerObj
	jQuery.event.handlers = function(event, handlers) {
		var newHandlers = [];
		for　(var i in handlers) {
			var cur = handlers[i];
			if (!/[0-9]+/.test(i)) {
				newHandlers[i] = cur;
			} else if (!busyMap[cur.guid]) {
				newHandlers.push(cur);
			}
		}
		return originHandles.call(this, event, newHandlers);
	}

	// 通过caller获取指定调用者
	function getJqKey(fn) {
		var topFn = fn,
			targetFn;
		while (topFn !== originDispatch) {
			targetFn = topFn;
			topFn = topFn.caller;
		}
		return targetFn.guid;
	}
}