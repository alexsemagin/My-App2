package com.example.robot.myapp2.presenter;

public class PhonePresenter {

    private PhoneInterface phoneInterface;
    private String number;

    public void setView(PhoneInterface phoneInterface) {
        this.phoneInterface = phoneInterface;
        number = "";
    }

    public void setNumber(String nextDigit) {
        number += nextDigit;
        phoneInterface.setNumber(number);
    }
}