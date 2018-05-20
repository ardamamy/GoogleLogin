package tw.edu.niu.googlelogin;

public class User {
 private String name;
 private String number;
    User(){
        name = null;
        number = null;
    }
    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
