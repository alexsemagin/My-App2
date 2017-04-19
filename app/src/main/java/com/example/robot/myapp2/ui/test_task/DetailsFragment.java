package com.example.robot.myapp2.ui.test_task;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.example.robot.myapp2.R;
import com.example.robot.myapp2.presenter.DetailsPresenter;

import butterknife.BindView;

public class DetailsFragment extends BaseFragment implements DetailsPresenter.View {

    @BindView(R.id.textView)
    TextView tvDetail;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private static final String TITLE = "title";
    private static final String DETAIL = "detail";

    private DetailsPresenter mDetailsPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String title = getArguments() == null ? TITLE : getArguments().getString(TITLE);
        String detail = getArguments() == null ? DETAIL : getArguments().getString(DETAIL);
        mDetailsPresenter = new DetailsPresenter(title, detail);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.details, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setNavigationOnClickListener(v -> getActivity().getSupportFragmentManager().beginTransaction().remove(DetailsFragment.this).commit());

        mDetailsPresenter.setView(this);
        mDetailsPresenter.setData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mDetailsPresenter.dropView();
    }

    @Override
    public void setTitleAndDetail(String title, String detail) {
        toolbar.setTitle(title);
        tvDetail.setText(detail);
        Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.combination);
        tvDetail.startAnimation(animation);
    }

}