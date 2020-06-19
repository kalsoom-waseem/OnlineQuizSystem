package sample;

import java.util.SplittableRandom;

public class admin extends User{
    private String post;

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public admin(String name, String username, String password, long id, String post) {
        super(name, username, password, id);
        this.post = post;
    }
}
