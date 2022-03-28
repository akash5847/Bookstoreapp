package bookdb1;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class Dbcon {
//Creating a Methord
Connection connect(){
    String url = "jdbc:sqlite:C:/Users/Akash/Documents/NetBeansProjects/database_book.db";
    Connection conn = null;  
    
    try{
        conn = DriverManager.getConnection(url);
        System.out.println("Connection is succesfull");            
    }catch(SQLException e){
        System.out.println(e.getMessage());
    }

      return conn;

    }
    
}
