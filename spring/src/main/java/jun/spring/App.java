package jun.spring;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Hello world!
 *
 */
public class App {
    public static void main( String[] args ) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
        
    	Metro m = context.getBean(Metro.class);
        
    	m.run();
        
    	System.out.println(m == context.getBean(Metro.class));
    	
    	context.close();
    	
    	
    	/**
    		metroLine : {
    			pull: function() {},
    			
    			addStation: function(names) {},
    		}
    		
    		metro: {
    			curStation: {},
    			getIn: function(){},
    			getOut: function() {},
    			suspend: function() {},
    			access: function(passenger) {}
    		}
    		
    		station: {
    			prev: {},
    			next: {},
    			access: function(passenger) {}
    		}
    		
    		passenger: {
    			
    		}
    	 */
    }
}
