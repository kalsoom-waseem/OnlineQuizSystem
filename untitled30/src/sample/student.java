package sample;

public class student extends User{
    private String semester;

    public student(String semester) {
        this.semester = semester;
    }

    public student(String name, String username, String password, long id, String semester) {
        super(name, username, password, id);
        this.semester = semester;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }


}
