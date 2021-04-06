import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Table_select_course {
  public static  ArrayList<unit_select_course> arrayList_select_course=new ArrayList<>();
    public  static HashMap<Integer,String> hashMap=new HashMap<>();
    public void add_data(){
        String line=null;
        String each[]=null;

        try(BufferedReader bufferedReader=new BufferedReader
                (new FileReader("D:\\databasepj2\\select_course.csv"))) {
            try{
                while ((line = bufferedReader.readLine()) != null&&!line.equals("")){
                    each=line.split(",");
                   for (int i=4;i<each.length;i++){
                       arrayList_select_course.add(new unit_select_course(each[0],each[i]));
                   }
                    //System.out.println(each[1]);
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
        Set<String> all_class=new HashSet<>();
        String line=null;
        String each[]=null;

        try(BufferedReader bufferedReader=new BufferedReader
                (new FileReader("D:\\databasepj2\\select_course.csv"))) {
            try{
                while ((line = bufferedReader.readLine()) != null&&!line.equals("")){
                    each=line.split(",");
                   if (Integer.parseInt(each[3])==sid){
                       for (int i=4;i<each.length;i++){
                           all_class.add(each[i]);
                       }
                       break;
                   }
                    //System.out.println(each[1]);
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


        Iterator<String> it = all_class.iterator();
        int have=0;
      while (it.hasNext()){
          have++;
          String temp=it.next();
          System.out.println(temp+" "+Table_course.hashMap.get(temp));
      }
        if (have==0){
            System.out.println("courseInformation can't get ");
        }

    }

    public void calculateCredit(int sid){
        String line=null;
        String each[]=null;
        int have=0;
        Set<String> all_class=new HashSet<>();
        try(BufferedReader bufferedReader=new BufferedReader
                (new FileReader("D:\\databasepj2\\select_course.csv"))) {
            try{
                while ((line = bufferedReader.readLine()) != null&&!line.equals("")){
                    each=line.split(",");
                    if (Integer.parseInt(each[3])==sid){
                        for (int i=4;i<each.length;i++){
                            all_class.add(each[i]);
                        }
                        break;
                    }
                    //System.out.println(each[1]);
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
        int count=0;

        Iterator<String> it = all_class.iterator();
        while (it.hasNext()){
            have++;
            String temp=it.next();
count+=Table_course.hashMap2.get(temp);
        }

        if (have==0){
            System.out.println("credit can nto find");
        }else {
            System.out.println(count);
        }
        System.out.println(count);
    }

}
