package vr.com.data.mongo;

import java.io.IOException;

import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import vr.com.data.DataProvider;
import vr.com.data.DataResource;
import vr.com.pojo.InterfaceEntityCodec;
import vr.com.pojo.InterfaceParamCodec;

public enum MongoResource implements DataResource{
	
	Instance;
	
	private MongoDatabase db = null;
	private MongoClient mongoClient = null;
	private boolean initialize = false;
	
	@Override
	public void initialize() {
		initialize = true;
		
		CodecRegistry registry_prev = CodecRegistries.fromCodecs(new InterfaceParamCodec());
		CodecRegistry registry = CodecRegistries.fromRegistries(
				CodecRegistries.fromCodecs(new InterfaceEntityCodec(registry_prev)),
				registry_prev,
				MongoClient.getDefaultCodecRegistry());
		
		MongoClientOptions options = MongoClientOptions.builder()
	                .codecRegistry(registry).build();
		
		mongoClient = new MongoClient("127.0.0.1:27017", options);
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
