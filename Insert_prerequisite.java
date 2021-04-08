import com.sun.javafx.scene.control.skin.FXVK;

import java.io.*;
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

    public void insert_prerequisite()  throws  Exception {
        //getConnection();
       Connection connection= ConnectionManager.getConnection();
        String line=null;
        String each[]=null;
        long count=0;
        String prerequisite="insert into prerequisite(courseID,prerequisiteID1,prerequisiteID2,prerequisiteID3,prerequisiteID4,pre_id)  values(?,?,?,?,?,?);";
        connection.setAutoCommit(false);
        ConnectionManager.beginTransaction(connection);
        preparedStatement=connection.prepareStatement(prerequisite);
        int now_id=0;
    //    BufferedWriter bufferedWriter =new BufferedWriter(new FileWriter("prerequisite.sql"));
        try(BufferedReader bufferedReader=new BufferedReader(new FileReader("prerequisite.csv"))){
            try{
                while (((line = bufferedReader.readLine()) != null)&&!line.equals("")){
                    now_id++;
                    each=line.split(",",-1);
                    System.out.println(each.length);

                    preparedStatement.setObject(1,each[0].trim());
                    for (int i=1;i<5;i++){
                      if (each[i].equals("")){
                          preparedStatement.setNull(i+1,Types.LONGVARCHAR);
                      }else {
                          preparedStatement.setObject(i+1,each[i]);
                      }
                    }
                   preparedStatement.setInt(6,now_id);
                //    bufferedWriter.write(preparedStatement.toString()+";");
                //    bufferedWriter.newLine();
                    preparedStatement.addBatch();
                    count++;
                    if (count%500==0){
                 //       bufferedWriter.flush();
                        count=0;
                        preparedStatement.executeBatch();
                        preparedStatement.clearBatch();
                    }

                }
            }catch (Exception e){
                System.out.println("this is the prerequisite problem ");
                e.printStackTrace();
                /*System.out.println(each[0]+" "+each[1]);*/
            }



        }catch (FileNotFoundException e) {
            System.out.println("file not find ");
        }
      //  bufferedWriter.flush();
      //  bufferedWriter.close();
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
