package sample;

import java.io.Serializable;

public class Mcq implements Serializable {
    private String body,op1,op2,op3,op4;
    private long id;
    private int cop;

    public Mcq() {
    }

    public Mcq(String body, String op1, String op2, String op3, String op4, long id, int cop) {
        this.body = body;
        this.op1 = op1;
        this.op2 = op2;
        this.op3 = op3;
        this.op4 = op4;
        this.id = id;
        this.cop = cop;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getOp1() {
        return op1;
    }

    public void setOp1(String op1) {
        this.op1 = op1;
    }

    public String getOp2() {
        return op2;
    }

    public void setOp2(String op2) {
        this.op2 = op2;
    }

    public String getOp3() {
        return op3;
    }

    public void setOp3(String op3) {
        this.op3 = op3;
    }

    public String getOp4() {
        return op4;
    }

    public void setOp4(String op4) {
        this.op4 = op4;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getCop() {
        return cop;
    }

    public void setCop(int cop) {
        this.cop = cop;
    }

    @Override
    public String toString() {
        return "Mcq{" +
                "body='" + body + '\'' +
                ", op1='" + op1 + '\'' +
                ", op2='" + op2 + '\'' +
                ", op3='" + op3 + '\'' +
                ", op4='" + op4 + '\'' +
                ", id=" + id +
                ", cop=" + cop +
                '}';
    }
}

