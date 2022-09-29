package eu.ase.threads;

public class MyMultiThreadArray implements Runnable {

	private float[] vinp1;
	private float[] vinp2;
	private float[] voutRez;
	
	private int startC;
	private int stopC;
	
	public MyMultiThreadArray(float[] v1, float[] v2, float[] vout, int start, int stop) {
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
