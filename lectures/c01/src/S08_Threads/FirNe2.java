class FirNesincronizatA1 extends Thread {
	
	static int a = 0, b = 0;
	
	public FirNesincronizatA1(String nume){
		super(nume);
	}
	
	void metoda() {
		System.out.println(this.getName() + " a=" +a+" b="+b);
		a++;
			try {
				this.sleep((int)(Math.random()*1000));//blocked thread
			} catch(InterruptedException e) {e.printStackTrace();}
		b++; //este atomic?
	}
	
	public void run(){
		for(int i = 0; i < 3; i++){
			this.metoda();			
		}
		
		System.out.println("GATA! " + this.getName());
	}
}

class ProgMainNeA1 {
	public static void main(String[] args) {
		
		FirNesincronizatA1 f1 = new FirNesincronizatA1("f1");//new Thread
		FirNesincronizatA1 f2 = new FirNesincronizatA1("f2");//new Thread
		
		f1.start();//runnable thread
		f2.start();//runnable thread
		
		System.out.println("End main()");
		
	}
}