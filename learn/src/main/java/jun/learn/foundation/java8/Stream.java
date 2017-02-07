package jun.learn.foundation.java8;

import java.util.Arrays;
import java.util.Collection;

public class Stream {
	public static void main(String[] args) {
		// http://www.importnew.com/11908.html#lambdaAndFunctional
		
		final Collection<Task> tasks = Arrays.asList(
			new Task(Status.OPEN, 5),
			new Task(Status.OPEN, 13),
			new Task(Status.CLOSED, 8)
		);
		
		System.out.println(tasks.stream()
		.filter( task -> task.getStatus() == Status.OPEN )
		.mapToInt(Task::getPoints)
		.sum());
	}
	
	private enum Status {
        OPEN, CLOSED
    };
     
    private static final class Task {
        private final Status status;
        private final Integer points;
 
        Task( final Status status, final Integer points ) {
            this.status = status;
            this.points = points;
        }
         
        public Integer getPoints() {
            return points;
        }
         
        public Status getStatus() {
            return status;
        }
         
        @Override
        public String toString() {
            return String.format( "[%s, %d]", status, points );
        }
    }
}
