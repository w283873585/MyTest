package vr.com.data.springData.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import vr.com.pojo.InterfaceEntity;

public interface InterfaceEntityRepository extends MongoRepository<InterfaceEntity, String>, 
	InterfaceEntityRepositoryCustom {
	
	@Query("{'$or': [{'name': {'$regex': ?0}}, {'url': {'$regex': ?0}}]}")
	public List<InterfaceEntity> query(String query);
}
