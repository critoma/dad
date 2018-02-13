package eu.ase.threads;

class FirExemplu extends Thread {
  String mesaj;
  public FirExemplu(String m) {this.mesaj = m;}
  public void run() {
     while (true) {
       System.out.println(this.mesaj);
       this.yield();
     }
  }
}
/*
class ProgMainFir1 {
 public static void main(String[] args) {
   FirExemplu f1 = new FirExemplu("f1");
   FirExemplu f2 = new FirExemplu("f2");

    f1.start();
    f2.start();
 }
}
*/

public class Priority {
  public static void main(String[] args) {
   FirExemplu f1 = new FirExemplu("f1");
   FirExemplu f2 = new FirExemplu("f2");

   f1.start();

    Thread curent = Thread.currentThread();
    f2.setPriority(curent.getPriority() + 1);    
    f2.start();
 } 
}
