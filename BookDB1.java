package bookdb1;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
public class BookDB1 {
    
//non Static variable  
    String reg = "^[a-z0-9_-]{3,15}$";  //takes only alphabet and number.
    String bk_name;
    String au_name;
    int price;
    int qnty;
    
    Connection conn;
    Dbcon ref;
    
    public void insert(){
        Scanner sc = new Scanner(System.in); 
        try {
            ref = new Dbcon();
            conn=ref.connect();
            int i = 0;
            bk_name = "";
            do{
                Scanner input = new Scanner(System.in);
                System.out.println("Enter book name:");
                if(input.hasNext("^[A-Za-z]{0,}[0-9]{1,10}$")){
                    bk_name = input.next();
                    i=1;
                    
                }else{
                    
                    System.out.println("Invalid input format");
                    
                }
                }while(i==0);
            
            int j = 0;
            au_name="";
            do{
                Scanner input = new Scanner(System.in);
                System.out.println("Enter author name:");
                if(input.hasNext("^[A-Za-z]{0,20}$")){
                    au_name = input.next();
                    j=1;
                    
                }else{
                    
                    System.out.println("Invalid input format");
                    
                }
                }while(j==0);
            
            int k = 0;
            boolean flag;
            Scanner next = new Scanner(System.in);
            do {
                String yearPattern = "\\d{1,}";
                System.out.print("");
                String input = next.next();
                flag = input.matches(yearPattern);
                if (!flag) System.out.println("Invalid data!");
            } while (!flag);
            System.out.println("Enter book price: ");
            price = sc.nextInt();
            System.out.println("Enter book quantity: ");
            qnty = sc.nextInt();
            
            //  String variable which hold a String representation of a Query
            // ? is called Placeholder
            String sql = "INSERT INTO tbl_book(bk_name,au_name,price,qnty) VALUES (?,?,?,?)";
            
            //Conn knows where the database is located
            //Conn knows which database contains the following table
            PreparedStatement ps = conn.prepareStatement(sql);
            
            ps.setString(1, bk_name);
            ps.setString(2, au_name);
            ps.setInt(3, price);
            ps.setInt(4, qnty);
            
            // Save record inside table
            ps.execute();
            System.out.println("Record inserted");
            
            
            
            
            conn.close(); 
        }catch(Exception e){
            System.out.println("e.getMeassage()");
        }
    }
    
    public void display(){
         try{
            ref = new Dbcon();
            conn = ref.connect();
            
            String sql = "SELECT bk_name, au_name, price, qnty FROM tbl_book";
            Statement stmt = conn.createStatement();//statement as ? is not used
            ResultSet rs = stmt.executeQuery(sql);
             System.err.println("book_name"+"\t"+"author_name"+"\t"+"price"+"\t"+"quantity");
            while(rs.next()){                
                System.out.println(rs.getString("bk_name")+"\t"+"\t"+rs.getString("au_name")+"\t"+"\t"+rs.getInt("price")+"\t"+rs.getInt("qnty"));
            }       
            
        }catch(Exception e){
            
        }
    }
   public void search(String bkname){
        int flag=0;
        try{
            ref = new Dbcon();
            conn = ref.connect();
            
            String sql = "SELECT au_name, price FROM tbl_book WHERE bk_name=?";
            PreparedStatement ps = conn.prepareStatement(sql);//prepared used as ? is used
            ps.setString(1, bkname);
            ResultSet rs = ps.executeQuery();//it is a matrix that hold data in rs
             while(rs.next()){
                System.out.println(rs.getString("au_name")+"\t"+rs.getInt("price"));
                flag=1;
            } 
             if(flag==0){
                 System.out.println("Book not found");
             }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
            
            
    
    public void update(String bkname,Integer pr,Integer q){
       
        try{
          ref = new Dbcon();
            conn = ref.connect();
            
             String sql = "UPDATE tbl_book SET price = ?, qnty = ? WHERE bk_name=? "; //One particular sql command (SET,UPDATE,SELECT etc.) can be used only once in a particular query
             PreparedStatement ps = conn.prepareStatement(sql);
             ps.setInt(1, pr);
             ps.setInt(2, q);
             ps.setString(3, bkname);
             ps.execute();
             System.out.println("RECORD UPDATED");
               
            
            
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    public void delete(){
        
        
    }
    
    public static void main(String[] args) {
      BookDB1 obj = new BookDB1();
      String bkname;
      int ch;
      Scanner sc = new Scanner(System.in);
      while(true){
        System.out.println("1. insert book");
        System.out.println("2. display book");
        System.out.println("3. search book");
        System.out.println("4. update book");
        System.out.println("5. Exit");
        System.out.println("Enter your choice");
        ch=sc.nextInt();
        
        if(ch==1){
            obj.insert();
        }
        if(ch==2){
            obj.display();
        }
        if(ch==3){
            sc.nextLine();
              System.out.println("Enter book name to search: ");
              bkname=sc.nextLine();
               obj.search(bkname);
              
        }
        if(ch==4)
           {
           sc.nextLine();
               System.out.println("Enter book name whose details is to be updated :");
                bkname=sc.nextLine();
                System.out.println("Enter the updated price");
                int pr=sc.nextInt();
                System.out.println("Enter the updated quantity");
                int q=sc.nextInt();
                obj.update(bkname,pr,q);
           
           
           }
        if(ch==5){    
            System.exit(0);
        }
    }
    
    }
}
