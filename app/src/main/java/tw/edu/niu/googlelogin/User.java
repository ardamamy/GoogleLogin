package tw.edu.niu.googlelogin;

import java.io.Serializable;

public class User implements Serializable {
    private String uid;
    private String name;
    private String number;
    private String department;
    User(String i,String n,String s,String d){
        this.uid = i;
        this.name = n;
        this.number = s;
        this.department = d;
    }


    public String getDepartment() { return department; }

    public String getUid() { return uid; }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

    public void setDepartment(String department) { this.department = department; }

    public void setUid(String uid) { this.uid = uid; }

    public void setName(String name) {
        this.name = name;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
