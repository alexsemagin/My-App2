package com.example.robot.myapp2.ui.test_task;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.robot.myapp2.R;
import com.example.robot.myapp2.presenter.PhoneInterface;
import com.example.robot.myapp2.presenter.PhonePresenter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PhoneFragment extends Fragment implements PhoneInterface {

    @BindView(R.id.textView2)
    EditText tvNumber;
    @BindView(R.id.digit_1)
    Button digit1;
    @BindView(R.id.digit_2)
    Button digit2;
    @BindView(R.id.digit_3)
    Button digit3;
    @BindView(R.id.digit_4)
    Button digit4;
    @BindView(R.id.digit_5)
    Button digit5;
    @BindView(R.id.digit_6)
    Button digit6;
    @BindView(R.id.digit_7)
    Button digit7;
    @BindView(R.id.digit_8)
    Button digit8;
    @BindView(R.id.digit_9)
    Button digit9;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private PhonePresenter mPonePresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        mPonePresenter = new PhonePresenter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_phone, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setNavigationOnClickListener(v -> getActivity().getSupportFragmentManager().beginTransaction().remove(PhoneFragment.this).commit());
        digit1.setOnClickListener(v -> mPonePresenter.setNumber(digit1.getText().toString()));
        digit2.setOnClickListener(v -> mPonePresenter.setNumber(digit2.getText().toString()));
        digit3.setOnClickListener(v -> mPonePresenter.setNumber(digit3.getText().toString()));
        digit4.setOnClickListener(v -> mPonePresenter.setNumber(digit4.getText().toString()));
        digit5.setOnClickListener(v -> mPonePresenter.setNumber(digit5.getText().toString()));
        digit6.setOnClickListener(v -> mPonePresenter.setNumber(digit6.getText().toString()));
        digit7.setOnClickListener(v -> mPonePresenter.setNumber(digit7.getText().toString()));
        digit8.setOnClickListener(v -> mPonePresenter.setNumber(digit8.getText().toString()));
        digit9.setOnClickListener(v -> mPonePresenter.setNumber(digit9.getText().toString()));
        mPonePresenter.setView(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPonePresenter.setView(null);
    }

    public void setNumber(String number) {
        tvNumber.setText(number);
    }
}
