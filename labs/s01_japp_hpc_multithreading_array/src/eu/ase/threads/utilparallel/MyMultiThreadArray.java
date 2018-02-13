package eu.ase.threads.utilparallel;

public class MyMultiThreadArray implements Runnable {

	private int[] vinp1;
	
	private int startC;
	private int stopC;
	private Long sum;
	
	public MyMultiThreadArray(int[] v1, int start, int stop, Long sum) {
		this.vinp1 = v1; 
		this.startC = start;
		this.stopC = stop;
		this.sum = sum;
	}
	
	@Override
	public void run() {
		for (int idx = startC; idx <= stopC; idx++) {
			this.sum += vinp1[idx];
		}

	}
	
	public Long getSum() {
		return this.sum;
	}

}
