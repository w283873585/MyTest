package vr.com.data.springData.repository;

import vr.com.data.springData.pojo.Item;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ItemRepository extends MongoRepository<Item, String> {
	
}
