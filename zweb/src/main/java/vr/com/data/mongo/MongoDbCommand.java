package vr.com.data.mongo;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.alibaba.fastjson.JSONObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.DeleteResult;

import vr.com.data.Command;
import vr.com.data.Condition;
import vr.com.data.DataProvider;

public enum MongoDbCommand implements Command{
	select(new Consumer() {
		public String exec(MongoDataProvider provider) {
			MongoCollection<Document> db = provider.getDb();
			Condition condition = provider.getConditions();
			FindIterable<Document> it = db.find(condition.toBson());
			List<Document> result = new ArrayList<Document>();
			it.<List<Document>> into(result);
			return JSONObject.toJSONString(result);
		}
	}), 
	
	selectOne(new Consumer() {
		public String exec(MongoDataProvider provider) {
			MongoCollection<Document> db = provider.getDb();
			Condition condition = provider.getConditions();
			FindIterable<Document> it = db.find(condition.toBson());
			return JSONObject.toJSONString(it.first());
		}
	}), 
	
	insert(new Consumer() {
		public String exec(MongoDataProvider provider) {
			MongoCollection<Document> db = provider.getDb();
			for (Object o : provider.getEntitys())
				db.insertOne(Document.parse(JSONObject.toJSONString(o)));
			return "";
		}
	}), 
	
	updateOne(new Consumer() {
		public String exec(MongoDataProvider provider) {
			MongoCollection<Document> db = provider.getDb();
			Condition condition = provider.getConditions();
			return db.updateOne(condition.toBson(), (Document) provider.getEntitys()).toString();
		}
	}),
	
	updateMany(new Consumer() {
		public String exec(MongoDataProvider provider) {
			MongoCollection<Document> db = provider.getDb();
			Condition condition = provider.getConditions();
			return db.updateMany(condition.toBson(), (Document) provider.getEntitys()).toString();
		}
	}),
	
	deleteOne(new Consumer() {
		public String exec(MongoDataProvider provider) {
			MongoCollection<Document> db = provider.getDb();
			Condition condition = provider.getConditions();
			DeleteResult result = db.deleteOne(condition.toBson());
			return JSONObject.toJSONString(result);
		}
	}),
	
	delete(new Consumer() {
		public String exec(MongoDataProvider provider) {
			MongoCollection<Document> db = provider.getDb();
			Condition condition = provider.getConditions();
			DeleteResult result = db.deleteMany(condition.toBson());
			return JSONObject.toJSONString(result);
		}
	});

	MongoDbCommand(Consumer c) {
		this.consumer = c;
	}
	
	private Consumer consumer;
	
	@Override
	public String exec(DataProvider provider) {
		if (!(provider instanceof MongoDataProvider))
			throw new RuntimeException("");
		
		return consumer.exec((MongoDataProvider) provider);
	}
	
	public static interface Consumer{
		String exec(MongoDataProvider provider); 
	}
}
