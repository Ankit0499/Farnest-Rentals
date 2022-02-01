package com.example.ankit_prajapati.login;

public class UserProfile {
    public String name;
    public String email;
    public String mobieno;

    public UserProfile() {

    }

    public UserProfile(String name, String email, String mobieno) {

        this.name = name;
        this.email = email;
        this.mobieno = mobieno;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobieno() {
        return mobieno;
    }

    public void setMobieno(String mobieno) {
        this.mobieno = mobieno;
    }
}
