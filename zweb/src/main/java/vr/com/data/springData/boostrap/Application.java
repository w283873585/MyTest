package vr.com.data.springData.boostrap;

import java.net.UnknownHostException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import vr.com.data.springData.repository.ItemRepository;

import com.mongodb.Mongo;

@SpringBootApplication
@EnableMongoRepositories(basePackages={"vr.com.data.springData.repository"})
public class Application {
	
	@Bean
	public Mongo mongo() throws UnknownHostException {
		return new Mongo("localhost");
	}
	
	@Bean
	public MongoDbFactory mongoDbFactory() throws Exception {
		return new SimpleMongoDbFactory(new Mongo(), "test");
	}
	
	public @Bean MongoTemplate mongoTemplate() throws Exception {
        return new MongoTemplate(mongo(), "test");
	}
	
	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(Application.class, args);
		ItemRepository r = context.getBean(ItemRepository.class);
		System.out.println(r.count());
	}
}
