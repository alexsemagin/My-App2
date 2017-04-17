package com.example.robot.myapp2.presenter;

public class PhonePresenter {

    private PhoneInterface phoneInterface;

    public void setView(PhoneInterface phoneInterface) {
        this.phoneInterface = phoneInterface;
    }

    public void callToNumber(String s) {
        phoneInterface.callToNumber(s.replaceAll("(\\+)(\\D+)", ""));
    }

    public void getPermission() {
        phoneInterface.getPermission();
    }

    public void openContacts() {
        phoneInterface.openContacts();
    }

    public void getLastNumber() {
        phoneInterface.getLastNumber();
    }

}