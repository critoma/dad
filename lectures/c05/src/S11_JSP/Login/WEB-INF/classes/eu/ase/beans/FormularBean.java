package eu.ase.beans;

import java.sql.*;


public class FormularBean {
	public FormularBean() {}

	public int insertInDB(String n, String p, String c, String credit, String cash) {
	   int result = 0;
	   String OdbcUrl = "jdbc:odbc:Login";
	   String user = "";
	   String pass = "";

	   try {
      		Class cc = Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
    	   }  catch(ClassNotFoundException exc) {
		exc.printStackTrace();
		result = 1;
	   }

	   Connection db_con=null;
    	   Statement st = null;

    	   try{
      		db_con = DriverManager.getConnection(OdbcUrl , user, pass);

      		st = db_con.createStatement();
		String sqlstr2 = "INSERT INTO Membri(Nume, Prenume, Card, Cash, Credit) VALUES ('"+n+"','"+p+"', '"+c+"', '"+cash+"', '"+credit+"')";
	        st.executeUpdate(sqlstr2);
      		st.close();
		
           } catch(Exception e1) {
	       e1.printStackTrace();
		result = 2;
    	   }
    	   finally {
      		try {
        		if(db_con!=null && !db_con.isClosed()) db_con.close();
      		}
      		catch(Exception e2) {
       			e2.printStackTrace();
      		}
	   }
	  return result;
    }

} //end class

