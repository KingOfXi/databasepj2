import org.postgresql.util.PSQLException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class update_data {
    Map<String,Integer> college_collegeid =new HashMap<>();
    Map<String,Integer> classid_classname_courseid=new HashMap<>();
    Set<String> courseID=new HashSet<>();
public Connection connection=null;


    private   String host = "localhost";
    private    String dbname = "fun";
    private   String user = "postgres";
    private    String pwd = "admin";
    private   String port = "5432";
    PreparedStatement preparedStatement=null;
    Statement statement=null;
    ResultSet resultSet=null;

    public void getConnection() throws Exception {
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
    }
public void create_tables() throws Exception{
    getConnection();
    String create_college="create table college(id serial primary key ,namec Text not null, namee Text not null );";
    String create_student ="create table student(sid int , name Text not null , college_id int  , sex Text ," +
            "constraint student__fk  foreign key (college_id) references college  ,primary key(sid));";
    String create_course="create table course(courseID Text ,courseName Text not null, credit float not null, hour int ,dept Text,primary key(courseID));";
    String select_course="create table select_course(sid int ,course_id Text , constraint sid__fk foreign key(sid) references student "
            +", constraint course_id__fk foreign key(course_id) references course, primary key(sid,course_id));";
    String class_stu="create table class(class_id int primary key,class_name Text not null,courseID Text ,capacity int ,teacher Text,foreign key(courseID) references course);";  //加上teacher 会有麻烦,, unique(class_name,courseID) 傻逼玩意不满足去掉了
    String schedule="create table schedule(class_id int ,weekday int, class_time Text,location Text,weeklist Text,primary key(class_id,weekday,class_time,location,weeklist)," +
            "constraint schedule_class_id_fk foreign key(class_id) references class);";
    String prerequisite="create table prerequisite(courseID Text , prerequisite Text,foreign key(courseID) references course, primary key(courseID,prerequisite) );";


    try {
        connection.setAutoCommit(false);
        statement=connection.createStatement();
        statement.addBatch(create_college);
        statement.addBatch(create_student);
        statement.addBatch(create_course);
        statement.addBatch(select_course);
        statement.addBatch(class_stu);
        statement.addBatch(schedule);
        statement.addBatch(prerequisite);

        statement.executeBatch();
        connection.commit();

        statement.close();
        connection.close();



    } catch (SQLException throwables) {
        throwables.printStackTrace();
    }
}
public void insert_college() throws Exception {
    String line=null;
    String each[];
    String whole_college_name=null;
    String cname=null;
    String ename=null;
    int id=0;
    getConnection();
    String insert_college="insert into  college(id,namec,namee) values(?,?,?);";
    connection.setAutoCommit(false);
    preparedStatement=connection.prepareStatement(insert_college);
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
    preparedStatement.executeBatch();
    preparedStatement.clearBatch();
    connection.commit();
preparedStatement.close();
    statement.close();
}

public void insert_student()  throws  Exception {
    String insert_student="insert into student(sid,name,sex,college_id) values(?,?,?,?);";
    String line=null;
    String each[]=null;
    String name=null;
    String sex=null;
    int sid=0;
    int college_id=0;
  getConnection();
    connection.setAutoCommit(false);
    preparedStatement=connection.prepareStatement(insert_student);
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
               preparedStatement.addBatch();
               count++;

               if (count%500==0){
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
    preparedStatement.executeBatch();
    preparedStatement.clearBatch();
    connection.commit();
    preparedStatement.close();
    statement.close();

}
    public void insert_course() throws  Exception {
        String insert_course="insert into course(courseID,courseName,credit,hour,dept) values(?,?,?,?,?);";
        String line=null;
        String each[]=null;

        getConnection();
        connection.setAutoCommit(false);
        preparedStatement=connection.prepareStatement(insert_course);
        long count=0;
        try (BufferedReader bufferedReader=new BufferedReader(new FileReader("course.txt"))){
           try{
               while ((line=bufferedReader.readLine())!=null&&!line.equals("")){
                   each=line.split(";");

                   preparedStatement.setObject(1,each[0]);
                   preparedStatement.setObject(2,each[1]);
                   preparedStatement.setFloat(3,Float.parseFloat(each[2]));
                   preparedStatement.setInt(4,Integer.parseInt(each[3]));
                   preparedStatement.setObject(5,each[4]);
                   preparedStatement.addBatch();
                   count++;
                   if (count%10000==0){
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
        connection.commit();
        preparedStatement.close();
        statement.close();
    }
public void insert_select_course() throws Exception {
    String insert_select_course="insert into select_course(sid,course_id) values(?,?);";
    String line=null;
    String each[]=null;
    getConnection();
    connection.setAutoCommit(false);
    preparedStatement=connection.prepareStatement(insert_select_course);
    long count=0;
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
    connection.commit();
    preparedStatement.close();
    statement.close();
}
public void insert_class() throws  Exception {
    String line=null;
    String each[];
    int class_id=0;
    long count=0;
   getConnection();
   connection.setAutoCommit(false);
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
    connection.commit();
    preparedStatement.close();
    statement.close();
}
public void insert_shedule() throws  Exception {
    String line=null;
    String each[];
    int class_id=0;
    long count=0;
    String temp=null;
   getConnection();
   String schedule="insert into schedule(class_id  ,weekday , class_time ,location ,weeklist)  values(?,?,?,?,?); ";
   connection.setAutoCommit(false);
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
    connection.commit();
    preparedStatement.close();
    statement.close();
}
public void insert_prerequisite()  throws  Exception {
   getConnection();
    String line=null;
    String each[];
    long count=0;
    String prerequisite="insert into prerequisite(courseID,prerequisite)  values(?,?);";
    getConnection();
    connection.setAutoCommit(false);
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
    connection.commit();
    preparedStatement.close();
    statement.close();
}
}
