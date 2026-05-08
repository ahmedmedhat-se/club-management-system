import java.util.Date;

public class Achievement {

    private String achievementId;
    private String name;
    private String userId;
    private Date dateEarned;
    private int requiredPoints;

    public Achievement(String achievementId, String name, String userId,
                       Date dateEarned, int requiredPoints) {
        this.achievementId  = achievementId;
        this.name           = name;
        this.userId         = userId;
        this.dateEarned     = dateEarned;
        this.requiredPoints = requiredPoints;
    }

    public boolean isEligible(int userPoints) {
        return userPoints >= requiredPoints;
    }

    public String getAchievementId()                    { return achievementId; }
    public String getName()                             { return name; }
    public String getUserId()                           { return userId; }
    public Date getDateEarned()                         { return dateEarned; }
    public int getRequiredPoints()                      { return requiredPoints; }

    public void setAchievementId(String achievementId)  { this.achievementId = achievementId; }
    public void setName(String name)                    { this.name = name; }
    public void setUserId(String userId)                { this.userId = userId; }
    public void setDateEarned(Date dateEarned)          { this.dateEarned = dateEarned; }
    public void setRequiredPoints(int requiredPoints)   { this.requiredPoints = requiredPoints; }

    @Override
    public String toString() {
        return name + " (earned by userId: " + userId + ")";
    }
}