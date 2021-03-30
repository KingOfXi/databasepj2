import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

public class Insert_class_schedule implements Runnable {
    CountDownLatch latch=null;

    public Connection connection=null;
    Map<String,Integer> classid_classname_courseid=new HashMap<>();

 /*   private   String host = "localhost";
    private    String dbname = "fun";
    private   String user = "postgres";
    private    String pwd = "admin";
    private   String port = "5432";*/
    PreparedStatement preparedStatement=null;
    Statement statement=null;
    ResultSet resultSet=null;
public Insert_class_schedule(){}
    public Insert_class_schedule(CountDownLatch a){latch=a;}
    /*public void getConnection() throws Exception {
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
    public void insert_class() throws  Exception {
        String line=null;
        String each[];
        int class_id=0;
        long count=0;
        //getConnection();
      connection= ConnectionManager.getConnection();
       // connection.setAutoCommit(false);
        ConnectionManager.beginTransaction(connection);
        String class_stu="insert into class(class_id ,class_name ,courseID ,capacity ,teacher) values(?,?,?,?,?);";
        preparedStatement =connection.prepareStatement(class_stu);
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader("class.txt"))) {
            while (((line = bufferedReader.readLine()) != null)&&!line.equals("")){
                try{
                    each=line.split(";");
                    class_id++;
                    String temp=each[0]+";"+each[1];

                    classid_classname_courseid.put(temp,class_id);

                    preparedStatement.setInt(1,class_id);
                    preparedStatement.setObject(2,each[0]);
                    preparedStatement.setObject(3,each[1]);
                    preparedStatement.setInt(4,Integer.parseInt(each[2]));
                    preparedStatement.setObject(5,each[3]);
                    preparedStatement.addBatch();
                    count++;
                    if (count%500==0){
                        count=0;
                        preparedStatement.executeBatch();
                        preparedStatement.clearBatch();
                    }
                }catch (Exception e){
                    class_id--;
                    System.out.println("this is the class problem");
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println("file not find ");
        }
        preparedStatement.executeBatch();
        preparedStatement.clearBatch();
        //connection.commit();
        //preparedStatement.close();
        //statement.close();
        ConnectionManager.commitTransaction(connection);
        ConnectionManager.close(preparedStatement);
        //ConnectionManager.closeConnection();
    }
    public void insert_shedule() throws  Exception {
        String line=null;
        String each[];
        int class_id=0;
        long count=0;
        String temp=null;
       // getConnection();
       // ConnectionManager.getConnection();
        String schedule="insert into schedule(class_id  ,weekday , class_time ,location ,weeklist)  values(?,?,?,?,?); ";
        //connection.setAutoCommit(false);
        preparedStatement=connection.prepareStatement(schedule);
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader("schedule.txt"))) {
            while (((line = bufferedReader.readLine()) != null)&&!line.equals("")){
                try{
                    each=line.split(";");

                    temp=each[0]+";"+each[1];
                    class_id=classid_classname_courseid.get(temp);
                    preparedStatement.setInt(1,class_id);
                    preparedStatement.setInt(2,Integer.parseInt(each[2]));
                    preparedStatement.setObject(3,each[3]);
                    preparedStatement.setObject(4,each[4].trim());
                    preparedStatement.setObject(5,each[5]);
                    preparedStatement.addBatch();
                    count++;
                    if (count%500==0){
                        count=0;
                        preparedStatement.executeBatch();
                        preparedStatement.clearBatch();
                    }
                }catch ( Exception e){
                    System.out.println("this is the schedule problem ");
                }


            }



        } catch (FileNotFoundException e) {
            System.out.println("file not find ");
        }
        preparedStatement.executeBatch();
        preparedStatement.clearBatch();
        //connection.commit();
        //preparedStatement.close();
        //statement.close();
        ConnectionManager.commitTransaction(connection);
        ConnectionManager.close(preparedStatement);
        ConnectionManager.closeConnection();

    }

/*
    @Override
    public Integer call() throws Exception {
        insert_class();
        insert_shedule();
        return 2;
    }*/

    @Override
    public void run() {
        try {
            insert_class();
            insert_shedule();
            latch.countDown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
