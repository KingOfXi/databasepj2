public class unit_select_course {
    int sid=0;
    String student_name=null;
    String course_id=null;

    public unit_select_course(int sid, String course_id) {
        this.sid = sid;
        this.course_id = course_id;
    }
    public unit_select_course(String student_name, String course_id) {
      this.student_name=student_name;
        this.course_id = course_id;
    }
    public unit_select_course(){}

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public String getCourse_id() {
        return course_id;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
    }
}
