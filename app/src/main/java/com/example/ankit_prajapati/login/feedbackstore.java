package com.example.ankit_prajapati.login;

public class feedbackstore {
    public String fuemail;
    public String fucomment;
    public String ratingstar;

    public feedbackstore() {
    }

    public feedbackstore(String fuemail, String fucomment,String ratingstar) {
        this.fuemail = fuemail;
        this.fucomment = fucomment;
        this.ratingstar = ratingstar;
    }

    public String getFuemail() {
        return fuemail;
    }

    public void setFuemail(String fuemail) {
        this.fuemail = fuemail;
    }

    public String getFucomment() {
        return fucomment;
    }

    public void setFucomment(String fucomment) {
        this.fucomment = fucomment;
    }

    public String getRatingstar() {
        return ratingstar;
    }

    public void setRatingstar(String ratingstar) {
        this.ratingstar = ratingstar;
    }
}
