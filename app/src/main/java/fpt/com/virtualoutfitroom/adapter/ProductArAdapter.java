package fpt.com.virtualoutfitroom.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.downloader.Progress;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import fpt.com.virtualoutfitroom.R;
import fpt.com.virtualoutfitroom.activities.DetailProductActivity;
import fpt.com.virtualoutfitroom.model.Product;
import fpt.com.virtualoutfitroom.utils.GetAbsolutePathFile;
import fpt.com.virtualoutfitroom.utils.RefineImage;
import fpt.com.virtualoutfitroom.utils.SharePreferenceUtils;
import fpt.com.virtualoutfitroom.utils.UrlHelper;
import pl.droidsonroids.gif.GifImageView;

public class ProductArAdapter extends RecyclerView.Adapter<ProductArAdapter.RecyclerViewHolder> {
    private Context mContext;
    private List<Product> mListProduct;
    private OnItemClickedListener mListener;
    private String mGalleryPath;
    private RecyclerViewHolder mHolder;
    private int mPosition;

    public ProductArAdapter(Context mContext, List<Product> mListProduct) {
        this.mContext = mContext;
        this.mListProduct = mListProduct;
        this.mGalleryPath = GetAbsolutePathFile.getRootDirPath(mContext);
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.row_item_product_ar, viewGroup, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        mHolder = holder;
        mPosition = position;
        holder.imgProductImage.setVisibility(View.VISIBLE);
        holder.txtName.setVisibility(View.VISIBLE);
        holder.mGifView.setVisibility(View.GONE);
        holder.imgProductImage.setImageResource(R.mipmap.glasses);
        String urlImage = RefineImage.getUrlImage(mListProduct.get(position).getProductImageList(), "img");
        if (urlImage != null) {
            Picasso.get().load(urlImage).into(mHolder.imgProductImage);
        }
        holder.txtName.setText(mListProduct.get(position).getProductName().toString());
        holder.lnlRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onItemClicked(position);
            }
        });
        String currentSfb = RefineImage.getUrlImage(mListProduct.get(position).getProductImageList(), "sfb");
        String sfbOld = checkProductUpdated(mListProduct.get(position));
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
            download(url, UrlHelper.getFileNameFromUrl(url));
        }
    }
    private void download(String urlFile, String nameFile) {
        int downloadId = PRDownloader.download(urlFile, mGalleryPath, nameFile)
                .build()
                .setOnStartOrResumeListener(new OnStartOrResumeListener() {
                    @Override
                    public void onStartOrResume() {
                        mHolder.mLnlProductValid.setVisibility(View.GONE);
                        mHolder.mGifView.setVisibility(View.VISIBLE);
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
                        mHolder.mLnlProductValid.setVisibility(View.VISIBLE);
                        mHolder.mGifView.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Error error) {
                        mHolder.mLnlProductValid.setVisibility(View.VISIBLE);
                        mHolder.mGifView.setVisibility(View.GONE);
                        mHolder.mImgBan.setVisibility(View.VISIBLE);
                        mHolder.lnlRoot.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                            }
                        });
                    }
                });
    }
    private String checkProductUpdated(Product product) {
        List<Product> list = SharePreferenceUtils.getListObjectSharedPreference(mContext, "LISTPRODUCT", Product.class);
        if (list != null) {
            int i = 0;
            for (Product item : list) {
                if (item.getId() == product.getId()) {
                    String sfbOld = RefineImage.getUrlImage(item.getProductImageList(), "sfb");
                    product.setClicked(item.getClicked() + 1);
                    list.set(i, product);
                    SharePreferenceUtils.saveListObjectSharedPreference(mContext, "LISTPRODUCT", list);
                    return sfbOld;
                }
                i++;
            }
        } else {
            list = new ArrayList<>();
        }
        product.setClicked(1);
        list.add(product);
        SharePreferenceUtils.saveListObjectSharedPreference(mContext, "LISTPRODUCT", list);
        return null;
    }


    @Override
    public int getItemCount() {
        int count = (mListProduct != null) ? mListProduct.size() : 0;
        return count;
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView txtName;
        ImageView imgProductImage;
        LinearLayout lnlRoot;
        GifImageView mGifView;
        ImageView mImgBan;
        LinearLayout mLnlProductValid;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            imgProductImage = (ImageView) itemView.findViewById(R.id.img_product_image);
            txtName = (TextView) itemView.findViewById(R.id.txt_product_name);
            mGifView = itemView.findViewById(R.id.gif_loading_2);
            mImgBan = itemView.findViewById(R.id.img_ban);
            mLnlProductValid = itemView.findViewById(R.id.lnl_product_valid);
            lnlRoot = itemView.findViewById(R.id.lnl_row_product_ar);
        }
    }

    public interface OnItemClickedListener {
        void onItemClicked(int position);
    }

    public void setOnItemClickedListener(OnItemClickedListener listener) {
        this.mListener = listener;
    }
}
