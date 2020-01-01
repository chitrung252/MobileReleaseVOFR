package fpt.com.virtualoutfitroom.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kaopiz.kprogresshud.KProgressHUD;
import com.squareup.picasso.Picasso;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;
import com.synnapps.carouselview.ViewListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import fpt.com.virtualoutfitroom.R;
import fpt.com.virtualoutfitroom.activities.DetailProductActivity;
import fpt.com.virtualoutfitroom.activities.HomeActivity;
import fpt.com.virtualoutfitroom.activities.SearchProductActivity;
import fpt.com.virtualoutfitroom.activities.ShopCartActivity;
import fpt.com.virtualoutfitroom.adapter.RecyclerViewApdapter;
import fpt.com.virtualoutfitroom.model.Product;
import fpt.com.virtualoutfitroom.model.ProductImage;
import fpt.com.virtualoutfitroom.presenter.HomePresenter;
import fpt.com.virtualoutfitroom.utils.BundleString;
import fpt.com.virtualoutfitroom.utils.InternetHelper;
import fpt.com.virtualoutfitroom.utils.SharePreferenceUtils;
import fpt.com.virtualoutfitroom.utils.SpinnerManagement;
import fpt.com.virtualoutfitroom.views.HomeView;

public class HomeFragment extends Fragment implements HomeView, View.OnClickListener {
    private RecyclerView mRrvEaring, mRrvGlasses, mRrvHat, mRrvShoes ;
    private List<Product> mListGlasses, mListHat, mListEaring, mListShoes, mListSearch;
    private RecyclerViewApdapter mRrvAdapterGlasses, mRrvAdapterHat, mRrvAdapterEaring, mRrvAdapterShoes;
    private HomePresenter mPresenter;
    private ImageView mImgShopCart;
    private CarouselView mCarouselView;
    private List<Integer> listImageHeader;
    private KProgressHUD hud;
    private EditText mEdtSearch;
    private TextView mTxtCount;
    public HomeFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        setLayout(rootView);
        getSpinner();
        return rootView;
    }
    public static Fragment newInstance(){
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupCarousel();
        initialData();
    }
    public void getSpinner(){
      hud = SpinnerManagement.getSpinner(getActivity());
    }
    public void initialData() {
        mPresenter = new HomePresenter(getActivity(), this);
        if (InternetHelper.isOnline(getActivity()) == false) {
            Toast.makeText(getActivity(), "No Connection", Toast.LENGTH_LONG).show();
        } else {
            mPresenter.getListProduct();

        }
        setCountShopCart();
    }
    public void setCountShopCart(){
            int count = SharePreferenceUtils.getIntSharedPreference(getActivity(), BundleString.COUNTSHOPCART);
            if(count == 0){
                mTxtCount.setText("0");
            }else{
                mTxtCount.setText(count + "");
            }
    }
    public void setLayout(View rootView) {
        mTxtCount = rootView.findViewById(R.id.txt_count_shop_cart);
        mRrvGlasses = rootView.findViewById(R.id.rcv_list_glasses);
        mRrvHat = rootView.findViewById(R.id.rcv_list_hat);
        mRrvEaring = rootView.findViewById(R.id.rcv_list_earing);
        mRrvShoes = rootView.findViewById(R.id.rcv_list_shoes);
        mCarouselView = rootView.findViewById(R.id.carouselView);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL,false);
        //layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        final LinearLayoutManager layoutManager2 = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        //layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        final LinearLayoutManager layoutManager3 = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        //layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        final LinearLayoutManager layoutManager4 = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        mRrvGlasses.setLayoutManager(layoutManager);
        mRrvHat.setLayoutManager(layoutManager2);
        mRrvEaring.setLayoutManager(layoutManager3);
        mRrvShoes.setLayoutManager(layoutManager4);
        mListGlasses = new ArrayList<>();
        mListHat = new ArrayList<>();
        mListEaring = new ArrayList<>();
        mListShoes = new ArrayList<>();
        mImgShopCart = rootView.findViewById(R.id.img_shop_cart);
        mImgShopCart.setOnClickListener(this);
        mEdtSearch = rootView.findViewById(R.id.edt_search_home);
        mEdtSearch.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press
                    getSpinner();
                    mPresenter.searchProduct(mEdtSearch.getText().toString());
                    return true;
                }
                return false;
            }
        });
    }
    public void updateUIGlasses() {
        mRrvAdapterGlasses = new RecyclerViewApdapter(getActivity(), mListGlasses);
        // Inflate the layout for this fragment
        mRrvGlasses.setAdapter(mRrvAdapterGlasses);
        mRrvAdapterGlasses.setOnItemClickedListener(new RecyclerViewApdapter.OnItemClickedListener() {
            @Override
            public void onItemClicked(int position) {
                Intent intent = new Intent(getActivity(), DetailProductActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("PRODUCT", mListGlasses.get(position));
                intent.putExtra("BUNDLE", bundle);
                startActivity(intent);
            }
        });
    }
    public void updateUIHat() {
        mRrvAdapterHat = new RecyclerViewApdapter(getActivity(), mListHat);
        // Inflate the layout for this fragment
        mRrvHat.setAdapter(mRrvAdapterHat);
        mRrvAdapterHat.setOnItemClickedListener(new RecyclerViewApdapter.OnItemClickedListener() {
            @Override
            public void onItemClicked(int position) {
                Intent intent = new Intent(getActivity(), DetailProductActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("PRODUCT", mListHat.get(position));
                intent.putExtra("BUNDLE", bundle);
                startActivity(intent);
            }
        });
    }
    public void updateUIEaring() {
        mRrvAdapterEaring = new RecyclerViewApdapter(getActivity(), mListEaring);
        // Inflate the layout for this fragment
        mRrvEaring.setAdapter(mRrvAdapterEaring);
        mRrvAdapterEaring.setOnItemClickedListener(new RecyclerViewApdapter.OnItemClickedListener() {
            @Override
            public void onItemClicked(int position) {
                Intent intent = new Intent(getActivity(), DetailProductActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("PRODUCT", mListEaring.get(position));
                intent.putExtra("BUNDLE", bundle);
                startActivity(intent);
            }
        });
    }
    public void updateUIShoes() {
        mRrvAdapterShoes = new RecyclerViewApdapter(getActivity(), mListShoes);
        // Inflate the layout for this fragment
        mRrvShoes.setAdapter(mRrvAdapterShoes);
        mRrvAdapterShoes.setOnItemClickedListener(new RecyclerViewApdapter.OnItemClickedListener() {
            @Override
            public void onItemClicked(int position) {
                Intent intent = new Intent(getActivity(), DetailProductActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("PRODUCT", mListShoes.get(position));
                intent.putExtra("BUNDLE", bundle);
                startActivity(intent);
            }
        });
    }
    @Override
    public void showListProduct(List<Product> productList) {
        hud.dismiss();
        for (Product product : productList) {
            if (product.getMasterCategoryId() == 1 && product.isActived() == true) {
                mListGlasses.add(product);
                updateUIGlasses();
            }
            if (product.getMasterCategoryId() == 2 && product.isActived() == true) {
                mListHat.add(product);
                updateUIHat();
            }
            if (product.getMasterCategoryId() == 3 && product.isActived() == true) {
                mListEaring.add(product);
                updateUIEaring();
            }
            if(product.getMasterCategoryId() == 4 && product.isActived() == true){
                mListShoes.add(product);
                updateUIShoes();
            }
        }
    }

    @Override
    public void showListProductSearch(List<Product> productList) {
        hud.dismiss();
        mListSearch = productList;
        Intent intent = new Intent(getActivity(), SearchProductActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("LISTPRODUCT", (Serializable) mListSearch);
        intent.putExtra("BUNDLE",bundle);
        startActivity(intent);
    }


    @Override
    public void showError(String message) {
        hud.dismiss();
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_shop_cart:
                moveToShopCart();
                break;
        }
    }

    void moveToShopCart(){
        Intent intent = new Intent(getActivity(), ShopCartActivity.class);
        startActivity(intent);
    }
    void setupCarousel(){
        listImageHeader  = new ArrayList<>();
        listImageHeader.add(R.drawable.banner_1);
        listImageHeader.add(R.drawable.banner_2);
        listImageHeader.add(R.drawable.banner_3);
        mCarouselView.setViewListener(new ViewListener() {
            @Override
            public View setViewForPosition(int position) {
                View customView = getLayoutInflater().inflate(R.layout.custom_image_header, null);
                //set view attributes here
                ImageView imageView = customView.findViewById(R.id.image_header);
                Picasso.get().load(listImageHeader.get(position)).into(imageView);
                return customView;
            }
        });
        mCarouselView.setPageCount(listImageHeader.size());
    }

}
