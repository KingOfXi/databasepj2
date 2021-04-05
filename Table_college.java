import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Table_college {
HashMap<Integer,String> id_namec=new HashMap<>();
HashMap<String,String> id_namee=new HashMap<>();
public static HashMap<Integer,String> id_namec_namee=new HashMap<>();
ArrayList<unit_college> arrayList=new ArrayList<>();
public void add_data(){
String line=null;
String each[]=null;
try(BufferedReader bufferedReader=new BufferedReader
        (new FileReader("C:\\Users\\admin\\fun_public_college.csv"))) {
               try{
                   while ((line = bufferedReader.readLine()) != null){
                       each=line.split(",");
                       arrayList.add(new unit_college(Integer.parseInt(each[0]),each[1],each[2]));
id_namec.put(Integer.parseInt(each[0]),each[1]);
id_namee.put(each[1],each[2]);
id_namec_namee.put(Integer.parseInt(each[0]),each[1]+"("+each[2]+")");
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

}

