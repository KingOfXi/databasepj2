import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) throws Exception {

        //import data into database
        ExecutorService service= Executors.newFixedThreadPool(8);
        long startTime = System.currentTimeMillis();
        Create_tables tables =new Create_tables();
        tables.create_tables();
        CountDownLatch latch1 = new CountDownLatch(2);



Insert_college_student insert_college_student=new Insert_college_student(latch1);
service.execute(insert_college_student);

        System.out.println("1");
Insert_course insert_course =new Insert_course(latch1);
service.execute(insert_course);
        System.out.println("2");
latch1.await();
Thread.sleep(10000);
        System.out.println("first line is ok");





        CountDownLatch latch2=new CountDownLatch(3);
        Select_course select_course=new Select_course(latch2);service.execute(select_course);
 Insert_class_schedule insert_class_schedule =new Insert_class_schedule(latch2);
 service.execute(insert_class_schedule);
 Insert_prerequisite insert_prerequisite=new Insert_prerequisite(latch2);
 service.execute(insert_prerequisite);




 latch2.await();
        service.shutdown();


        long endTime = System.currentTimeMillis();
        System.out.println("程序运行时间：" + (endTime - startTime) + "ms");

      /*  Table_course table_course=new Table_course();
        table_course.add_data();

        Table_class table_class=new Table_class();
        table_class.add_data();

        Table_student table_student=new Table_student();
        table_student.add_data();

        Table_college table_college=new Table_college();
        table_college.add_data();

        Table_select_course table_select_course=new Table_select_course();
     //   table_select_course.add_data();


        long startTime_selectcoursebyid =System.nanoTime();
        System.out.println(table_course.selectCourseByIDBF("ESS312"));
long endTime_selectcoursebyid =System.nanoTime();
        System.out.println(((endTime_selectcoursebyid-startTime_selectcoursebyid)/(double)1000000));
        //0.3386ms  时间



        long startTime_selectClassByCourseID =System.nanoTime();
        table_class.selectClassByCourseID("IPE105");
        long endTime_selectClassByCourseID=System.nanoTime();
        System.out.println((endTime_selectClassByCourseID-startTime_selectClassByCourseID)/(double)1000000);
        //0.6982ms



        long startTime_selectClassByCourseIDAndclassName =System.nanoTime();
       table_class.selectClassByCourseIDAndclassName("IPE105","中文6班");
        long endTime_selectClassByCourseIDAndclassName=System.nanoTime();
        System.out.println((endTime_selectClassByCourseIDAndclassName-startTime_selectClassByCourseIDAndclassName)/(double)1000000);



        long startTime_selectCourseByDept=System.nanoTime();
        table_course.selectCourseByDept("计算机科学与工程系");
        long endTime_selectCourseByDept=System.nanoTime();
        System.out.println((endTime_selectCourseByDept-startTime_selectCourseByDept)/(double)1000000);





        long startTime_selectStudentBySID=System.nanoTime();
        table_student.selectStudentBySID(13326182);
        long endTime_selectStudentBySID=System.nanoTime();
        System.out.println((endTime_selectStudentBySID-startTime_selectStudentBySID)/(double)1000000);


        long startTime_printCourseInformation=System.nanoTime();
        table_select_course.printCourseInformation(13326182);
        long endTime_printCourseInformation=System.nanoTime();
        System.out.println((endTime_printCourseInformation-startTime_printCourseInformation)/(double)1000000);




long startTime_calculateCredit=System.nanoTime();
table_select_course.calculateCredit(13326182);
long endTime_calculateCredit =System.nanoTime();
        System.out.println((endTime_calculateCredit-startTime_calculateCredit)/(double)1000000);*/

    }

}
