public class unit_teacher {
    int class_id=0;
    String teacher=null;

    public unit_teacher(int class_id, String teacher) {
        this.class_id = class_id;
        this.teacher = teacher;
    }

    public unit_teacher() {
    }

    public int getClass_id() {
        return class_id;
    }

    public void setClass_id(int class_id) {
        this.class_id = class_id;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }
}
