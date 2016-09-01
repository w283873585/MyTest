package vr.com.data.mongo;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.alibaba.fastjson.JSONObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;

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
			return result.toString();
		}
	}), 
	
	selectOne(new Consumer() {
		public String exec(MongoDataProvider provider) {
			MongoCollection<Document> db = provider.getDb();
			Condition condition = provider.getConditions();
			FindIterable<Document> it = db.find(condition.toBson());
			return it.first() == null ? null : it.first().toJson();
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
			UpdateResult result = db.updateOne(condition.toBson(), (Document) provider.getEntitys());
			return result.toString();
		}
	}),
	
	updateMany(new Consumer() {
		public String exec(MongoDataProvider provider) {
			MongoCollection<Document> db = provider.getDb();
			Condition condition = provider.getConditions();
			UpdateResult result = db.updateMany(condition.toBson(), (Document) provider.getEntitys());
			return result.toString();
		}
	}),
	
	deleteOne(new Consumer() {
		public String exec(MongoDataProvider provider) {
			MongoCollection<Document> db = provider.getDb();
			Condition condition = provider.getConditions();
			DeleteResult result = db.deleteOne(condition.toBson());
			return result.toString();
		}
	}),
	
	delete(new Consumer() {
		public String exec(MongoDataProvider provider) {
			MongoCollection<Document> db = provider.getDb();
			Condition condition = provider.getConditions();
			DeleteResult result = db.deleteMany(condition.toBson());
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
			throw new RuntimeException("");
		
		return consumer.exec((MongoDataProvider) provider);
	}
	
	public static interface Consumer{
		String exec(MongoDataProvider provider); 
	}
}
