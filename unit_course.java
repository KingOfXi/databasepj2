public class unit_course {
    private String couseID=null;
    private String courseName=null;
         private  float credit=0;
int hour=0;
String dept=null;

    public unit_course(String couseID, String courseName, float credit, int hour, String dept) {
        this.couseID = couseID;
        this.courseName = courseName;
        this.credit = credit;
        this.hour = hour;
        this.dept = dept;
    }
    public unit_course(){}

    public String getCouseID() {
        return couseID;
    }

    public void setCouseID(String couseID) {
        this.couseID = couseID;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public float getCredit() {
        return credit;
    }

    public void setCredit(float credit) {
        this.credit = credit;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }
}
