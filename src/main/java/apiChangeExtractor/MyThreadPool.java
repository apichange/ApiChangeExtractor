package apiChangeExtractor;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyThreadPool extends ThreadPoolExecutor {
	private static final Logger logger = LoggerFactory.getLogger(MyThreadPool.class);
	public MyThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
		super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
	}
	@SuppressWarnings("unchecked")
	protected void afterExecute(Runnable r, Throwable t) {
		super.afterExecute(r, t);
		String repositoryId = null;
		if (t == null && r instanceof Future) {
		       try {
		    	   repositoryId = ((Future<String>) r).get();
		       } catch (CancellationException ce) {
		           t = ce;
		           ce.printStackTrace();
		       } catch (ExecutionException ee) {
		           t = ee.getCause();
		           ee.printStackTrace();
		       } catch (InterruptedException ie) {
		           Thread.currentThread().interrupt(); // ignore/reset
		       }
		}
		if (t != null){
			logger.warn(t.toString());
		}else{
			logger.warn("repository: "+repositoryId +" task finished");
		}
	}
}
