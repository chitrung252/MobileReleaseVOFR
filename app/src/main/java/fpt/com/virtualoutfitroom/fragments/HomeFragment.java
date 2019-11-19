package fpt.com.virtualoutfitroom.fragments;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;
import com.synnapps.carouselview.ViewListener;

import java.util.ArrayList;
import java.util.List;

import fpt.com.virtualoutfitroom.R;
import fpt.com.virtualoutfitroom.activities.DetailProductActivity;
import fpt.com.virtualoutfitroom.activities.ShopCartActivity;
import fpt.com.virtualoutfitroom.adapter.RecyclerViewApdapter;
import fpt.com.virtualoutfitroom.model.Product;
import fpt.com.virtualoutfitroom.model.ProductImage;
import fpt.com.virtualoutfitroom.presenter.HomePresenter;
import fpt.com.virtualoutfitroom.utils.InternetHelper;
import fpt.com.virtualoutfitroom.views.HomeView;

public class HomeFragment extends Fragment implements HomeView, View.OnClickListener {
    private RecyclerView mRrvEaring;
    private RecyclerView mRrvGlasses;
    private RecyclerView mRrvHat;
    private List<Product> mListGlasses, mListHat, mListEaring;
    private RecyclerViewApdapter mRrvAdapterGlasses;
    private RecyclerViewApdapter mRrvAdapterHat;
    private RecyclerViewApdapter mRrvAdapterEaring;
    private HomePresenter mPresenter;
    private ImageView mImgShopCart;
    private CarouselView mCarouselView;
    private List<Integer> listImageHeader;
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
        return rootView;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupCarousel();
        initialData();
    }
    public void initialData() {
        mPresenter = new HomePresenter(getActivity(), this);
        if (InternetHelper.isOnline(getActivity()) == false) {
            Toast.makeText(getActivity(), "No Connection", Toast.LENGTH_LONG).show();
        } else {
            mPresenter.getListProduct();
        }
    }
    public void setLayout(View rootView) {
        mRrvGlasses = rootView.findViewById(R.id.rcv_list_glasses);
        mRrvHat = rootView.findViewById(R.id.rcv_list_hat);
        mRrvEaring = rootView.findViewById(R.id.rcv_list_earing);
        mCarouselView = rootView.findViewById(R.id.carouselView);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL,false);
        //layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        final LinearLayoutManager layoutManager2 = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        //layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        final LinearLayoutManager layoutManager3 = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        //layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRrvGlasses.setLayoutManager(layoutManager);
        mRrvHat.setLayoutManager(layoutManager2);
        mRrvEaring.setLayoutManager(layoutManager3);
        mListGlasses = new ArrayList<>();
        mListHat = new ArrayList<>();
        mListEaring = new ArrayList<>();
        mImgShopCart = rootView.findViewById(R.id.img_shop_cart);
        mImgShopCart.setOnClickListener(this);
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
    @Override
    public void showListProduct(List<Product> productList) {
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
        }
    }


    @Override
    public void showError(String message) {
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