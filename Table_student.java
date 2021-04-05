import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Table_student {
ArrayList<unit_student> arrayList=new ArrayList<>();
    public void add_data(){
        String line=null;
        String each[]=null;
        try(BufferedReader bufferedReader=new BufferedReader
                (new FileReader("C:\\Users\\admin\\fun_public_student.csv"))) {
            try{
                while ((line = bufferedReader.readLine()) != null){
                    each=line.split(",");
arrayList.add(new unit_student(Integer.parseInt(each[0]),each[1],Integer.parseInt(each[2]),each[3]));
                }
            }catch (Exception e){
                System.out.println("this is college problem");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void selectStudentBySID(int sid){
        int have=0;
        for (int i=0;i<arrayList.size();i++){
            if (arrayList.get(i).getId()==sid){
                System.out.println(arrayList.get(i).getCollege_id());
                System.out.println(arrayList.get(i).getName()+" "+arrayList.get(i).getSex()+" "+sid+" "+Table_college.id_namec_namee.get(arrayList.get(i).getCollege_id()));
                have=1;
                break;
            }
        }
        if (have==0){
            System.out.println("sorry not find student by sid ");
        }
    }
}
