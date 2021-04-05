import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Table_select_course {
    ArrayList<unit_select_course> arrayList=new ArrayList<>();
    public  static HashMap<Integer,String> hashMap=new HashMap<>();
    public void add_data(){
        String line=null;
        String each[]=null;

        try(BufferedReader bufferedReader=new BufferedReader
                (new FileReader("C:\\Users\\admin\\fun_public_select_course.csv"))) {
            try{
                while ((line = bufferedReader.readLine()) != null&&!line.equals("")){
                    each=line.split(",");
                    arrayList.add(new unit_select_course(Integer.parseInt(each[0]),each[1]));
                    hashMap.put(Integer.parseInt(each[0]),each[1]);
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
    public void printCourseInformation(int sid){
        int have=0;
        for (int i=0;i<arrayList.size();i++){
            if (arrayList.get(i).getSid()==sid){
                have++;
                System.out.println(arrayList.get(i).getCourse_id()+" "+Table_course.hashMap.get(arrayList.get(i).getCourse_id()) );
            }
        }
        if (have==0){
            System.out.println("courseInformation can't get ");
        }

    }

    public void calculateCredit(int sid){
        int have=0;
        float count=0;
        for (int i=0;i<arrayList.size();i++){
            if (arrayList.get(i).getSid()==sid){
                have++;
               count+= Table_course.hashMap2.get(arrayList.get(i).getCourse_id());

            }
        }
        if (have==0){
            System.out.println("credit can nto find");
        }else {
            System.out.println(count);
        }
    }
}
