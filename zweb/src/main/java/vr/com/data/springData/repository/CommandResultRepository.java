package vr.com.data.springData.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import vr.com.command.support.CommandResultSupport;


public interface CommandResultRepository extends MongoRepository<CommandResultSupport, String>{

}
