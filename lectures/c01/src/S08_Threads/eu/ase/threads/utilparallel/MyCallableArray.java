package eu.ase.threads.utilparallel;

import java.util.concurrent.Callable;

public class MyCallableArray implements Callable<Long> {

	private int[] vinp = null;
	private int startC;
	private int stopC;
	private Long sum;
	
	public MyCallableArray(int[] vi1, int start, int stop, Long sum) {
		this.vinp = vi1; 
		this.startC = start;
		this.stopC = stop;
		this.sum = sum;
	}
	
	@Override
	public Long call() throws Exception {
		for(int i = startC; i <= stopC; i++) {
			sum += this.vinp[i];
		}
		return this.sum;
	}
}
