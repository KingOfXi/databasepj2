import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {

        long startTime = System.currentTimeMillis();
update_data a=new update_data();
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
a.insert_prerequisite();

        long endTime = System.currentTimeMillis();
        System.out.println("程序运行时间：" + (endTime - startTime) + "ms");
    }
}
