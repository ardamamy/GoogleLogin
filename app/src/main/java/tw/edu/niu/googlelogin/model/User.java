package tw.edu.niu.googlelogin.model;

import java.io.Serializable;

public class User implements Serializable {
    private String userId;
    private String name;
    private String number;
    private String department;
    private String gender;
    private String coperation;
    private String role;
    private String teamID;
    private Boolean hasfee;
    private Boolean success;
    public User(String i,String n,String s,String d,String g,String c,String r,String team,Boolean hasfee,Boolean success){
        this.userId = i;
        this.name = n;
        this.number = s;
        this.department = d;
        this.gender = g;
        this.coperation = c;
        this.role = r;
        this.teamID = team;
        this.hasfee = hasfee;
        this.success = success;
    }

    public Boolean getSuccess() { return success; }

    public Boolean getHasfee() { return hasfee; }

    public String getTeamID() { return teamID; }

    public String getRole() { return role; }

    public String getCoperation() { return coperation; }

    public String getGender() { return gender; }

    public String getDepartment() { return department; }

    public String getUserId() { return userId; }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

    public void setDepartment(String department) { this.department = department; }

    public void setUserId(String uid) { this.userId = uid; }

    public void setName(String name) {
        this.name = name;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setGender(String gender) { this.gender = gender; }

    public void setCoperation(String coperation) { this.coperation = coperation; }

    public void setRole(String role) { this.role = role; }

    public void setTeamID(String teamID) { this.teamID = teamID; }

    public void setHasfee(Boolean hasfee) { this.hasfee = hasfee; }

    public void setSuccess(boolean b) { this.success = success; }
}
