public class unit_prerequisite {
    String course_ID=null;
   String prerequisiteID1=null;
    String prerequisiteID2=null;
    String prerequisiteID3=null;
    String prerequisiteID4=null;

    public unit_prerequisite(String course_ID, String prerequisiteID1, String prerequisiteID2, String prerequisiteID3, String prerequisiteID4) {
        this.course_ID = course_ID;
        this.prerequisiteID1 = prerequisiteID1;
        this.prerequisiteID2 = prerequisiteID2;
        this.prerequisiteID3 = prerequisiteID3;
        this.prerequisiteID4 = prerequisiteID4;
    }

    public unit_prerequisite() {
    }

    public String getCourse_ID() {
        return course_ID;
    }

    public void setCourse_ID(String course_ID) {
        this.course_ID = course_ID;
    }

    public String getPrerequisiteID1() {
        return prerequisiteID1;
    }

    public void setPrerequisiteID1(String prerequisiteID1) {
        this.prerequisiteID1 = prerequisiteID1;
    }

    public String getPrerequisiteID2() {
        return prerequisiteID2;
    }

    public void setPrerequisiteID2(String prerequisiteID2) {
        this.prerequisiteID2 = prerequisiteID2;
    }

    public String getPrerequisiteID3() {
        return prerequisiteID3;
    }

    public void setPrerequisiteID3(String prerequisiteID3) {
        this.prerequisiteID3 = prerequisiteID3;
    }

    public String getPrerequisiteID4() {
        return prerequisiteID4;
    }

    public void setPrerequisiteID4(String prerequisiteID4) {
        this.prerequisiteID4 = prerequisiteID4;
    }
}
