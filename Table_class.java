import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Table_class {
    ArrayList<unit_class> arrayList=new ArrayList<>();
    public void add_data(){
        String line=null;
        String each[]=null;
        try(BufferedReader bufferedReader=new BufferedReader
                (new FileReader("C:\\Users\\admin\\fun_public_class.csv"))) {
            try{
                while ((line = bufferedReader.readLine()) != null&&!line.equals("")){
                    each=line.split(",");
arrayList.add(new unit_class(Integer.parseInt(each[0]),each[1],each[2],Integer.parseInt(each[3])));
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
    public void selectClassByCourseID(String courseid){
        int k=0;
for (int i=0;i<arrayList.size();i++) {
    if (arrayList.get(i).courseid.equals(courseid)){
        k++;
        System.out.println(arrayList.get(i).class_name+" "+arrayList.get(i).courseid+" "+arrayList.get(i).capacity);
    }
        }
if (k==0){
    System.out.println("sorry there is no such class");
}
    }
    public void selectClassByCourseIDAndclassName(String courseid, String class_name){
        int have=0;
        for (int i=0;i<arrayList.size();i++){
            if (arrayList.get(i).courseid.equals(courseid)&&arrayList.get(i).class_name.equals(class_name)){
                have++;
                System.out.println(arrayList.get(i).class_name+" "+arrayList.get(i).courseid+" "+arrayList.get(i).capacity);
                break;
            }
        }
if (have==0){
    System.out.println("sorry we can't find it by courseid and calssname");
}

    }
}
