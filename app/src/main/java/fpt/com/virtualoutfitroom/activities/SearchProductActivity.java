package fpt.com.virtualoutfitroom.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import fpt.com.virtualoutfitroom.R;
import fpt.com.virtualoutfitroom.adapter.ProductCateAdapter;
import fpt.com.virtualoutfitroom.model.Product;
import fpt.com.virtualoutfitroom.utils.GridSpacingItemDecoration;

public class SearchProductActivity extends BaseActivity implements View.OnClickListener {
    private RecyclerView mRcvSearch;
    private ProductCateAdapter mRcvSearchAdapter;
    private List<Product> mListProduct;
    private ImageView mImgBack;
    private TextView mTxtNotFound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_product);
        initialView();
        initialData();
    }

    private void initialView(){
        mTxtNotFound = findViewById(R.id.txt_not_found);
        mRcvSearch = findViewById(R.id.rcv_search_product);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        mRcvSearch.setLayoutManager(layoutManager);
        mListProduct = new ArrayList<>();
        mImgBack = findViewById(R.id.img_back_to_previous);
        mImgBack.setOnClickListener(this);
        mRcvSearch.setHasFixedSize(true);
        int numberOfColumns = calculateNumberOfColumns(this);
        GridLayoutManager gridLayoutManager = new
                GridLayoutManager(this, numberOfColumns);
        mRcvSearch.setLayoutManager(gridLayoutManager);
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.dp15);
        mRcvSearch.addItemDecoration(new GridSpacingItemDecoration
                (numberOfColumns, spacingInPixels, true));
    }

    private void initialData(){
        Intent intent = this.getIntent();
        Bundle bundle = intent.getBundleExtra("BUNDLE");
        mListProduct = (List<Product>) bundle.getSerializable("LISTPRODUCT");
        if (mListProduct.size() == 0){
            mTxtNotFound.setVisibility(View.VISIBLE);
        }else {
            updateUI();
        }
    }

    private void updateUI(){
        mRcvSearchAdapter = new ProductCateAdapter(this,mListProduct);
        mRcvSearch.setAdapter(mRcvSearchAdapter);
        mRcvSearchAdapter.setOnItemClickedListener(new ProductCateAdapter.OnItemClickedListener() {
            @Override
            public void onItemClicked(int position) {
                Intent intent = new Intent(SearchProductActivity.this, DetailProductActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("PRODUCT", mListProduct.get(position));
                intent.putExtra("BUNDLE", bundle);
                startActivity(intent);
            }
        });
    }
    private int calculateNumberOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int noOfColumns = (int) (dpWidth / 180);
        return noOfColumns;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.img_back_to_previous: finish(); break;
        }
    }
}
