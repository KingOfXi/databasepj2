import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

public class Main {
    public static void main(String[] args) throws Exception {

        long startTime = System.currentTimeMillis();
        Create_tables tables =new Create_tables();
        tables.create_tables();
        CountDownLatch latch1 = new CountDownLatch(2);
        CountDownLatch latch2=new CountDownLatch(3);
        ExecutorService service= Executors.newFixedThreadPool(8);
Insert_college_student insert_college_student=new Insert_college_student(latch1);
service.execute(insert_college_student);
        System.out.println("1");
Insert_course insert_course =new Insert_course(latch1);
service.execute(insert_course);
        System.out.println("2");
latch1.await();
        System.out.println("first line is ok");
        Select_course select_course=new Select_course(latch2);service.execute(select_course);
 Insert_class_schedule insert_class_schedule =new Insert_class_schedule(latch2);
 service.execute(insert_class_schedule);
 Insert_prerequisite insert_prerequisite=new Insert_prerequisite(latch2);
 service.execute(insert_prerequisite);



 latch2.await();
        service.shutdown();
/*update_data a=new update_data();
a.create_tables();
        System.out.println("1");
a.insert_college();
        System.out.println("2");
a.insert_student();
        System.out.println("3");
a.insert_course();
        System.out.println("4");
a.insert_select_course();
        System.out.println("5");
a.insert_class();
        System.out.println("6");
a.insert_shedule();
        System.out.println("7");
a.insert_prerequisite();*/

        long endTime = System.currentTimeMillis();
        System.out.println("程序运行时间：" + (endTime - startTime) + "ms");
    }
}
