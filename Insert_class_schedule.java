import java.io.*;
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

    public void insert_class() throws  Exception {
        String line=null;
        String each[];
        int class_id=0;
        long count=0;
      connection= ConnectionManager.getConnection();
        ConnectionManager.beginTransaction(connection);

        String class_stu="insert into class(class_id ,class_name ,courseID ,capacity ) values(?,?,?,?);";
        preparedStatement =connection.prepareStatement(class_stu);
        BufferedWriter bufferedWriter=new BufferedWriter(new FileWriter("class.sql"));
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader("class.txt"))) {
            while (((line = bufferedReader.readLine()) != null)&&!line.equals("")){
                try{
                    each=line.split(";");
                    class_id++;
                    String temp=each[0]+";"+each[1].trim();

                    classid_classname_courseid.put(temp,class_id);
                    preparedStatement.setInt(1,class_id);
                    preparedStatement.setObject(2,each[0]);
                    preparedStatement.setObject(3,each[1].trim());
                    preparedStatement.setInt(4,Integer.parseInt(each[2]));
                    bufferedWriter.write(preparedStatement.toString()+";");
                    bufferedWriter.newLine();
                 //   preparedStatement.setObject(5,each[3]);
                    preparedStatement.addBatch();
                    count++;
                    if (count%500==0){
                        bufferedWriter.flush();
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


        bufferedWriter.flush();bufferedWriter.close();
        preparedStatement.executeBatch();
        preparedStatement.clearBatch();

        ConnectionManager.commitTransaction(connection);
        ConnectionManager.close(preparedStatement);

        String teacher ="insert into teachers (class_id,teacher) values(?,?);";
        preparedStatement =connection.prepareStatement(teacher);

        int now_class_id=0;//each[3] is teacher
        String each_temp[]=null;
        BufferedWriter bufferedWriter1=new BufferedWriter(new FileWriter("teachers.sql"));
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader("class.txt"))){
            while (((line = bufferedReader.readLine()) != null)&&!line.equals("")){
                try{
                    each=line.split(";");
                    String temp=each[0]+";"+each[1].trim();

                   now_class_id=  classid_classname_courseid.get(temp);
                   each_temp=each[3].split(",");
                   for (int i=0;i<each_temp.length;i++){
                       preparedStatement.setInt(1,now_class_id);
                       preparedStatement.setObject(2,each_temp[i]);
                       bufferedWriter1.write(preparedStatement.toString()+";");
                       bufferedWriter1.newLine();
                       preparedStatement.addBatch();
                       count++;
                       if (count%500==0){
                           bufferedWriter1.flush();
                           count=0;
                           preparedStatement.executeBatch();
                           preparedStatement.clearBatch();
                       }
                   }

                }catch (Exception e){
                    System.out.println("this is the teacher problem");
                }
            }
        }catch (FileNotFoundException e) {
            System.out.println("file not find ");
        }
        bufferedWriter1.flush();bufferedWriter1.close();
        preparedStatement.executeBatch();
        preparedStatement.clearBatch();

        ConnectionManager.commitTransaction(connection);
        ConnectionManager.close(preparedStatement);

    }
    public void insert_shedule() throws  Exception {
        String line=null;
        String each[]=null;
        int class_id=0;
        long count=0;
        String temp=null;

        String schedule="insert into schedule(class_id  ,weekday , class_time ,location ,weeklist)  values(?,?,?,?,?); ";
        preparedStatement=connection.prepareStatement(schedule);
        BufferedWriter bufferedWriter =new BufferedWriter(new FileWriter("schedule.sql"));
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader("schedule.txt"))) {
            while (((line = bufferedReader.readLine()) != null)&&!line.equals("")){
                try{
                    each=line.split(";");

                    temp=each[0]+";"+each[1].trim();
                    class_id=classid_classname_courseid.get(temp);
                    preparedStatement.setInt(1,class_id);
                    preparedStatement.setInt(2,Integer.parseInt(each[2]));
                    preparedStatement.setObject(3,each[3]);
                    preparedStatement.setObject(4,each[4].trim());
                    preparedStatement.setObject(5,each[5]);
                    bufferedWriter.write(preparedStatement.toString()+";");
                    bufferedWriter.newLine();
                    preparedStatement.addBatch();
                    count++;
                    if (count%500==0){
                        bufferedWriter.flush();
                        count=0;
                        preparedStatement.executeBatch();
                        preparedStatement.clearBatch();
                    }
                }catch ( Exception e){
                    System.out.println("this is the schedule problem "+each[2]);
                }


            }



        } catch (FileNotFoundException e) {
            System.out.println("file not find ");
        }
        bufferedWriter.flush();bufferedWriter.close();




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
