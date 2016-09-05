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

public enum MongoDbCommand implements Command{
	select(new Consumer() {
		public String exec(MongoDataProvider provider) {
			FindIterable<Document> it = provider.getDb().find(
					MongoUtil.toBson(provider.getCondition()));
			List<Document> result = new ArrayList<Document>();
			it.<List<Document>> into(result);
			return result.toString();
		}
	}), 
	
	selectOne(new Consumer() {
		public String exec(MongoDataProvider provider) {
			FindIterable<Document> it = provider.getDb().find(
					MongoUtil.toBson(provider.getCondition()));
			return it.first() == null ? null : it.first().toJson();
		}
	}), 
	
	insert(new Consumer() {
		public String exec(MongoDataProvider provider) {
			for (Object o : provider.getEntitys())
				provider.getDb().insertOne(
						Document.parse(JSONObject.toJSONString(o)));
			return null;
		}
	}), 
	
	updateOne(new Consumer() {
		public String exec(MongoDataProvider provider) {
			UpdateResult result = provider.getDb().updateOne(
					MongoUtil.toBson(provider.getCondition()), 
					(Document) provider.getEntitys());
			return result.toString();
		}
	}),
	
	update(new Consumer() {
		public String exec(MongoDataProvider provider) {
			Object entity = null;
			List<Object> entityList = provider.getEntitys();
			if (entityList.size() > 0)
				entity = entityList.get(0);
			
			UpdateResult result = provider.getDb().updateMany(
					MongoUtil.toBson(provider.getCondition()), 
					MongoUtil.toUpdateBson(entity));
			
			return result.toString();
		}
	}),
	
	deleteOne(new Consumer() {
		public String exec(MongoDataProvider provider) {
			DeleteResult result = provider.getDb().deleteOne(
					MongoUtil.toBson(provider.getCondition()));
			return result.toString();
		}
	}),
	
	delete(new Consumer() {
		public String exec(MongoDataProvider provider) {
			DeleteResult result = provider.getDb().deleteMany(
					MongoUtil.toBson(provider.getCondition()));
			return result.toString();
		}
	});

	MongoDbCommand(Consumer c) {
		this.consumer = c;
	}
	
	private Consumer consumer;
	
	@Override
	public String exec(DataProvider provider) {
		if (!(provider instanceof MongoDataProvider))
			throw new RuntimeException("type error");
		
		return consumer.exec((MongoDataProvider) provider);
	}
	
	public static interface Consumer{
		String exec(MongoDataProvider provider); 
	}
}
