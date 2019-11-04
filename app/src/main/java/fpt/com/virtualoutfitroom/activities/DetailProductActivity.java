package fpt.com.virtualoutfitroom.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import fpt.com.virtualoutfitroom.R;
import fpt.com.virtualoutfitroom.dialog.BottomSheetDialog;
import fpt.com.virtualoutfitroom.model.Product;


public class DetailProductActivity extends BaseActivity implements View.OnClickListener{
    TextView txtName;
    TextView txtPrice;
    ImageView imgProductImage;
    Button btnPayment;
    Button btnBottomSheet;
    WebView mWvProductModel;
    Product mProduct;
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
        btnPayment = findViewById(R.id.btn_payment);
        btnPayment.setOnClickListener(this);
        btnBottomSheet = findViewById(R.id.btn_show_bottom_sheet);
        btnBottomSheet.setOnClickListener(this);
//        mWvProductModel = findViewById(R.id.wv_product_model);
    }
    public void initData(){
        Intent intent = this.getIntent();
        Bundle bundle = intent.getBundleExtra("BUNDLE");
        mProduct = (Product)bundle.getSerializable("PRODUCT");
       imgProductImage.setImageResource(R.drawable.glasses);
        txtName.setText(mProduct.getProductName());
        txtPrice.setText(mProduct.getProductPrice());
//        WebSettings webSettings = mWvProductModel.getSettings();
//        webSettings.setJavaScriptEnabled(true);
//        webSettings.setAllowFileAccess(true);
//        webSettings.setAllowFileAccessFromFileURLs(true);
//        webSettings.setAllowUniversalAccessFromFileURLs(true);
//        String contentWebview = "<script type=\"module\" src=\"https://unpkg.com/@google/model-viewer/dist/model-viewer.js\"></script>" +
//                "<script nomodule src=\"https://unpkg.com/@google/model-viewer/dist/model-viewer-legacy.js\"></script>" +
//                "<h1>Model Viewer</h1>" +
//                "<model-viewer src=\"https://cdn.glitch.com/36cb8393-65c6-408d-a538-055ada20431b/Astronaut.glb\" alt=\"A 3D model of an astronaut\" auto-rotate camera-controls background-color=\"#455A64\"></model-viewer>";
//        mWvProductModel.loadDataWithBaseURL(null,contentWebview,"text/html","UTF-8",null);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_payment: payment();
                break;
            case R.id.btn_show_bottom_sheet: showDialogBottom();
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

    public void payment(){
        Intent intent = new Intent(this, AugmentedFacesActivity.class);
        startActivity(intent);
    }

}
