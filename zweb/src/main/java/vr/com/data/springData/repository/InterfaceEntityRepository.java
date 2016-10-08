package vr.com.data.springData.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import vr.com.data.springData.pojo.InterfaceEntity;

public interface InterfaceEntityRepository extends MongoRepository<InterfaceEntity, String> {
	
}
