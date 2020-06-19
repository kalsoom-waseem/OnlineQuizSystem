package sample;

public class teacher extends User {
    private String subject;

    public teacher(String subject) {
        this.subject = subject;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public teacher(String name, String username, String password, long id, String subject) {
        super(name, username, password, id);
        this.subject = subject;
    }
}
