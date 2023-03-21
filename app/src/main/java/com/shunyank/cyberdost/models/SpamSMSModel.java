package com.shunyank.cyberdost.models;

public class SpamSMSModel {
    int spam_score;
    String spam_text;

    public int getSpam_score() {
        return spam_score;
    }

    public void setSpam_score(int spam_score) {
        this.spam_score = spam_score;
    }

    public String getSpam_text() {
        return spam_text;
    }

    public void setSpam_text(String spam_text) {
        this.spam_text = spam_text;
    }
}
