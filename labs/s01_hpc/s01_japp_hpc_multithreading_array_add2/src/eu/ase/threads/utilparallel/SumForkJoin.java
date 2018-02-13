package eu.ase.threads.utilparallel;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class SumForkJoin extends RecursiveTask<Void> /*RecursiveTask<Long>*/ {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	static final int SEQUENTIAL_THRESHOLD = 5000;

    int low;
    int high;
    int[] inparray1;
    int[] inparray2;
    int[] outarrayres;

    SumForkJoin(int[] arrinp1, int[] arrinp2, int[] outarray, int lo, int hi) {
        inparray1 = arrinp1;
        inparray2 = arrinp2;
        outarrayres = outarray;
        low   = lo;
        high  = hi;
    }

    protected /*Long*/ Void compute() {
        if(high - low <= SEQUENTIAL_THRESHOLD) {
            //long sum = 0;
            for(int i=low; i < high; ++i) 
            	//sum += array[i];
            	outarrayres[i] = inparray1[i] + inparray2[i];
            //return sum;
         } else {
            int mid = low + (high - low) / 2;
            SumForkJoin left  = new SumForkJoin(inparray1, inparray2, outarrayres, low, mid);
            SumForkJoin right = new SumForkJoin(inparray1, inparray2, outarrayres, mid, high);
            left.fork();
            right.compute();
            left.join();
            //long rightAns = right.compute();
            //long leftAns  = left.join();
            //return leftAns + rightAns;
         }
		return null;
     }

     static /*long*/ Void sumArrays(int[] inparray1, int[] inparray2, int[] outarrayres) {
         return ForkJoinPool.commonPool().invoke(new SumForkJoin(inparray1, inparray2, outarrayres, 0, inparray1.length));
     }
}
