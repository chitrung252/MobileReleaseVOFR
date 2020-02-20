package fpt.com.virtualoutfitroom.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import fpt.com.virtualoutfitroom.R;
import fpt.com.virtualoutfitroom.model.Product;
import fpt.com.virtualoutfitroom.utils.CurrencyManagement;
import fpt.com.virtualoutfitroom.utils.RefineImage;

public class ProductArTextViewAdapter extends RecyclerView.Adapter<ProductArTextViewAdapter.RecyclerViewHolder> {
    private Context mContext;
    private List<Product> mListProduct;
    private OnItemClickedListener mListener;

    public ProductArTextViewAdapter(Context mContext, List<Product> mListProduct) {
        this.mContext = mContext;
        this.mListProduct = mListProduct;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.row_item_product_view_ar,viewGroup,false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, final int position) {
        holder.imgProductImage.setImageResource(R.mipmap.glasses);
        String urlImage = RefineImage.getUrlImage(mListProduct.get(position).getProductImageList(),"img");
        if(urlImage != null){
            Picasso.get().load(urlImage).into(holder.imgProductImage);
        }
        holder.txtName.setText(mListProduct.get(position).getProductName().toString());
        holder.txtPrice.setText(CurrencyManagement.getPrice(mListProduct.get(position).getProductPrice(),"đ"));
        holder.mImgRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onItemClicked(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        int count = (mListProduct != null) ? mListProduct.size() : 0;
        return count;
    }
    public void removeItem(int position) {
        mListProduct.remove(position);
        notifyItemRemoved(position);
    }
    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView txtName;
        TextView txtPrice;
        ImageView imgProductImage;
        LinearLayout mLnlListProduct;
        ImageView mImgRemove;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            imgProductImage = (ImageView) itemView.findViewById(R.id.img_product_image);
            txtName = (TextView) itemView.findViewById(R.id.txt_product_name);
            txtPrice = (TextView)itemView.findViewById(R.id.txt_product_price);
            mLnlListProduct = itemView.findViewById(R.id.lnl_list_product);
            mImgRemove = (ImageView) itemView.findViewById(R.id.img_remove);
        }
    }
    public interface OnItemClickedListener{
        void onItemClicked(int position);
    }
    public void setOnItemClickedListener(ProductArTextViewAdapter.OnItemClickedListener listener){
        this.mListener= listener;
    }
}
