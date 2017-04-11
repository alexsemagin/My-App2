package com.example.robot.myapp2.ui.test_task;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.robot.myapp2.R;
import com.example.robot.myapp2.presenter.TitlesInterface;
import com.example.robot.myapp2.presenter.TitlesPresenter;
import com.example.robot.myapp2.ui.MainActivity;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.holder.StringHolder;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TitlesFragment extends Fragment implements RecyclerAdapter.OnItemSelected, TitlesInterface, SearchView.OnQueryTextListener {

    @BindView(R.id.recycler)
    RecyclerView recyclerView;
    private RecyclerAdapter rAdapter;
    private static final String TITLE = "title";
    private static final String DETAIL = "detail";
    private TitlesPresenter mTitlesPresenter;
    private Boolean checkName = false;
    private Boolean checkTime = false;
    MenuItem sortByName;
    MenuItem sortByTime;
    ProgressBar progressBar;
    Drawer drawer;
    Toolbar toolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        rAdapter = new RecyclerAdapter(getActivity(), this);
        mTitlesPresenter = new TitlesPresenter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recycler_layout, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        toolbar = ButterKnife.findById(getActivity(), R.id.toolbar);
        toolbar.setTitle(R.string.app_name);
        toolbar.inflateMenu(R.menu.menu_activity_main);

        MainActivity ma = (MainActivity) this.getActivity();
        drawer = ma.getDrawer();
        drawer.setToolbar(ma, toolbar);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(rAdapter);

        MenuItem searchMenuItem = toolbar.getMenu().findItem(R.id.action_search);
        SearchView sv = (SearchView) searchMenuItem.getActionView();
        sv.setOnQueryTextListener(this);

        sortByName = toolbar.getMenu().findItem(R.id.sort_by_name);
        sortByTime = toolbar.getMenu().findItem(R.id.sort_by_time);

        sortByName.setOnMenuItemClickListener(item -> {
            mTitlesPresenter.sortByName();
            checkName = true;
            sortByName.setChecked(true);
            checkTime = false;
            sortByTime.setChecked(false);
            return false;
        });

        sortByTime.setOnMenuItemClickListener(item -> {
            mTitlesPresenter.sortByTime();
            checkTime = true;
            sortByTime.setChecked(true);
            checkName = false;
            sortByName.setChecked(false);
            return false;
        });

        progressBar = (ProgressBar) getActivity().findViewById(R.id.progressBar);
        mTitlesPresenter.setView(this);
        mTitlesPresenter.getData();

    }

    @Override
    public void onResume() {
        super.onResume();
        sortByName.setChecked(checkName);
        sortByTime.setChecked(checkTime);
    }

    @Override
    public void onItemSelected(String title, String detail) {
        mTitlesPresenter.onItemSelected(title, detail);
    }

    public void openNewFragment(String title, String detail) {
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        DetailsFragment det = new DetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putString(TITLE, title);
        bundle.putString(DETAIL, detail);
        det.setArguments(bundle);
        ft.replace(R.id.container2, det, DetailsFragment.class.getName()).commit();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mTitlesPresenter.setView(null);
    }

    @Override
    public void setList(List list) {
        rAdapter.setList(list);
        drawer.updateBadge(1, new StringHolder(mTitlesPresenter.getModelSize()));
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        mTitlesPresenter.searchItem(query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        mTitlesPresenter.searchItem(newText);
        return false;
    }
}