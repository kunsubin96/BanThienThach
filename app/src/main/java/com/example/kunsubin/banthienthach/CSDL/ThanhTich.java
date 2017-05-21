package com.example.kunsubin.banthienthach.CSDL;

/**
 * Created by kunsubin on 5/21/2017.
 */

public class ThanhTich {
    private String user;
    private String score;

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getGold() {
        return gold;
    }

    public void setGold(String gold) {
        this.gold = gold;
    }

    private String gold;
    public ThanhTich(String user,String score,String gold){
        this.user=user;
        this.score=score;
        this.gold=gold;
    }
    public ThanhTich(){

    }
}
