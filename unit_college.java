public class unit_college {
    private  int id=0;
    private  String namec=null;
    private  String namee=null;

    public unit_college(int id, String namec, String namee) {
        this.id = id;
        this.namec = namec;
        this.namee = namee;
    }

    public unit_college() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNamec() {
        return namec;
    }

    public void setNamec(String namec) {
        this.namec = namec;
    }

    public String getNamee() {
        return namee;
    }

    public void setNamee(String namee) {
        this.namee = namee;
    }
}
