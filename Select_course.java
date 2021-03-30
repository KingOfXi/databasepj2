import org.postgresql.util.PSQLException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.*;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CountDownLatch;

public class Select_course implements Runnable {

    //public Connection connection=null;
CountDownLatch latch=null;

    /*private   String host = "localhost";
    private    String dbname = "fun";
    private   String user = "postgres";
    private    String pwd = "admin";
    private   String port = "5432";*/
    PreparedStatement preparedStatement=null;
    Statement statement=null;
    ResultSet resultSet=null;
public  Select_course(){}
    public  Select_course(CountDownLatch a){latch=a;}
   /* public void getConnection() throws Exception {
        try {
            Class.forName("org.postgresql.Driver");

        } catch (Exception e) {
            System.err.println("Cannot find the PostgreSQL driver. Check CLASSPATH.");
            System.exit(1);
        }

        try {
            String url = "jdbc:postgresql://" + host + ":" + port + "/" + dbname;
            connection = DriverManager.getConnection(url, user, pwd);

        } catch (SQLException e) {
            System.err.println("Database connection failed");
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }*/

    public void insert_select_course() throws Exception {
        String insert_select_course="insert into select_course(sid,course_id) values(?,?);";
        String line=null;
        String each[]=null;
       // getConnection();
       // connection.setAutoCommit(false);
        Connection connection=ConnectionManager.getConnection();
        ConnectionManager.beginTransaction(connection);
        preparedStatement=connection.prepareStatement(insert_select_course);
        long count=0;
        int a=0;
        try (BufferedReader bufferedReader=new BufferedReader(new FileReader("select_course.csv"))){
            while ((line=bufferedReader.readLine())!=null&&!line.equals("")){
                try{
                    each=line.split(",");

                    Set<String> same=new HashSet<>();
                    for (int i=4;i<each.length;i++){
                        if (!same.contains(each[i])){
                            same.add(each[i]);
                            preparedStatement.setInt(1,Integer.parseInt(each[3]));
                            preparedStatement.setObject(2,each[i]);
                            preparedStatement.addBatch();
                            count++;
                            if (count%1000==0){
                               a++;
                               if (a==50){a=0;
                                   System.out.println("h");}
                                count=0;
                                preparedStatement.executeBatch();
                                preparedStatement.clearBatch();
                            }
                        }
                    }
                } catch (PSQLException e){
                    System.out.println("other select problem");
                } catch (SQLException e){
                    System.out.println("select course problem");
                } catch (Exception e){
                    System.out.println("other problem");
                }
            }


        }catch (FileNotFoundException e){
            System.out.println("file not find");
        }
        preparedStatement.executeBatch();
        preparedStatement.clearBatch();
        ConnectionManager.commitTransaction(connection);
     //   connection.commit();
      ConnectionManager.close(preparedStatement);
      ConnectionManager.closeConnection();

    }

    @Override
    public void run() {
        try {
            insert_select_course();
            latch.countDown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

   /* @Override
    public Integer call() throws Exception {
        insert_select_course();
        return 3;
    }*/
}

