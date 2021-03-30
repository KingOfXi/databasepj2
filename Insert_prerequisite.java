import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.*;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

public class Insert_prerequisite implements Runnable {
    CountDownLatch latch=null;
   /* public Connection connection=null;
    private   String host = "localhost";
    private    String dbname = "fun";
    private   String user = "postgres";
    private    String pwd = "admin";
    private   String port = "5432";*/
    PreparedStatement preparedStatement=null;
    Statement statement=null;
    ResultSet resultSet=null;
public Insert_prerequisite(){}
    public Insert_prerequisite(CountDownLatch a){
    latch=a;
    }
  /*  public void getConnection() throws Exception {
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
    public void insert_prerequisite()  throws  Exception {
        //getConnection();
       Connection connection= ConnectionManager.getConnection();
        String line=null;
        String each[];
        long count=0;
        String prerequisite="insert into prerequisite(courseID,prerequisite)  values(?,?);";
       // connection.setAutoCommit(false);
        ConnectionManager.beginTransaction(connection);
        preparedStatement=connection.prepareStatement(prerequisite);
        try(BufferedReader bufferedReader=new BufferedReader(new FileReader("prerequisite.txt"))){
            try{
                while (((line = bufferedReader.readLine()) != null)&&!line.equals("")){
                    each=line.split(";");
                    preparedStatement.setObject(1,each[0]);
                    preparedStatement.setObject(2,each[1]);
                    preparedStatement.addBatch();
                    count++;
                    if (count%500==0){
                        count=0;
                        preparedStatement.executeBatch();
                        preparedStatement.clearBatch();
                    }
                }
            }catch (Exception e){
                System.out.println("this is the prerequisite problem ");
            }



        }catch (FileNotFoundException e) {
            System.out.println("file not find ");
        }
        preparedStatement.executeBatch();
        preparedStatement.clearBatch();
        ConnectionManager.commitTransaction(connection);
        ConnectionManager.close(preparedStatement);
        ConnectionManager.closeConnection();
       // connection.commit();
     //   preparedStatement.close();
       // statement.close();
    }


   /* @Override
    public Integer call() throws Exception {
        insert_prerequisite();
        return 1;
    }
*/
    @Override
    public void run() {
        try {
            insert_prerequisite();
            latch.countDown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
