package vr.com.data.mongo;

import java.io.IOException;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import vr.com.data.DataProvider;
import vr.com.data.DataResource;

public enum MongoResource implements DataResource{
	
	Instance;
	
	private MongoDatabase db = null;
	private MongoClient mongoClient = null;
	private boolean initialize = false;
	
	@Override
	public void initialize() {
		initialize = true;
		mongoClient = new MongoClient();
		db = mongoClient.getDatabase("test");
	}

	@Override
	public DataProvider getProvider(String name) {
		if (!initialize) {
			initialize();
		}
		MongoCollection<Document> c = db.getCollection(name);
		return new MongoDataProvider(c);
	}

	@Override
	public void close() throws IOException {
		mongoClient.close();
	}
	
	/**
	 * 	接口
	 * 	{
	 * 		url: "",
	 * 		name: "",
	 * 		desc: "",
	 * 		params: [{
	 *			key: "",
	 *			desc: ""
	 *			constraint: ""
	 * 		}],
	 * 		result: [{
	 * 			key: "",
	 * 			desc: "",
	 * 		}]
	 * 	}
	 * 	
	 * 	测试用例
	 * 	{
	 * 		url: "",
	 * 		params: [],
	 * 		result: ""
	 * 	}
	 */
}
