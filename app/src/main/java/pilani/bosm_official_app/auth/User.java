package pilani.bosm_official_app.auth;

/**
 * Created by Saksham on 12 Sep 2016.
 */
public class User {
    private String email;
    private String mobile;
    private String name;
    private int score;
    private int bettingAmount;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getBettingAmount() {
        return bettingAmount;
    }

    public void setBettingAmount(int bettingAmount) {
        this.bettingAmount = bettingAmount;
    }
}
