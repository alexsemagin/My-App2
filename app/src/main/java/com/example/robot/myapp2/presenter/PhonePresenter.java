package com.example.robot.myapp2.presenter;

import android.text.Editable;
import android.widget.EditText;

public class PhonePresenter extends BasePresenter<PhoneInterface> {

    public void setView(PhoneInterface view) {
        super.setView(view);
    }

    public void callToNumber(String s) {
        mBaseInterface.callToNumber(s.replaceAll("(\\+)(\\D+)", ""));
    }

    public void getPermission() {
        mBaseInterface.getPermission();
    }

    public void openContacts() {
        mBaseInterface.openContacts();
    }

    public void getLastNumber() {
        mBaseInterface.getLastNumber();
    }

    public void textChanged(Editable s, EditText tvNumber) {
        String formatted;
        String regex0 = "(.+)(\\-)$";
        String regex1 = "(\\+\\d)(\\d{3})";
        String regex2 = "(.+ )(\\d{3})(\\d)$";
        String regex3 = "(.+\\-)(\\d{2})(\\d)$";
        String regex4 = "([0-7,9])(\\d{3})(\\d)";
        String regex5 = "(^[8])(\\d{3})(\\d)";
        String regex6 = "(\\+)(\\d)( \\()(\\d{3})(\\))";
        String regex7 = "(\\d)( \\()(\\d{3})(\\))";

        if (s.toString().matches(regex0)) {
            formatted = String.valueOf(s).replaceFirst(regex0, "$1");
            tvNumber.setText(formatted);
            tvNumber.setSelection(formatted.length());
        } else if (s.toString().matches(regex1)) {
            formatted = String.valueOf(s).replaceFirst(regex1, "$1 ($2) $3");
            tvNumber.setText(formatted);
            tvNumber.setSelection(formatted.length());
        } else if (s.toString().matches(regex2)) {
            formatted = String.valueOf(s).replaceFirst(regex2, "$1$2-$3");
            tvNumber.setText(formatted);
            tvNumber.setSelection(formatted.length());
        } else if (s.toString().matches(regex3) && s.length() < 18) {
            formatted = String.valueOf(s).replaceFirst(regex3, "$1$2-$3");
            tvNumber.setText(formatted);
            tvNumber.setSelection(formatted.length());
        } else if (s.toString().matches(regex4)) {
            formatted = String.valueOf(s).replaceFirst(regex4, "+$1 ($2) $3");
            tvNumber.setText(formatted);
            tvNumber.setSelection(formatted.length());
        } else if (s.toString().matches(regex5)) {
            formatted = String.valueOf(s).replaceFirst(regex5, "$1 ($2) ");
            tvNumber.setText(formatted);
            tvNumber.setSelection(formatted.length());
        } else if (s.toString().matches(regex6)) {
            formatted = String.valueOf(s).replaceFirst(regex6, "$2$4");
            tvNumber.setText(formatted);
            tvNumber.setSelection(formatted.length());
        } else if (s.toString().matches(regex7)) {
            formatted = String.valueOf(s).replaceFirst(regex7, "$1$3");
            tvNumber.setText(formatted);
            tvNumber.setSelection(formatted.length());
        }
    }

}