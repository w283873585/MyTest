/*package vr.com.data.springData.boostrap;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.Mongo;

import vr.com.data.springData.pojo.Item;
import vr.com.data.springData.repository.ItemRepository;

@SpringBootApplication
@EnableMongoRepositories(basePackages={"vr.com.data.springData.repository"})
public class Application {
	
	@Bean
	public MongoDbFactory mongoDbFactory() throws Exception {
		return new SimpleMongoDbFactory(new Mongo("128.0.0.1"), "test");
	}
	
	@Bean
	@SuppressWarnings("deprecation")
	public MongoTemplate mongoTemplate() throws Exception {
        return new MongoTemplate(new SimpleMongoDbFactory(new Mongo("127.0.0.5"), "test"));
	}
	
	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(Application.class, args);
		ItemRepository r = context.getBean(ItemRepository.class);
		System.out.println(r.count());
		Item it = new Item();
		it.setName("天下之大");
		it.setValue("莫非王土");
		r.insert(it);
		System.out.println(r.findAll());
		// r.insert(new Item());
	}
}
*/