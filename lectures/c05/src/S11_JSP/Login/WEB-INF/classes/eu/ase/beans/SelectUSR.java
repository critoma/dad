package eu.ase.beans;

import java.sql.*;


public class SelectUSR {
	public SelectUSR() {}

	public int selectFROMDB(String u, String p) {
	   int rez = 0;
	   String OdbcUrl = "jdbc:odbc:Login";
	   String user = "";
	   String pass = "";

	   try {
      		Class cc = Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
    	   }  catch(ClassNotFoundException exc) {
		exc.printStackTrace();
		rez = 2;
	   }

	   Connection db_con=null;
    	   Statement st = null;

    	   try{
      		db_con = DriverManager.getConnection(OdbcUrl , user, pass);
		Statement select = db_con.createStatement();

		ResultSet result = select.executeQuery("SELECT * FROM Login");
		while (result.next()&&(rez==0)) { 
		        String val1 = result.getString("Username");
		        String val2 = result.getString("Pass");
System.out.println("u="+u+" , p="+p+": val1="+val1+" : val2="+val2);
			if((u.compareTo(val1)==0)&&(p.compareTo(val2)==0)) rez=1;
		}
		
           } catch(Exception e1) {
	       e1.printStackTrace();
		rez = 3;
    	   }
    	   finally {
      		try {
        		if(db_con!=null && !db_con.isClosed()) db_con.close();
      		}
      		catch(Exception e2) {
       			e2.printStackTrace();
      		}
	   }
	  return rez;

    }

} //end class

