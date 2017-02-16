package vr.com.data.springData.repository;

import vr.com.data.springData.pojo.TestCaseEntity;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface TestCaseRepository extends MongoRepository<TestCaseEntity, String> {
	
}
