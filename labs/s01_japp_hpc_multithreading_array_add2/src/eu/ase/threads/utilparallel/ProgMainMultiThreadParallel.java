package eu.ase.threads.utilparallel;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ProgMainMultiThreadParallel {

	private static final int NTHREADS = 2;
	
	public static void main(String[] args) {
		int vectDim = 4000000;
		
		int[] vv1 = new int[vectDim];
		int[] vv2 = new int[vectDim];
		
		for(int  i = 0; i < vectDim; i++) {
			vv1[i] = 15; vv2[i] = 25;
		}
		
		int[] vv3 = new int[vectDim];
		
		//########################
		int startIdx = 0, stopIdx = 0;
		long startTime = 0, stopTime = 0;
		
		// 1.1 Seq Array Sum
		startTime = System.currentTimeMillis();
		for(int i = 0; i < vectDim; i++)
			vv3[i] = vv1[i] + vv2[i];
		stopTime = System.currentTimeMillis();
		System.out.println("1.1 Seq Array Sum = " + (stopTime - startTime) + " , vv3[0] = " + vv3[0]);
		
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
		System.out.println("2.1 Traditional Multi-Thread Array Sum = " + (stopTime - startTime) + " , vv3[0] = " + vv3[0]);
		
		
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
		
		// 5.1 - Fork-Join
		startTime = System.currentTimeMillis();		
		SumForkJoin.sumArrays(vv1, vv2, vv3);
		stopTime = System.currentTimeMillis();
		System.out.println("5.1 exec-service Parallel Array sum = "
				+ (stopTime - startTime) + " , vv3[0] = " + vv3[0]);
	}

}
