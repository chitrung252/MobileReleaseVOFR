package fpt.com.virtualoutfitroom.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import fpt.com.virtualoutfitroom.R;
import fpt.com.virtualoutfitroom.activities.DetailProductActivity;
import fpt.com.virtualoutfitroom.adapter.ProductCateAdapter;
import fpt.com.virtualoutfitroom.model.Category;
import fpt.com.virtualoutfitroom.model.Product;
import fpt.com.virtualoutfitroom.presenter.HomePresenter;
import fpt.com.virtualoutfitroom.presenter.product_cate.ProductCatePresenter;
import fpt.com.virtualoutfitroom.utils.GridSpacingItemDecoration;
import fpt.com.virtualoutfitroom.utils.InternetHelper;
import fpt.com.virtualoutfitroom.views.ProductCateView;

public class PageFragment extends Fragment implements ProductCateView {
    private RecyclerView mRcvProCate;
    private ProductCateAdapter mRcvProCateAdapter;
    private List<Product> mListProduct;
    private Category mCategory;
    private View mView;
    private ProductCatePresenter mPresenter;

    public PageFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_page, container, false);
        initialView();
        return mView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialData();
    }

    public void initialView() {
        mRcvProCate = mView.findViewById(R.id.rcv_product_cate);
        mRcvProCate.setHasFixedSize(true);
        int numberOfColumns = calculateNumberOfColumns(getContext());
        GridLayoutManager gridLayoutManager = new
                GridLayoutManager(getActivity(), numberOfColumns);
        mRcvProCate.setLayoutManager(gridLayoutManager);
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.dp15);
        mRcvProCate.addItemDecoration(new GridSpacingItemDecoration
                (numberOfColumns, spacingInPixels, true));
        mListProduct = new ArrayList<>();
    }

    private int calculateNumberOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int noOfColumns = (int) (dpWidth / 180);
        return noOfColumns;
    }
    public void updateUI(){
        mRcvProCateAdapter = new ProductCateAdapter(getActivity(),mListProduct);
        mRcvProCate.setAdapter(mRcvProCateAdapter);
        mRcvProCateAdapter.setOnItemClickedListener(new ProductCateAdapter.OnItemClickedListener() {
            @Override
            public void onItemClicked(int position) {
                Intent intent = new Intent(getActivity(), DetailProductActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("PRODUCT", mListProduct.get(position));
                intent.putExtra("BUNDLE", bundle);
                startActivity(intent);
            }
        });
    }
    public void initialData() {
        Bundle bundle = this.getArguments();
        mCategory =(Category) bundle.getSerializable("SUBCATEGORY");
        mPresenter = new ProductCatePresenter(getActivity(), this);
        if (InternetHelper.isOnline(getActivity()) == false) {
            Toast.makeText(getActivity(), "No Connection", Toast.LENGTH_LONG).show();
        } else {
                mPresenter.getProductByCateId(mCategory.getCategoryId());
        }

    }

    @Override
    public void showListProduct(List<Product> productList) {
        if(productList != null){
            mListProduct = productList;
            updateUI();
        }
    }

    @Override
    public void showError(String message) {

    }
}
