import java.io.*;
import java.sql.*;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

public class Insert_course implements Runnable {
    CountDownLatch latch=null;
   /* private   String host = "localhost";
    private    String dbname = "fun";
    private   String user = "postgres";
    private    String pwd = "admin";
    private   String port = "5432";*/
    PreparedStatement preparedStatement=null;
    Statement statement=null;
    ResultSet resultSet=null;
   /* public Connection connection=null;*/
    public Insert_course(){}
    public Insert_course(CountDownLatch a){latch=a;}
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
    public void insert_course() throws  Exception {
        String insert_course="insert into course(courseID,courseName,credit,hour,dept) values(?,?,?,?,?);";
        String line=null;
        String each[]=null;

        //getConnection();
       // connection.setAutoCommit(false);
       Connection connection= ConnectionManager.getConnection();
       ConnectionManager.beginTransaction(connection);
        preparedStatement=connection.prepareStatement(insert_course);
        long count=0;
        BufferedWriter bufferedWriter =new BufferedWriter(new FileWriter("course.sql"));
        try (BufferedReader bufferedReader=new BufferedReader(new FileReader("course_message.csv"))){
            try{
                while ((line=bufferedReader.readLine())!=null&&!line.equals("")){
                    each=line.split(",");
                    preparedStatement.setObject(1,each[0].trim());
                    preparedStatement.setObject(2,each[1]);
                    preparedStatement.setFloat(3,Float.parseFloat(each[2]));
                    preparedStatement.setInt(4,Integer.parseInt(each[3]));
                    preparedStatement.setObject(5,each[4]);
                    bufferedWriter.write(preparedStatement.toString()+";");
                    bufferedWriter.newLine();
                    preparedStatement.addBatch();
                    count++;
                    if (count%200==0){
                        bufferedWriter.flush();
                        count=0;
                        preparedStatement.executeBatch();
                        preparedStatement.clearBatch();
                    }
                }
            }catch (Exception e){
                System.out.println("insert course problem ");
            }

        }catch (IOException e){
            System.out.println("file not find");
        }
        preparedStatement.executeBatch();
        preparedStatement.clearBatch();
      //  connection.commit();
      //this.preparedStatement.close();
       //this.statement.close();
        bufferedWriter.flush();
        bufferedWriter.close();
        ConnectionManager.commitTransaction(connection);
        ConnectionManager.close(preparedStatement);
        ConnectionManager.closeConnection();
    }

    @Override
    public void run() {
        try {
            insert_course();
            latch.countDown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

   /* @Override
    public Integer call() throws Exception {
        insert_course();
        return 4;
    }*/
}
