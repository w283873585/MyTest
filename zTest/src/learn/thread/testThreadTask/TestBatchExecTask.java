package learn.thread.testThreadTask;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;




/**
 * 批量执行任务
 * @author Administrator
 *
 */
public class TestBatchExecTask{
	private final ExecutorService exec = Executors.newFixedThreadPool(10);
	
	public List<TravelQuote> getRankedTravelQuotes(
			TravelInfo info, Set<TravelCompany> companies,
			Comparator<TravelQuote> ranking, long time, TimeUnit unit) throws InterruptedException {
		List<QuoteTask> tasks = new ArrayList<QuoteTask>();
		for (TravelCompany company : companies) {
			tasks.add(new QuoteTask(company, info));
		}
		
		List<Future<TravelQuote>> futures = exec.invokeAll(tasks, time, unit);
		
		List<TravelQuote> quotes = new ArrayList<TravelQuote>(tasks.size());
		Iterator<QuoteTask> taskIter = tasks.iterator();
		for (Future<TravelQuote> f : futures) {
			QuoteTask task = taskIter.next();
			try {
				quotes.add(f.get());
			} catch (ExecutionException e) {
				System.out.println(task);
				e.printStackTrace();
			}
		}
		Collections.sort(quotes, ranking);
		return quotes;
	}
	
	
	
	public static class QuoteTask implements Callable<TravelQuote> {
		private final TravelCompany company;
		private final TravelInfo info;
		
		public QuoteTask(TravelCompany company, TravelInfo info) {
			this.company = company;
			this.info = info;
		}
		@Override
		public TravelQuote call() throws Exception {
			return company.solicitQuote(info);
		}
		
	}
	public static class TravelCompany{
		public TravelQuote solicitQuote(TravelInfo info) {
			return null;
		}
	}
	
	
	public static class TravelInfo{}
	public static class TravelQuote {}
}