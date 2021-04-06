import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Table_course {
public static ArrayList<unit_course> course_arrayList=new ArrayList<>();
public static HashMap<String,String> hashMap=new HashMap<>();
public static HashMap<String ,Float> hashMap2=new HashMap<>();
    public void add_data(){
        String line=null;
        String each[]=null;
        try(BufferedReader bufferedReader=new BufferedReader
                (new FileReader("C:\\Users\\admin\\fun_public_course.csv"))) {
            try{
                while ((line = bufferedReader.readLine()) != null&&!line.equals("")){
                    each=line.split(",");
                    course_arrayList.add(new unit_course(each[0],each[1],Float.parseFloat(each[2]),Integer.parseInt(each[3]),each[4]));
hashMap.put(each[0],each[1]+" "+each[2]+" "+each[3]+" "+each[4]);
hashMap2.put(each[0],Float.parseFloat(each[2]));
                }
            }catch (Exception e){
                //System.out.println("this is college problem");
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public String selectCourseByIDBF(String courseId){
        if (hashMap.containsKey(courseId)){
            return hashMap.get(courseId);
        }
             return "sorry, we can't find related courseid";
    }
    public void selectCourseByDept(String Dept){
        int k=0;
        for (int i=0;i<course_arrayList.size();i++){
            if (course_arrayList.get(i).dept.equals(Dept)){
                k++;
                System.out.println(course_arrayList.get(i).getCouseID()+" "+course_arrayList.get(i).getCourseName()+" "
                +course_arrayList.get(i).getCredit()+" "+course_arrayList.get(i).getHour()+" "+course_arrayList.get(i).dept);
            }
        }
        if (k==0){
            System.out.println("sorry i can't find CourseByDept");
        }
    }
}
