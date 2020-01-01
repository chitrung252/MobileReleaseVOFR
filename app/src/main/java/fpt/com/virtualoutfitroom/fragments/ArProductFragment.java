package fpt.com.virtualoutfitroom.fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ogaclejapan.smarttablayout.utils.v4.Bundler;

import java.util.ArrayList;
import java.util.List;

import fpt.com.virtualoutfitroom.R;
import fpt.com.virtualoutfitroom.activities.AugmentedFacesActivity;
import fpt.com.virtualoutfitroom.activities.DetailProductActivity;
import fpt.com.virtualoutfitroom.adapter.ProductArAdapter;
import fpt.com.virtualoutfitroom.adapter.ProductSeenApdater;
import fpt.com.virtualoutfitroom.model.Category;
import fpt.com.virtualoutfitroom.model.Product;
import fpt.com.virtualoutfitroom.presenter.product_cate.ProductCatePresenter;
import fpt.com.virtualoutfitroom.utils.InternetHelper;
import fpt.com.virtualoutfitroom.utils.RefineImage;
import fpt.com.virtualoutfitroom.views.ProductCateView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ArProductFragment extends Fragment implements ProductCateView {
    private RecyclerView mRcvProCate;
    private ProductArAdapter mRcvProAr;
    private List<Product> mListProduct;
    private Category mCategory;
    private View mView;
    private ProductCatePresenter mPresenter;
    private FragmentListener mFragment;

    public ArProductFragment() {
        // Required empty public constructor
    }
//    public  ArProductFragment newInstance(String param) {
//        ArProductFragment f = new ArProductFragment();
//        f.setArguments(arguments(param));
//        return f;
//    }
//
//    public  Bundle arguments(String param) {
//        return new Bundler()
//                .putString("SUBCATEGORY", param)
//                .get();
//    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         mView = inflater.inflate(R.layout.fragment_ar_product, container, false);
         initialView();
         return mView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialData();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mFragment = (FragmentListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " You must implement FragmentListener");
        }
    }
    public interface FragmentListener {
        void sendData(Product product);
    }
    private void initialView(){
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL,false);
        mRcvProCate = mView.findViewById(R.id.rcv_product_cate);
        mRcvProCate.setLayoutManager(layoutManager);
        mListProduct = new ArrayList<>();
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
            for (Product product: productList) {
                if(product.isActived() == true && RefineImage.getUrlImage(product.getProductImageList(), "sfb") != null){
                    mListProduct.add(product);
                }
            }
            updateUI();
        }
    }

    public void updateUI(){
        mRcvProAr = new ProductArAdapter(getActivity(),mListProduct);
        mRcvProCate.setAdapter(mRcvProAr);
        mRcvProAr.setOnItemClickedListener(new ProductArAdapter.OnItemClickedListener() {
            @Override
            public void onItemClicked(int position) {
                mFragment.sendData(mListProduct.get(position));
            }
        });
    }

    @Override
    public void showError(String message) {

    }
}
