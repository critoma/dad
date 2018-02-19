package eu.ase.threadsintro;

class ResurseComune {
	int a = 0, b = 0;
	
	void metoda(String nume) {
		Thread t = Thread.currentThread();
		System.out.println(nume + " a=" +a+" b="+b);
		a++;
			try {
				t.sleep((int)(Math.random()*1000));//blocked thread
			} catch(InterruptedException e) {e.printStackTrace();}
		b++; //este atomic?
	}
}

class FirNesincronizatA2 extends Thread {
	
	ResurseComune rc;
	
	public FirNesincronizatA2(String nume, ResurseComune x){
		super(nume);
		this.rc = x;
	}
		
	public void run(){
		for(int i = 0; i < 3; i++){
			rc.metoda(this.getName());			
		}
		
		System.out.println("GATA! " + this.getName());
	}
}

class ProgMainNeA2 {
	public static void main(String[] args) {
	
		ResurseComune rcd = new ResurseComune();
		
		FirNesincronizatA2 f1 = new FirNesincronizatA2("f1", rcd);//new Thread
		FirNesincronizatA2 f2 = new FirNesincronizatA2("f2", rcd);//new Thread
		
		f1.start();//runnable thread
		f2.start();//runnable thread
		
		System.out.println("End main()");
		
	}
}
