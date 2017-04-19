package com.example.robot.myapp2.ui.test_task;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.robot.myapp2.R;
import com.example.robot.myapp2.presenter.TitlesInterface;
import com.example.robot.myapp2.presenter.TitlesPresenter;
import com.example.robot.myapp2.ui.MainActivity;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.holder.StringHolder;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TitlesFragment extends BaseFragment implements TitlesInterface, RecyclerAdapter.OnItemSelected, SearchView.OnQueryTextListener {

    @BindView(R.id.recycler)
    RecyclerView recyclerView;

    private RecyclerAdapter mRecyclerAdapter;
    private MenuItem mSortByName;
    private MenuItem mSortByTime;
    private Drawer mDrawer;
    private static final String TITLE = "title";
    private static final String DETAIL = "detail";

    private TitlesPresenter mTitlesPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRecyclerAdapter = new RecyclerAdapter(this);
        mTitlesPresenter = new TitlesPresenter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.recycler_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Toolbar toolbar = ButterKnife.findById(getActivity(), R.id.toolbar);
        toolbar.setTitle(R.string.app_name);
        toolbar.inflateMenu(R.menu.menu_activity_main);

        MainActivity ma = (MainActivity) this.getActivity();
        mDrawer = ma.getDrawer();
        mDrawer.setToolbar(ma, toolbar);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(mRecyclerAdapter);

        MenuItem searchMenuItem = toolbar.getMenu().findItem(R.id.action_search);
        SearchView sv = (SearchView) searchMenuItem.getActionView();
        sv.setOnQueryTextListener(this);

        mSortByName = toolbar.getMenu().findItem(R.id.sort_by_name);
        mSortByTime = toolbar.getMenu().findItem(R.id.sort_by_time);

        mSortByName.setOnMenuItemClickListener(item -> {
            mTitlesPresenter.sortByName();
            mSortByName.setChecked(true);
            return false;
        });

        mSortByTime.setOnMenuItemClickListener(item -> {
            mTitlesPresenter.sortByTime();
            mSortByTime.setChecked(true);
            return false;
        });

        mTitlesPresenter.setView(this);
        mTitlesPresenter.getData();
    }

    @Override
    public void onItemSelected(String title, String detail) {
        mTitlesPresenter.onItemSelected(title, detail);
    }

    public void openNewFragment(String title, String detail) {
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        DetailsFragment detailsFragment = new DetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putString(TITLE, title);
        bundle.putString(DETAIL, detail);
        detailsFragment.setArguments(bundle);
        ft.replace(R.id.container2, detailsFragment, DetailsFragment.class.getName()).commit();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mTitlesPresenter.dropView();
    }

    @SuppressWarnings("unchecked")
    @Override
    public void setList(List list) {
        mRecyclerAdapter.setList(list);
        mDrawer.updateBadge(1, new StringHolder(mTitlesPresenter.getModelSize()));
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