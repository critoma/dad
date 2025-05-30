package eu.ase.lambdathreads;

import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public class ProgMainLambdaThreadCallFuture04 {
	public static void main(String[] args) {

		Callable<Integer> task = () -> {
		    try {
			TimeUnit.SECONDS.sleep(2);
			return 123;
		    }
		    catch (InterruptedException e) {
			throw new IllegalStateException("task interrupted", e);
		    }
		};

		ExecutorService executor = Executors.newFixedThreadPool(1);
		Future<Integer> future = executor.submit(task);

		System.out.println("future done? " + future.isDone());

		Integer result = 0;
		try { 
			result = future.get();
			// exception here, because non-blocking waiting is for 1 sec 
			// and the future exceution is taking 2 seconds 
			//result = future.get(1, TimeUnit.SECONDS);
		} catch(InterruptedException ie) {
			ie.printStackTrace();
		} catch(ExecutionException ee) {
			ee.printStackTrace();
		} // catch (TimeoutException te) {
		//	te.printStackTrace();
		//}

		System.out.println("future done? " + future.isDone());
		System.out.print("result: " + result);

		try {
		    System.out.println("attempt to shutdown executor");
		    executor.shutdown();
		    executor.awaitTermination(5, TimeUnit.SECONDS);
		}
		catch (InterruptedException e) {
		    System.err.println("tasks interrupted");
		}
		finally {
		    if (!executor.isTerminated()) {
			System.err.println("cancel non-finished tasks");
		    }
		    executor.shutdownNow();
		    System.out.println("shutdown finished");
		}

		System.out.println("Done!");

	}
}
