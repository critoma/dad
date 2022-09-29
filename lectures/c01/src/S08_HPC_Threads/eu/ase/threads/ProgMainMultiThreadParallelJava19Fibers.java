package eu.ase.threads;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

// it works ONLY with Java 19+ - Project Loom
// javac --enable-preview --release 19 eu/ase/threads/*.java
// java --enable-preview -Xms1024m -Xmx8192m eu.ase.threads.ProgMainMultiThreadParallelJava19Fibers


/*

// javac --enable-preview --release 19 -cp .:./EAAsyncLib/ea-async-1.2.3.jar eu/ase/threads/*.java
// java -javaagent:./EAAsyncLib/ea-async-1.2.3.jar --enable-preview -Xms1024m -Xmx8192m eu.ase.threads.ProgMainMultiThreadParallelJava19Fibers


// Java 11+ not 17 with EA - Electronic Arts Library for async-await:
// https://github.com/electronicarts/ea-async
import com.ea.async.Async;
import static com.ea.async.Async.await;
import java.util.concurrent.CompletableFuture;
import static java.util.concurrent.CompletableFuture.completedFuture;


class FactorialAsync {
	static { 
    	Async.init(); 
	}
	
	private CompletableFuture<Long> factorialAsync(int number) {
		long fact = 1;
		for (var i = 1; i < number; i++) {
			fact = fact * i;
		}
		
		System.out.println("Factorial Thread name = " + Thread.currentThread().getName());
		Long f = Long.parseLong("" + fact);
		return CompletableFuture.completedFuture(f);
	}
	
	public long factorialUsingEAAsync(int number) {
    	CompletableFuture<Integer> completableFuture = CompletableFuture.supplyAsync(() -> {
    		return 5;
    	});
    	int result = Async.await(completableFuture);
    	return result;
	}
	
}
*/

public class ProgMainMultiThreadParallelJava19Fibers {

	

	private static final int NTHREADS = 4;
	
	public static void main(String[] args) {
		var vectDim = 300_000_000;
		
		float[] vv1 = new float[vectDim];
		float[] vv2 = new float[vectDim];
		
		for(int  i = 0; i < vectDim; i++) {
			vv1[i] = 15.3f; vv2[i] = 25.2f;
		}
		
		float[] vv3 = new float[vectDim];
		
		//########################
		int startIdx = 0, stopIdx = 0;
		long startTime = 0, stopTime = 0;
		
		// 1.1 Seq Array Sum
		startTime = System.currentTimeMillis();
		for(int i = 0; i < vectDim; i++)
			vv3[i] = vv1[i] + vv2[i];
		stopTime = System.currentTimeMillis();
		System.out.println("1.1 Seq Array ms = " + (stopTime - startTime) + " , vv3[0] = " + vv3[0]);
		
		// 2.1 "Multi-threading parallel traditional" - Array Sum
		startTime = System.currentTimeMillis();
		Thread[] vectThreads = new Thread[NTHREADS];
		
		for(int it = 0; it < NTHREADS; it++) {
			startIdx = it * (vv1.length / NTHREADS);
			stopIdx = (it + 1) * (vv1.length / NTHREADS) - 1;
			vectThreads[it] = new Thread(new MyMultiThreadArray(vv1, vv2, vv3, startIdx, stopIdx));
		}
		
		for (int it = 0; it < NTHREADS; it++) {
			vectThreads[it].start();
		}
		
		for(int it = 0; it < NTHREADS; it++) {
			try {
				vectThreads[it].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		stopTime = System.currentTimeMillis();
		System.out.println("2.1 Traditional Multi-Thread Array ms = " + (stopTime - startTime) + " , vv3[0] = " + vv3[0]);
		
		
		// 3.1 Executor - Service parallel Array sum:
		startTime = System.currentTimeMillis();
		ExecutorService execThreadPool = Executors.newFixedThreadPool(NTHREADS);

		for (int it = 0; it < NTHREADS; it++) {
			startIdx = it * (vv1.length / NTHREADS);
			stopIdx = (it + 1) * (vv1.length / NTHREADS) - 1;
			Runnable workerTask = new MyMultiThreadArray(vv1, vv2, vv3,
					startIdx, stopIdx);
			execThreadPool.execute(workerTask);
		}

		execThreadPool.shutdown();
		try {
			execThreadPool.awaitTermination(Long.MAX_VALUE,
					TimeUnit.NANOSECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		stopTime = System.currentTimeMillis();
		System.out.println("3.1 exec-service Parallel Array sum = "
				+ (stopTime - startTime) + " , vv3[0] = " + vv3[0]);
				
		// 3.2 Fibers:
		Thread testJ19Fiber = Thread.startVirtualThread(
  			() -> {
    			System.out.println("Hello World");
  			}
		);
		try {
		   testJ19Fiber.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("---------");
		try (ExecutorService e = Executors.newVirtualThreadPerTaskExecutor()) {
    		e.submit(() -> System.out.println("1"));
    		e.submit(() -> System.out.println("2"));
		}
		System.out.println("---------");
		
		
		// 3.2 Java 19 Fibers:
		startTime = System.currentTimeMillis();
		Thread[] vectThreadsFiber = new Thread[NTHREADS];
		Runnable[] workerTasks = new Runnable[NTHREADS];
		
		for(int it = 0; it < NTHREADS; it++) {
			startIdx = it * (vv1.length / NTHREADS);
			stopIdx = (it + 1) * (vv1.length / NTHREADS) - 1;
			workerTasks[it] = new MyMultiThreadArray(vv1, vv2, vv3, startIdx, stopIdx);
		}
		
		for (int it = 0; it < NTHREADS; it++) {
			vectThreadsFiber[it] = Thread.startVirtualThread(workerTasks[it]);
		}
		
		for(int it = 0; it < NTHREADS; it++) {
			try {
				vectThreadsFiber[it].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		stopTime = System.currentTimeMillis();
		System.out.println("3.2 Java 19 Fibers ms = " + (stopTime - startTime) + " , vv3[0] = " + vv3[0]);
		
		// 3.3 java EA Async - Await:
		// FactorialAsync factorialAsyncObj = new FactorialAsync();
		// long rFact = factorialAsyncObj.factorialUsingEAAsync(5);
		// System.out.println("5! = " + rFact);
		
	} // end main

} // end class
