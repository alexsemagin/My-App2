package com.example.robot.myapp2.ui.test_task;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.robot.myapp2.R;
import com.example.robot.myapp2.presenter.DetailsInterface;
import com.example.robot.myapp2.presenter.DetailsPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsFragment extends Fragment implements DetailsInterface {

    @BindView(R.id.textView)
    TextView tvDetail;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private static final String TITLE = "title";
    private static final String DETAIL = "detail";
    private DetailsPresenter mdetailsPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String title = getArguments() == null ? TITLE : getArguments().getString(TITLE);
        String detail = getArguments() == null ? DETAIL : getArguments().getString(DETAIL);
        mdetailsPresenter = new DetailsPresenter(title, detail);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.details, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setNavigationOnClickListener(v -> getActivity().getSupportFragmentManager().beginTransaction().remove(DetailsFragment.this).commit());
        mdetailsPresenter.setView(this);
        mdetailsPresenter.setData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mdetailsPresenter.setView(null);
    }

    @Override
    public void setTitleAndDetail(String title, String detail) {
        toolbar.setTitle(title);
        tvDetail.setText(detail);
    }
}
