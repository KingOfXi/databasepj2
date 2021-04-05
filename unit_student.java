public class unit_student {
    private  int id=0;
    private  String name=null;
    private  int college_id=0;
    private String sex=null;

    public unit_student(int id, String name, int college_id, String sex) {
        this.id = id;
        this.name = name;
        this.college_id = college_id;
        this.sex = sex;
    }
    public unit_student(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCollege_id() {
        return college_id;
    }

    public void setCollege_id(int college_id) {
        this.college_id = college_id;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
