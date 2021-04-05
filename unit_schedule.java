public class unit_schedule {
    int class_id=0;
    int weekday=0;
    String class_time=null;
    String location=null;
    String week_list=null;

    public unit_schedule(int class_id, int weekday, String class_time, String location, String week_list) {
        this.class_id = class_id;
        this.weekday = weekday;
        this.class_time = class_time;
        this.location = location;
        this.week_list = week_list;
    }

    public unit_schedule() {
    }

    public int getClass_id() {
        return class_id;
    }

    public void setClass_id(int class_id) {
        this.class_id = class_id;
    }

    public int getWeekday() {
        return weekday;
    }

    public void setWeekday(int weekday) {
        this.weekday = weekday;
    }

    public String getClass_time() {
        return class_time;
    }

    public void setClass_time(String class_time) {
        this.class_time = class_time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getWeek_list() {
        return week_list;
    }

    public void setWeek_list(String week_list) {
        this.week_list = week_list;
    }
}
