package vr.com.data.mongo;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.alibaba.fastjson.JSONObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;

import vr.com.data.Command;
import vr.com.data.DataProvider;
import vr.com.data.Result;

public enum MongoDbCommand implements Command{
	select(new Consumer() {
		public Result exec(MongoDataProvider provider) {
			return new MongoFindResult(select, provider);
		}
	}), 
	
	selectOne(new Consumer() {
		public Result exec(MongoDataProvider provider) {
			return new MongoFindResult(selectOne, provider);
		}
	}), 
	
	insert(new Consumer() {
		public Result exec(MongoDataProvider provider) {
			for (Object o : provider.getEntitys())
				provider.getDb().insertOne(
						Document.parse(JSONObject.toJSONString(o)));
			return new MongoInsertResult();
		}
	}), 
	
	update(new Consumer() {
		public Result exec(MongoDataProvider provider) {
			Object entity = null;
			List<Object> entityList = provider.getEntitys();
			if (entityList.size() > 0)
				entity = entityList.get(0);
			
			UpdateResult result = provider.getDb().updateMany(
					MongoUtil.toBson(provider.getCondition()), 
					MongoUtil.toUpdateBson(entity));
			
			return new MongoUpdateResult(result);
		}
	}),
	
	delete(new Consumer() {
		public Result exec(MongoDataProvider provider) {
			DeleteResult result = provider.getDb().deleteMany(
					MongoUtil.toBson(provider.getCondition()));
			return new MongoDeleteResult(result);
		}
	});

	MongoDbCommand(Consumer c) {
		this.consumer = c;
	}
	
	private Consumer consumer;
	
	@Override
	public Result exec(DataProvider provider) {
		if (!(provider instanceof MongoDataProvider))
			throw new RuntimeException("type error");
		
		return consumer.exec((MongoDataProvider) provider);
	}
	
	public static interface Consumer{
		Result exec(MongoDataProvider provider); 
	}
	
	public static class MongoFindResult implements Result{
		
		private MongoDbCommand command;
		private MongoDataProvider provider;
		
		public MongoFindResult(MongoDbCommand command, MongoDataProvider provider) {
			this.command = command;
			this.provider = provider;
		}

		@Override
		public <T> T getBean(Class<T> c) {
			if (command == MongoDbCommand.select) {
				throw new RuntimeException("expert one but get more");
			}
			FindIterable<T> it = provider.getDb().find(
					MongoUtil.toBson(provider.getCondition()), c);
			return it.first();
		}

		@Override
		public <T> List<T> getList(Class<T> c) {
			if (command == MongoDbCommand.selectOne) {
				throw new RuntimeException("can not change bean to list");
			}
			FindIterable<T> it = provider.getDb().find(
					MongoUtil.toBson(provider.getCondition()), c);
			List<T> result = new ArrayList<T>();
			it.into(result);
			return result;
		}

		@Override
		public boolean isSuccess() {
			return true;
		}
		
	}

	
	public static class MongoUpdateResult implements Result{
		
		private UpdateResult result;
		public MongoUpdateResult(UpdateResult result) { this.result = result; }
		@Override
		public <T> T getBean(Class<T> c) {
			return null;
		}

		@Override
		public <T> List<T> getList(Class<T> c) {
			return null;
		}

		@Override
		public boolean isSuccess() {
			return result.wasAcknowledged();
		}
	}
	
	
	public static class MongoInsertResult implements Result{

		@Override
		public <T> T getBean(Class<T> c) {
			return null;
		}

		@Override
		public <T> List<T> getList(Class<T> c) {
			return null;
		}

		@Override
		public boolean isSuccess() {
			return true;
		}
	}
	
	
	public static class MongoDeleteResult implements Result{

		private DeleteResult result;

		public MongoDeleteResult(DeleteResult result) {
			this.result = result;
		}
		
		@Override
		public <T> T getBean(Class<T> c) {
			return null;
		}

		@Override
		public <T> List<T> getList(Class<T> c) {
			return null;
		}

		@Override
		public boolean isSuccess() {
			return result.wasAcknowledged();
		}
	}
}
