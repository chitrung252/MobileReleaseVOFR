package fpt.com.virtualoutfitroom.activities;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.downloader.Error;
import com.downloader.OnCancelListener;
import com.downloader.OnDownloadListener;
import com.downloader.OnPauseListener;
import com.downloader.OnProgressListener;
import com.downloader.OnStartOrResumeListener;
import com.downloader.PRDownloader;
import com.downloader.PRDownloaderConfig;
import com.downloader.Progress;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import fpt.com.virtualoutfitroom.R;
import fpt.com.virtualoutfitroom.adapter.ProductSeenApdater;
import fpt.com.virtualoutfitroom.dialog.BottomSheetDialog;
import fpt.com.virtualoutfitroom.model.Product;
import fpt.com.virtualoutfitroom.presenter.detail_product.DetailProductPresenter;
import fpt.com.virtualoutfitroom.utils.CurrencyManagement;
import fpt.com.virtualoutfitroom.utils.GetAbsolutePathFile;
import fpt.com.virtualoutfitroom.utils.RefineImage;
import fpt.com.virtualoutfitroom.utils.SharePreferenceUtils;
import fpt.com.virtualoutfitroom.utils.SpinnerManagement;
import fpt.com.virtualoutfitroom.utils.UrlHelper;
import fpt.com.virtualoutfitroom.views.DetailProductView;
import pl.droidsonroids.gif.GifImageView;


