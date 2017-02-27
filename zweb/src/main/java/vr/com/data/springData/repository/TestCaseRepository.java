package vr.com.data.springData.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import vr.com.pojo.TestCaseEntity;

public interface TestCaseRepository extends MongoRepository<TestCaseEntity, String> {
	
}
