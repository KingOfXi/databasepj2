public class unit_class {
    int class_id=0;
    String class_name=null;
String courseid=null;
int capacity=0;

    public unit_class(int class_id, String class_name, String courseid, int capacity) {
        this.class_id = class_id;
        this.class_name = class_name;
        this.courseid = courseid;
        this.capacity = capacity;
    }

    public unit_class() {
    }

    public int getClass_id() {
        return class_id;
    }

    public void setClass_id(int class_id) {
        this.class_id = class_id;
    }

    public String getClass_name() {
        return class_name;
    }

    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }

    public String getCourseid() {
        return courseid;
    }

    public void setCourseid(String courseid) {
        this.courseid = courseid;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}
