package eu.ase.beans;

import java.sql.*;
import java.util.Date;

public class FormularBean {
	public FormularBean() {}

	public int insertInDB(String n, String p, String c) {
	   int result = 0;
	   String mySQLUrl = "jdbc:mysql://127.0.0.1:3306/mycards?"
              + "user=mysqluser&password=mysqluserpwd";
	   //String user = "";
	   //String pass = "";

	   try {
      		Class cc = Class.forName("com.mysql.jdbc.Driver");
    	   }  catch(ClassNotFoundException exc) {
		exc.printStackTrace();
		result = 1;
	   }

	   Connection db_con=null;
    	   Statement st = null;

    	   try{
      		db_con = DriverManager.getConnection(mySQLUrl);

      		st = db_con.createStatement();
		String sqlstr2 = "INSERT INTO mycards.ClientsCards(id, Name, FirstName, CardType, InsertTime) VALUES (default, '"+n+"','"+p+"', '"+c+"', '"+new java.sql.Date((new java.util.Date()).getTime())+"')";
	        st.executeUpdate(sqlstr2);
      		st.close();
		for(int i = 0; i < 100000; i++) {
			if(i == 100000)
				System.out.println("i = " + i);
		}
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

