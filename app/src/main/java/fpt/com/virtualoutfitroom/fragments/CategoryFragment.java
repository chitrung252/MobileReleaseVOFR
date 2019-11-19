package fpt.com.virtualoutfitroom.fragments;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import fpt.com.virtualoutfitroom.R;
import fpt.com.virtualoutfitroom.activities.ProductCategoryActivity;
import fpt.com.virtualoutfitroom.adapter.CategoryAdapter;
import fpt.com.virtualoutfitroom.adapter.RecyclerViewApdapter;
import fpt.com.virtualoutfitroom.model.Category;
import fpt.com.virtualoutfitroom.presenter.category.CategoryPresenter;
import fpt.com.virtualoutfitroom.utils.GridSpacingItemDecoration;
import fpt.com.virtualoutfitroom.views.CategoryView;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryFragment extends Fragment implements CategoryView {
    private RecyclerView mRcvCate;
    private List<Category> mListCate;
    private CategoryAdapter mRcvAdapterCate;
    private CategoryPresenter mCategoryPresenter;
    public CategoryFragment() {
        // Required empty public constructor
    }
    public static Fragment newInstance(){
        CategoryFragment fragment = new CategoryFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        initialView(view);
        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialData();
    }
    public void initialView(View view) {
        mRcvCate = view.findViewById(R.id.rcv_cate);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRcvCate.setLayoutManager(layoutManager);
        mListCate = new ArrayList<>();
    }
    public void initialData(){
        mCategoryPresenter = new CategoryPresenter(getActivity(),this);
        mCategoryPresenter.getListCategory();
    }

    public void updateUI() {
        if (mRcvAdapterCate == null) {
            mRcvAdapterCate = new CategoryAdapter(getContext(), mListCate);
            mRcvCate.setAdapter(mRcvAdapterCate);
            mRcvAdapterCate.setOnItemClickedListener(new RecyclerViewApdapter.OnItemClickedListener() {
                @Override
                public void onItemClicked(int position) {
                    Intent intent= new Intent(getActivity(), ProductCategoryActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("CATEGORY", mListCate.get(position));
                    intent.putExtra("BUNDLE", bundle);
                    startActivity(intent);
                }
            });
        } else {
            mRcvAdapterCate.notifyDataSetChanged();
        }
    }

    @Override
    public void showListCategory(List<Category> categoryList) {
        if (categoryList != null){
            for (Category cate: categoryList) {
                if(cate.isActive() == true){
                    mListCate.add(cate);
                }
            }
            updateUI();
        }
    }

    @Override
    public void showError(String message) {

    }
}
