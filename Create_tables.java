import java.io.BufferedWriter;
import java.io.FileWriter;
import java.sql.*;

public class Create_tables {
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
        BufferedWriter bufferedWriter=new BufferedWriter(new FileWriter("tables.sql"));
        String create_college="create table college(id serial primary key ,namec Text , namee Text ,unique (namec,namee));";
        String create_student ="create table student(sid int , name Text not null , college_id int  , sex Text ," +
                "constraint student__fk  foreign key (college_id) references college  ,primary key(sid));";
        String create_course="create table course(courseID Text ,courseName Text not null," +
                " credit float not null, hour int ,dept Text,primary key(courseID));";

        String select_course="create table select_course(sid int ,course_id Text , " +
                "constraint sid__fk foreign key(sid) references student "
                +", constraint course_id__fk foreign key(course_id) references course, primary key(sid,course_id));";
        String class_stu="create table class(class_id int primary key,class_name" +
                " Text not null,courseID Text ,capacity int ,foreign key(courseID) references course,unique(class_name,courseID,capacity));";  //??????teacher ????????????,, unique(class_name,courseID) ??????????????????????????????
        String schedule="create table schedule(class_id int ,weekday int, " +
                "class_time Text,location Text,weeklist Text,primary key(class_id,weekday,class_time,location,weeklist)," +
                "constraint schedule_class_id_fk foreign key(class_id) references class);";
        String prerequisite="create table prerequisite" +
                "(courseID Text not null, prerequisiteID1 Text default  null,prerequisiteID2 Text default  null,prerequisiteID3 Text default  null,prerequisiteID4 Text default  null," +
                "pre_id int,foreign key(courseID) references course, primary key(pre_id)," +
                "unique(courseID,prerequisiteID1,prerequisiteID2,prerequisiteID3,prerequisiteID4) );";

        String teacher="create table teachers\n" +
                "(class_id int,teacher Text,foreign key(class_id) references class ,primary key(class_id, teacher)); ";
        try {
            connection.setAutoCommit(false);
            statement=connection.createStatement();
            statement.addBatch(create_college);
            statement.addBatch(create_student);
            statement.addBatch(create_course);
            statement.addBatch(select_course);
            statement.addBatch(class_stu);
            //????????????
            statement.addBatch(teacher);
            statement.addBatch(schedule);
            statement.addBatch(prerequisite);

            statement.executeBatch();
            connection.commit();

            statement.close();
            connection.close();
bufferedWriter.write(create_college+";");bufferedWriter.newLine();
bufferedWriter.write(create_student+";");bufferedWriter.newLine();
bufferedWriter.write(create_course+";");bufferedWriter.newLine();
bufferedWriter.write(select_course+";"); bufferedWriter.newLine();
bufferedWriter.write(class_stu+";");bufferedWriter.newLine();
bufferedWriter.write(teacher+";"); bufferedWriter.newLine();
bufferedWriter.write(schedule+";");bufferedWriter.newLine();
bufferedWriter.write(prerequisite+";");bufferedWriter.newLine();
bufferedWriter.flush();
bufferedWriter.close();


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
