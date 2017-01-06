	function toJsonHtml(obj) {
		var html = "",
			gen = getGenerator();
		
		while (gen.hasNext()) {
			html += gen.next() + getNextLine();
		}
		return html;
		
		
		function getGenerator() {
			var count = 0,
				source = [obj],
				keys = initializeKey();
			
			return {
				next: next,
				hasNext: hasNext
			};
			
			function next() {
				var key = keys.shift();
				var val = source[source.length - 1][key];
				
				if (val === undefined) {
					if (isStart(key)) {	// for "{" or "["
						count++;
						return key;
					} else if (isEnd(key)){	// for "]" or "}"
						count--;
						source.pop();
						return getPlaceholder() + key + getCutOff();
					} else {	// for "[]" or "{}"
						source.pop();
						return key + getCutOff();
					}
				}
				
				if (isNormal(val)) {
					return getPlaceholder() + format(key, true) + format(val) + getCutOff();
				} else {
					var theKey = getPlaceholder() + format(key, true);
					source.push(val);
					initializeKey();
					return theKey + next();
				}
			}
			
			function hasNext() {
				return keys.length || source.length;
			}
			
			function isStart(key) {
				return key == "{" || key == "[";
			}
			
			function isEnd(key) {
				return key == "}" || key == "]";
			}
			
			function getCutOff() {
				var next = keys[0];
				return (next !== undefined && !isEnd(next)) ? "," : "";
			}
			
			function isNormal(val) {
				return typeof val === "string" 
					|| typeof val === "number" 
					|| val == null;
			}
			
			function initializeKey() {
				var arr = getKeys(source[source.length - 1]);
				if (!keys) 
					keys = arr;
				else 
					[].splice.apply(keys, [0, 0].concat(arr));
				return keys;
			}
			
			function getKeys(obj) {
				var keys = [],
					isArr = isArray(obj);
				
				for (var key in obj)
					keys.push(key)
				
				if (!keys.length) {
					keys.push(isArr ? "[]" : "{}")
				} else {
					keys.unshift(isArr ? "[" : "{");
					keys.push(isArr ? "]" : "}");
				}
				return keys;
			}
			
			function isArray(obj) {
				return Array.isArray(obj);
			}
			
			function format(str, isKey) {
				if (isKey && isArray(source[source.length - 1]))
					return "";
				
				if (isKey)
					return '"' + str + '"' + ": "
				
				if (typeof str == 'string')
					return '"' + str + '"';
				
				return str;
			}
			
			function getPlaceholder() {
				var result = "";
				for (var i = 0; i < count; i++) {
					result += "&nbsp;&nbsp;&nbsp;&nbsp;";
				}
				return result;
			}
		}
		
		function getNextLine() {
			return "<br>";
		}
	}