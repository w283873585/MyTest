package vr.com.data.springData.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import vr.com.commandRe.core.support.CommandResultSupport;

public interface CommandResultRepository extends MongoRepository<CommandResultSupport, String>{

}
