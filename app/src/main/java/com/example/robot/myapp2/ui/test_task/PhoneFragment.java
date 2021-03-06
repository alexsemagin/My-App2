package com.example.robot.myapp2.ui.test_task;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.CallLog;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.robot.myapp2.R;
import com.example.robot.myapp2.presenter.PhoneInterface;
import com.example.robot.myapp2.presenter.PhonePresenter;
import com.example.robot.myapp2.ui.MainActivity;
import com.mikepenz.materialdrawer.Drawer;

import butterknife.BindView;

public class PhoneFragment extends BaseFragment implements PhoneInterface {

    @BindView(R.id.input_phone)
    EditText tvNumber;
    @BindView(R.id.imgBtnCall)
    ImageButton call;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    AlertDialog.Builder alertDialog;

    private PhonePresenter mPhonePresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPhonePresenter = new PhonePresenter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_phone, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        toolbar.setTitle(R.string.drawer_item_phone);
        call.setOnClickListener(v -> {
            Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.rotate);
            call.startAnimation(animation);
            mPhonePresenter.callToNumber(tvNumber.getText().toString());
        });

        tvNumber.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                mPhonePresenter.textChanged(s, tvNumber);

            }
        });

        MainActivity ma = (MainActivity) this.getActivity();
        Drawer drawer = ma.getDrawer();
        drawer.setToolbar(ma, toolbar, true);

        mPhonePresenter.setView(this);
        mPhonePresenter.getPermission();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPhonePresenter.dropView();
    }

    @Override
    public void openContacts() {
        Intent intent = new Intent();
        intent.setClassName("com.android.contacts", "com.android.contacts.activities.PeopleActivity");
        startActivity(intent);
    }

    @Override
    public void callToNumber(String number) {
        if (getActivity().getPackageManager().hasSystemFeature(PackageManager.FEATURE_TELEPHONY)) {
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + number));
            if (number.isEmpty()) {
                mPhonePresenter.getLastNumber();
            } else {
                startActivity(intent);
            }
        } else {
            alertDialog = new AlertDialog.Builder(new ContextThemeWrapper(getContext(), R.style.AlertDialogCustom));
            alertDialog.setTitle("Голосовые вызовы не поддерживаются!");
            alertDialog.setPositiveButton("Добавить в контакты", (dialog, which) -> mPhonePresenter.openContacts());
            alertDialog.setNegativeButton("Закрыть", (dialog, which) -> {
            });
            alertDialog.setCancelable(true);
            alertDialog.show();
        }
    }

    @Override
    public void getPermission() {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE}, PackageManager.PERMISSION_GRANTED);
        }
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_CONTACTS}, PackageManager.PERMISSION_GRANTED);
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void getLastNumber() {
        String[] projection = new String[]{
                CallLog.Calls._ID,
                CallLog.Calls.DATE,
                CallLog.Calls.NUMBER,
                CallLog.Calls.CACHED_NAME,
                CallLog.Calls.DURATION,
                CallLog.Calls.TYPE
        };

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_CALL_LOG}, PackageManager.PERMISSION_GRANTED);
        }

        Cursor cursor = getContext().getContentResolver().query(
                CallLog.Calls.CONTENT_URI,
                projection,
                null,
                null,
                null
        );

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    tvNumber.setText(cursor.getString(cursor.getColumnIndex(CallLog.Calls.NUMBER)));
                } while (cursor.moveToNext());
            }
            if (!cursor.isClosed()) {
                cursor.close();
            }
        }
    }

}