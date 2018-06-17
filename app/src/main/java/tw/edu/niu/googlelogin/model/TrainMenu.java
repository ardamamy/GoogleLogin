package tw.edu.niu.googlelogin.model;

import java.util.ArrayList;

public class TrainMenu {
    private ArrayList<String> trainItem = new ArrayList<String>();
    private String trainTime;
    private String trainTitle;
    private String trainCreatedUserID;
    private String teamID;
    public TrainMenu(ArrayList<String> mtrainItem, String trainTime, String trainTitle, String trainCreatedUserID, String teamID){
        for(int i=0;i < mtrainItem.size();i++){
            this.trainItem.add(mtrainItem.get(i)) ;
        }
        this.trainTime = trainTime;
        this.trainTitle = trainTitle;
        this.trainCreatedUserID = trainCreatedUserID;
        this.teamID = teamID;
    }

    public String getTeamID() { return teamID; }

    public ArrayList<String> getTrainItem() { return trainItem; }

    public String getTrainCreatedUserID() { return trainCreatedUserID; }

    public String getTrainTime() { return trainTime; }

    public String getTrainTitle() { return trainTitle; }

    public void setTrainCreatedUserID(String trainCreatedUserID) { this.trainCreatedUserID = trainCreatedUserID; }

    public void setTrainItem(ArrayList<String> trainItem) { this.trainItem = trainItem; }

    public void setTrainTime(String trainTime) { this.trainTime = trainTime; }

    public void setTrainTitle(String trainTitle) { this.trainTitle = trainTitle; }

    public void setTeamID(String teamID) { this.teamID = teamID; }
}
