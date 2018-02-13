package eu.ase.threads.utilparallel;

public class MyMultiThreadArray implements Runnable {

	private int[] vinp1;
	private int[] vinp2;
	private int[] voutRez;
	
	private int startC;
	private int stopC;
	
	public MyMultiThreadArray(int[] v1, int[] v2, int[] vout, int start, int stop) {
		this.vinp1 = v1; this.vinp2 = v2; this.voutRez = vout;
		this.startC = start;
		this.stopC = stop;
	}
	
	@Override
	public void run() {
		for (int idx = startC; idx <= stopC; idx++) {
			voutRez[idx] = vinp1[idx] + vinp2[idx];
		}

	}

}
