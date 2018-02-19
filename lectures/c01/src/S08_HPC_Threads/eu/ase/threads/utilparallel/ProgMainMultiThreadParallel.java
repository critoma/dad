package eu.ase.threads.utilparallel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class ProgMainMultiThreadParallel {

	private static final int NTHREADS = 2;

	// Adding the sum of the elements
	public static void main(String[] args) {
		int vectDim = 40000000;
		int[] vv1 = new int[vectDim];
		Long sum = new Long(0);

		for (int i = 0; i < vectDim; i++) {
			vv1[i] = 1 + i;
		}

		// ########################
		int startIdx = 0, stopIdx = 0;
		long startTime = 0, stopTime = 0;
		Long[] vectSum = new Long[NTHREADS];

		// 1.1 Seq Array Sum
		sum = new Long(0);
		startTime = System.currentTimeMillis();
		for (int i = 0; i < vectDim; i++)
			sum += vv1[i];
		stopTime = System.currentTimeMillis();
		System.out.println("1.1 Seq Array time = " + (stopTime - startTime)
				+ " , sum = " + sum);

		// 2.1 "Multi-threading parallel traditional" - Array Sum
		sum = new Long(0);
		startTime = System.currentTimeMillis();
		Thread[] vectThreads = new Thread[NTHREADS];
		MyMultiThreadArray[] vectRThreads = new MyMultiThreadArray[NTHREADS];

		for (int it = 0; it < NTHREADS; it++) {
			startIdx = it * (vv1.length / NTHREADS);
			stopIdx = (it + 1) * (vv1.length / NTHREADS) - 1;
			vectSum[it] = new Long(0);
			vectRThreads[it] = new MyMultiThreadArray(vv1, startIdx, stopIdx,
					vectSum[it]);
			vectThreads[it] = new Thread(vectRThreads[it]);
		}

		for (int it = 0; it < NTHREADS; it++) {
			vectThreads[it].start();
		}

		for (int it = 0; it < NTHREADS; it++) {
			try {
				vectThreads[it].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		for (int it = 0; it < NTHREADS; it++) {
			sum += vectRThreads[it].getSum();
		}
		stopTime = System.currentTimeMillis();
		System.out.println("2.1 Traditional Multi-Thread Array time = "
				+ (stopTime - startTime) + " , sum = " + sum);

		// 3.1 Executor - Service parallel Array sum:
		sum = new Long(0);
		startTime = System.currentTimeMillis();
		ExecutorService execThreadPool = Executors.newFixedThreadPool(NTHREADS);
		MyMultiThreadArray[] workerTask = new MyMultiThreadArray[NTHREADS];

		for (int it = 0; it < NTHREADS; it++) {
			startIdx = it * (vv1.length / NTHREADS);
			stopIdx = (it + 1) * (vv1.length / NTHREADS) - 1;
			workerTask[it] = new MyMultiThreadArray(vv1, startIdx, stopIdx,
					vectSum[it]);
			execThreadPool.execute(workerTask[it]);
		}

		execThreadPool.shutdown();
		try {
			execThreadPool.awaitTermination(Long.MAX_VALUE,
					TimeUnit.NANOSECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		for (int it = 0; it < NTHREADS; it++) {
			sum += workerTask[it].getSum();
		}

		stopTime = System.currentTimeMillis();
		System.out.println("3.1 exec-service Parallel Array time = "
				+ (stopTime - startTime) + " , sum = " + sum);

		// 4.1 Future - Callable mechanism
		ExecutorService executor = Executors.newFixedThreadPool(NTHREADS);
		List<Future<Long>> list = new ArrayList<Future<Long>>();
		
		sum = new Long(0);
		
		int slot = 0;

		startTime = System.currentTimeMillis();
		for(int it = 0; it < NTHREADS; it++) {
			startIdx = it * (vv1.length / NTHREADS);
			stopIdx = (it + 1) * (vv1.length / NTHREADS) - 1;
			
			vectSum[slot] = new Long(0);
			Callable<Long> worker = new MyCallableArray(vv1, startIdx, stopIdx, vectSum[slot]);
			Future<Long> submit = executor.submit(worker);
			list.add(submit);
		} //end for
				
		for (Future<Long> future : list) {
			try {
				vectSum[slot] = future.get();
				slot++;
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException ee) {
				ee.printStackTrace();
			}
		} // end for - future

		for (int it = 0; it < NTHREADS; it++) {
			sum += vectSum[it];
		}

		executor.shutdown();
		try {
			executor.awaitTermination(Long.MAX_VALUE,
					TimeUnit.NANOSECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}


		stopTime = System.currentTimeMillis();
		System.out.println("4.1 Array Multithreading - thread pool (ExecutorService) + Callable & Future - stopTime - startTime = "
						+ (stopTime - startTime) + " , sum = " + sum);

		// 5.1 - Fork-Join
		sum = new Long(0);
		startTime = System.currentTimeMillis();
		sum = SumForkJoin.sumArrays(vv1);
		stopTime = System.currentTimeMillis();
		System.out.println("5.1 Fork-Join Parallel Array time = "
				+ (stopTime - startTime) + " , sum = " + sum);
	}

}
