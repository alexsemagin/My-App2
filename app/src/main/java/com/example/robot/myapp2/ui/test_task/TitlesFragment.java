package com.example.robot.myapp2.ui.test_task;

import android.graphics.Color;
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
import android.widget.EditText;
import android.widget.ImageView;

import com.example.robot.myapp2.R;
import com.example.robot.myapp2.presenter.TitlesInterface;
import com.example.robot.myapp2.presenter.TitlesPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TitlesFragment extends Fragment implements RecyclerAdapter.OnItemSelected, TitlesInterface, SearchView.OnQueryTextListener {

    @BindView(R.id.recycler)
    RecyclerView recyclerView;
    private RecyclerAdapter rAdapter;
    private static final String TITLE = "title";
    private static final String DETAIL = "detail";
    private TitlesPresenter mtitlesPresenter;

    Toolbar toolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        rAdapter = new RecyclerAdapter(getActivity(), this);
        mtitlesPresenter = new TitlesPresenter(rAdapter);
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
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(rAdapter);
        toolbar = ButterKnife.findById(getActivity(), R.id.toolbar);
        toolbar.setTitle(R.string.app_name);
        toolbar.setNavigationIcon(R.mipmap.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(v -> getActivity().onBackPressed());
        toolbar.inflateMenu(R.menu.menu_activity_main);
        MenuItem searchMenuItem = toolbar.getMenu().findItem(R.id.action_search);
        SearchView sv = (SearchView) searchMenuItem.getActionView();
        ImageView searchCloseIcon = (ImageView)sv.findViewById(android.support.v7.appcompat.R.id.search_close_btn);
        searchCloseIcon.setImageResource(R.drawable.ic_toolbar_clear);
        sv.setOnQueryTextListener(this);
        mtitlesPresenter.setView(this);
        mtitlesPresenter.getData();
    }

    @Override
    public void onItemSelected(String title, String detail) {
        mtitlesPresenter.onItemSelected(title, detail);
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
        mtitlesPresenter.setView(null);
    }

    @Override
    public void setList(List list) {
        rAdapter.setList(list);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        mtitlesPresenter.searchItem(query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        mtitlesPresenter.searchItem(newText);
        return false;
    }
}
