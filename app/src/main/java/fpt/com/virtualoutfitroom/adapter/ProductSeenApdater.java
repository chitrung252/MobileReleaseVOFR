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

public class ProductSeenApdater extends RecyclerView.Adapter<ProductSeenApdater.RecyclerViewHolder> {
    private Context mContext;
    private List<Product> mListProduct;
    private OnItemClickedListener mListener;

    public ProductSeenApdater(Context mContext , List<Product> mListProduct) {
        this.mContext = mContext;
        this.mListProduct = mListProduct;
    }
    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.row_item_product_seen,viewGroup,false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        holder.imgProductImage.setImageResource(R.mipmap.glasses);
        String urlImage = RefineImage.getUrlImage(mListProduct.get(position).getProductImageList(),"img");
        if(urlImage != null){
            Picasso.get().load(urlImage).into(holder.imgProductImage);
        }
        holder.txtName.setText(mListProduct.get(position).getProductName().toString());
        holder.lnlRoot.setOnClickListener(new View.OnClickListener() {
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

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView txtName;
        ImageView imgProductImage;
        LinearLayout lnlRoot;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            imgProductImage = (ImageView) itemView.findViewById(R.id.img_product_image);
            txtName = (TextView) itemView.findViewById(R.id.txt_product_name);
            lnlRoot = itemView.findViewById(R.id.lnl_row_product_seen);
        }
    }
    public interface OnItemClickedListener{
        void onItemClicked(int position);
    }
    public void setOnItemClickedListener(OnItemClickedListener listener){
        this.mListener= listener;
    }
}
