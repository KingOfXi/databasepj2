import java.io.*;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

public class Insert_college_student implements Runnable {
    public Connection connection=null;
CountDownLatch latch=null;
    Map<String,Integer> college_collegeid =new HashMap<>();
  /*  private   String host = "localhost";
    private    String dbname = "fun";
    private   String user = "postgres";
    private    String pwd = "admin";
    private   String port = "5432";*/
    PreparedStatement preparedStatement=null;
    Statement statement=null;
    ResultSet resultSet=null;
public Insert_college_student(){}
public Insert_college_student(CountDownLatch a){latch=a;}
    public void insert_college() throws Exception {
        String line=null;
        String each[];
        String whole_college_name=null;
        String cname=null;
        String ename=null;
        int id=0;
       connection=  ConnectionManager.getConnection();
        String insert_college="insert into  college(id,namec,namee) values(?,?,?);";
       // connection.setAutoCommit(false);
        ConnectionManager.beginTransaction(connection);
        preparedStatement=connection.prepareStatement(insert_college);
        BufferedWriter bufferedWriter=new BufferedWriter(new FileWriter("college.sql"));
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader("select_course.csv"))) {
            try{
                while ((line = bufferedReader.readLine()) != null){
                    each=line.split(",");
                    whole_college_name=each[2];
                    cname=whole_college_name.substring(0,whole_college_name.indexOf('('));
                    ename=whole_college_name.substring(whole_college_name.indexOf('(')+1,whole_college_name.length()-1);

                    if (!college_collegeid.containsKey(cname)){
                        id++;
                        college_collegeid.put(cname,id);
                        preparedStatement.setInt(1,id);
                        preparedStatement.setObject(2,cname);
                        preparedStatement.setObject(3,ename);
                       bufferedWriter.write(preparedStatement.toString()+";");
                       bufferedWriter.newLine();
                        preparedStatement.addBatch();
                        if(id%10000==0){ //1万次一条，或者最后一次进行提交。
                            preparedStatement.executeBatch();
                            preparedStatement.clearBatch(); /**清除缓存*/
                        }
                    }
                }
            }catch (Exception e){
                System.out.println("college problem");
            }





        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }



        bufferedWriter.flush();
        bufferedWriter.close();
        preparedStatement.executeBatch();
        preparedStatement.clearBatch();
        //connection.commit();
        //this.preparedStatement.close();
        //this.statement.close();
        ConnectionManager.commitTransaction(connection);
        ConnectionManager.close(preparedStatement);
        //ConnectionManager.closeConnection();
    }
    public void insert_student()  throws  Exception {
        String insert_student="insert into student(sid,name,sex,college_id) values(?,?,?,?);";
        String line=null;
        String each[]=null;
        String name=null;
        String sex=null;
        int sid=0;
        int college_id=0;
       // getConnection();
       // connection.setAutoCommit(false);
        //ConnectionManager.beginTransaction(connection);
        preparedStatement=connection.prepareStatement(insert_student);

        BufferedWriter bufferedWriter=new BufferedWriter(new FileWriter("student.sql"));
        long count=0;
        try (BufferedReader bufferedReader=new BufferedReader(new FileReader("select_course.csv"))){
            try{
                while ((line=bufferedReader.readLine())!=null){
                    each=line.split(",");
                    name=each[0];
                    sex=each[1];
                    sid=Integer.parseInt(each[3]);

                    college_id= this.college_collegeid.get(each[2].substring(0,each[2].indexOf('(')));
                    preparedStatement.setInt(1,sid);
                    preparedStatement.setObject(2,name);
                    preparedStatement.setObject(3,sex);
                    preparedStatement.setInt(4,college_id);
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
                }
            }catch (Exception e){
                System.out.println("student problem");
            }

        }catch (IOException e){
            System.out.println("file not find");
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

    @Override
    public void run() {

        try {
            insert_college();
            insert_student();
            latch.countDown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

  /*  @Override
    public Integer call() throws Exception {
        insert_college();
        insert_student();
        return 5;
    }*/
}
