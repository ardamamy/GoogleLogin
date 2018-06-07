package tw.edu.niu.googlelogin.model;

public class Team {

    private String name;
    private String adminId;

    public Team(String name, String adminId){
        this.name = name;
        this.adminId = adminId;
    }

    public String getName() {
        return name;
    }

    public String getAdminId() {
        return adminId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }
}
