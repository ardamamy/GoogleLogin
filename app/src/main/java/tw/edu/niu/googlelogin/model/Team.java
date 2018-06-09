package tw.edu.niu.googlelogin.model;

public class Team {

    private String name;
    private String adminId;
    private Boolean success;
    public Team(String name, String adminId,Boolean success){
        this.name = name;
        this.adminId = adminId;
        this.success = success;
    }

    public Boolean getSuccess() { return success; }

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

    public void setSuccess(Boolean success) { this.success = success; }
}
