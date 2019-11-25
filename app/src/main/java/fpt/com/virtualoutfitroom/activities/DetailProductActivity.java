package fpt.com.virtualoutfitroom.activities;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kaopiz.kprogresshud.KProgressHUD;
import com.squareup.picasso.Picasso;

import fpt.com.virtualoutfitroom.R;
import fpt.com.virtualoutfitroom.dialog.BottomSheetDialog;
import fpt.com.virtualoutfitroom.model.Product;
import fpt.com.virtualoutfitroom.model.ProductImage;
import fpt.com.virtualoutfitroom.utils.CurrencyManagement;
import fpt.com.virtualoutfitroom.utils.RefineImage;
import fpt.com.virtualoutfitroom.utils.SpinnerManagement;


public class DetailProductActivity extends BaseActivity implements View.OnClickListener{
   private TextView txtName;
   private TextView txtPrice;
   private TextView txtDescription;
   private ImageView imgProductImage;
   private Button btnArView;
   private Button btnBottomSheet;
   private Product mProduct;
   private String urlGlb ="http://107.150.52.213/api-votf/image/20191106001440088151.glb";
   private WebView mWvProductModel;
   private ImageView mImgCancel;
   private ImageView mImgBack;
   private Button mBtnShow3D;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);
        initView();
        initData();
    }
    public void initView(){
        imgProductImage = findViewById(R.id.img_product_image);
        txtName = findViewById(R.id.txt_product_name);
        txtPrice = findViewById(R.id.txt_product_price);
        txtDescription = findViewById(R.id.txt_description);
        btnArView = findViewById(R.id.btn_ar_view);
        btnArView.setOnClickListener(this);
        btnBottomSheet = findViewById(R.id.btn_show_bottom_sheet);
        btnBottomSheet.setOnClickListener(this);
        mImgBack = findViewById(R.id.img_back_to_previous);
        mImgBack.setOnClickListener(this);
        mBtnShow3D= findViewById(R.id.button_show_3d);
        mBtnShow3D.setOnClickListener(this);
        mBtnShow3D.setVisibility(View.GONE);

    }
    public void initData(){
        Intent intent = this.getIntent();
        Bundle bundle = intent.getBundleExtra("BUNDLE");
        mProduct = (Product)bundle.getSerializable("PRODUCT");
        imgProductImage.setImageResource(R.drawable.glasses);
        if(mProduct.getProductImageList() != null){
            String urlImg = RefineImage.getUrlImage(mProduct.getProductImageList(),"img");
            urlGlb = RefineImage.getUrlImage(mProduct.getProductImageList(),"glb");
            if(urlImg != null){
                Picasso.get().load(urlImg).into(imgProductImage);
            }
            if(urlGlb != null){
                mBtnShow3D.setVisibility(View.VISIBLE);
            }
        }
        txtName.setText(mProduct.getProductName());
        txtPrice.setText(CurrencyManagement.getPrice(mProduct.getProductPrice(),"đ"));
        txtDescription.setText(mProduct.getDescription());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_ar_view:
                Toast.makeText(this, mProduct.getMasterCategoryId() + "", Toast.LENGTH_LONG).show();
                if(mProduct.getMasterCategoryId() == 4){
                    viewShoeAr();
                } else {
                    viewFaceAr();
                }
                break;
            case R.id.btn_show_bottom_sheet: showDialogBottom();
            break;
            case R.id.img_back_to_previous: finish();
            break;
            case R.id.button_show_3d:
                show3d();

                break;

        }
    }

    private void showDialogBottom() {
        Bundle bundle = new Bundle();
        bundle.putSerializable("PRODUCT",mProduct);
        BottomSheetDialog bottomSheet = new BottomSheetDialog();
        bottomSheet.show(getSupportFragmentManager(),"Bottom Sheet");
        bottomSheet.setArguments(bundle);
    }

    public void viewFaceAr(){
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

    public void show3d(){
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
                "<model-viewer style=\"width:75%; height:100%\" src=\"" + urlGlb +"\" alt=\"A 3D model of an astronaut\" auto-rotate camera-controls background-color=\"#455A64\"></model-viewer>";
        mWvProductModel.loadDataWithBaseURL(null,contentWebview,"text/html","UTF-8",null);
        dialog.show();
    }

}