public class DetailProductActivity extends BaseActivity implements View.OnClickListener, DetailProductView {
    private TextView txtName;
    private TextView txtPrice;
    private TextView txtDescription;
    private ImageView imgProductImage;
    private Button mBtnArView;
    private Button btnBottomSheet;
    private Product mProduct;
    private String urlGlb = "http://23.94.26.75/vat-api/image/20191106001440088151.glb";
    private WebView mWvProductModel;
    private ImageView mImgCancel;
    private ImageView mImgBack;
    private Button mBtnShow3D;
    private GifImageView mGifLoading;
    private DetailProductPresenter mPresenter;
    private String mGalleryPath;
    private KProgressHUD hud;
    private LinearLayout mLnlProductSeen;
    private RecyclerView mRcvProductSeen;
    private ProductSeenApdater mRcvAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);
        initView();
        initData();
    }

    private void setSpinner() {
        hud = SpinnerManagement.getSpinner(this);
    }

    public void initView() {
        imgProductImage = findViewById(R.id.img_product_image);
        txtName = findViewById(R.id.txt_product_name);
        txtPrice = findViewById(R.id.txt_product_price);
        txtDescription = findViewById(R.id.txt_description);
        mBtnArView = findViewById(R.id.btn_ar_view);
        mBtnArView.setOnClickListener(this);
        btnBottomSheet = findViewById(R.id.btn_show_bottom_sheet);
        btnBottomSheet.setOnClickListener(this);
        mImgBack = findViewById(R.id.img_back_to_previous);
        mImgBack.setOnClickListener(this);
        mBtnShow3D = findViewById(R.id.button_show_3d);
        mBtnShow3D.setOnClickListener(this);
        mBtnShow3D.setVisibility(View.GONE);
        mGifLoading = findViewById(R.id.gif_view_loading);
        mPresenter = new DetailProductPresenter(this, this);
        mGalleryPath = GetAbsolutePathFile.getRootDirPath(this);
        PRDownloader.initialize(this);
        PRDownloaderConfig config = PRDownloaderConfig.newBuilder()
                .setDatabaseEnabled(true)
                .build();
        PRDownloader.initialize(getApplicationContext(), config);
        mLnlProductSeen = findViewById(R.id.lnl_product_seen);
        mLnlProductSeen.setVisibility(View.GONE);
        mRcvProductSeen = findViewById(R.id.rcv_product_seen);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false);
        mRcvProductSeen.setLayoutManager(layoutManager);
    }

    public void initData() {
        Intent intent = this.getIntent();
        Bundle bundle = intent.getBundleExtra("BUNDLE");
        mProduct = (Product) bundle.getSerializable("PRODUCT");
        imgProductImage.setImageResource(R.drawable.glasses);
        if (mProduct.getProductImageList() != null) {
            String urlImg = RefineImage.getUrlImage(mProduct.getProductImageList(), "img");
            urlGlb = RefineImage.getUrlImage(mProduct.getProductImageList(), "glb");
            String urlSfb = RefineImage.getUrlImage(mProduct.getProductImageList(), "sfb");
            if (urlImg != null) {
                Picasso.get().load(urlImg).into(imgProductImage);
            }
            if (urlGlb != null) {
                mBtnShow3D.setVisibility(View.VISIBLE);
            }
            if (urlSfb == null) {
                mBtnArView.setVisibility(View.GONE);
            }else {
                setSpinner();
                mPresenter.getProduct(mProduct.getId());
            }
        }
        txtName.setText(mProduct.getProductName());
        txtPrice.setText(CurrencyManagement.getPrice(mProduct.getProductPrice(), "đ"));
        txtDescription.setText(mProduct.getDescription());

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_ar_view:
                if (mProduct.getMasterCategoryId() == 4) {
                    viewShoeAr();
                } else {
                    viewFaceAr();
                }
                break;
            case R.id.btn_show_bottom_sheet:
                showDialogBottom();
                break;
            case R.id.img_back_to_previous:
                finish();
                break;
            case R.id.button_show_3d:
                show3d();
                break;
        }
    }

    private void showDialogBottom() {
        Bundle bundle = new Bundle();
        bundle.putSerializable("PRODUCT", mProduct);
        BottomSheetDialog bottomSheet = new BottomSheetDialog();
        bottomSheet.show(getSupportFragmentManager(), "Bottom Sheet");
        bottomSheet.setArguments(bundle);
    }

    public void viewFaceAr() {
        Intent intent = new Intent(this, AugmentedFacesActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("PRODUCT", mProduct);
        intent.putExtra("BUNDLE", bundle);
        startActivity(intent);
    }

    public void viewShoeAr() {
        Intent intent = new Intent(this, ARShoeActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("PRODUCT", mProduct);
        intent.putExtra("BUNDLE", bundle);
        startActivity(intent);
    }

    public void show3d() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_view_3d);
        mWvProductModel = dialog.findViewById(R.id.wv_product_model);
        mImgCancel = dialog.findViewById(R.id.img_cancel);
        mImgCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        WebSettings webSettings = mWvProductModel.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setAllowFileAccessFromFileURLs(true);
        webSettings.setAllowUniversalAccessFromFileURLs(true);
        String contentWebview = "<script type=\"module\" src=\"https://unpkg.com/@google/model-viewer/dist/model-viewer.js\"></script>" +
                "<script nomodule src=\"https://unpkg.com/@google/model-viewer/dist/model-viewer-legacy.js\"></script>" +
                "<model-viewer style=\"width:75%; height:100%\" src=\"" + urlGlb + "\" alt=\"A 3D model of an astronaut\" auto-rotate camera-controls background-color=\"#455A64\"></model-viewer>";
        mWvProductModel.loadDataWithBaseURL(null, contentWebview, "text/html", "UTF-8", null);
        dialog.show();
    }

    @Override
    public void getProductSuccess(Product product) {
        hud.dismiss();
        String currentSfb = RefineImage.getUrlImage(product.getProductImageList(), "sfb");
        if (product != null && currentSfb != null) {
            String sfbOld = checkProductUpdated(product);
            if (sfbOld != null) {
                if (!UrlHelper.getFileNameFromUrl(sfbOld).equals(UrlHelper.getFileNameFromUrl(currentSfb))) {
                    download(currentSfb, UrlHelper.getFileNameFromUrl(currentSfb));
                    checkFileExistAndDelete(UrlHelper.getFileNameFromUrl(sfbOld),"delete");
                }else {
                    checkFileExistAndDelete(sfbOld,"notexist");
                }
            } else {
                download(currentSfb, UrlHelper.getFileNameFromUrl(currentSfb));
            }
        } else {
            mBtnArView.setVisibility(View.GONE);
            Toast.makeText(this, "Tải sản phẩm thất bại", Toast.LENGTH_LONG).show();
        }
    }

    private void checkFileExistAndDelete(String url, String state) {
        File directory = new File(mGalleryPath);
        File[] files = directory.listFiles();
        for (int i = 0; i < files.length; i++) {
            if (files[i].getName().equals(UrlHelper.getFileNameFromUrl(url))) {
                if(state.equals("delete")){ files[i].delete();}
                return;
            }
        }
        if(state.equals("notexist")){
            Toast.makeText(this, "Model không đúng, đang tải lại, ", Toast.LENGTH_LONG).show();
            mBtnArView.setVisibility(View.GONE);
            download(url, UrlHelper.getFileNameFromUrl(url));
        }
    }

    private String checkProductUpdated(Product product) {
        List<Product> list = SharePreferenceUtils.getListObjectSharedPreference(this, "LISTPRODUCT", Product.class);
        if (list != null) {
            updateListProductSeen(list,product);
            int i = 0;
            for (Product item : list) {
                if (item.getId() == product.getId()) {
                    String sfbOld = RefineImage.getUrlImage(item.getProductImageList(), "sfb");
                    product.setClicked(item.getClicked() + 1);
                    list.set(i, product);
                    SharePreferenceUtils.saveListObjectSharedPreference(this, "LISTPRODUCT", list);
                    return sfbOld;
                }
                i++;
            }
        } else {
            list = new ArrayList<>();
        }
        product.setClicked(1);
        list.add(product);
        SharePreferenceUtils.saveListObjectSharedPreference(this, "LISTPRODUCT", list);
        return null;
    }

    private void updateListProductSeen(List<Product> list,Product currentProduct){
        List<Product> listCurrent = setPriorityProduct(list,currentProduct);
        mLnlProductSeen.setVisibility(View.VISIBLE);
        mRcvAdapter = new ProductSeenApdater(this,listCurrent);
        mRcvProductSeen.setAdapter(mRcvAdapter);
        mRcvAdapter.setOnItemClickedListener(new ProductSeenApdater.OnItemClickedListener() {
            @Override
            public void onItemClicked(int position) {
                Intent intent = new Intent(getApplication(), DetailProductActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("PRODUCT", listCurrent.get(position));
                intent.putExtra("BUNDLE", bundle);
                startActivity(intent);
            }
        });
    }

    private List<Product> setPriorityProduct(List<Product> list,Product currentProduct){
        List<Product> listCurrent = new ArrayList<>();
        for (Product item: list) {
            if(item.getId() != currentProduct.getId()){
                listCurrent.add(item);
            }
        }
        Collections.sort(listCurrent, new Comparator<Product>() {
            @Override
            public int compare(Product product, Product product2) {
                return product.getClicked() > product2.getClicked() ? -1: (product.getClicked() < product2.getClicked())? 1 : 0;
            }
        });
        return listCurrent;
    }
    private void download(String urlFile, String nameFile) {
        int downloadId = PRDownloader.download(urlFile, mGalleryPath, nameFile)
                .build()
                .setOnStartOrResumeListener(new OnStartOrResumeListener() {
                    @Override
                    public void onStartOrResume() {
                        mBtnArView.setVisibility(View.GONE);
                        mGifLoading.setVisibility(View.VISIBLE);
                    }
                })
                .setOnPauseListener(new OnPauseListener() {
                    @Override
                    public void onPause() {

                    }
                })
                .setOnCancelListener(new OnCancelListener() {
                    @Override
                    public void onCancel() {

                    }
                })
                .setOnProgressListener(new OnProgressListener() {
                    @Override
                    public void onProgress(Progress progress) {

                    }
                }).start(new OnDownloadListener() {
                    @Override
                    public void onDownloadComplete() {
                        mBtnArView.setVisibility(View.VISIBLE);
                        mGifLoading.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Error error) {
                        mBtnArView.setVisibility(View.GONE);
                        Toast.makeText(getApplication(), "Đường dẫn không thể tải", Toast.LENGTH_LONG).show();
                    }
                });

    }

    @Override
    public void showError(String message) {
        Toast.makeText(this, "Tải sản phẩm thất bại", Toast.LENGTH_LONG).show();
    }
}
