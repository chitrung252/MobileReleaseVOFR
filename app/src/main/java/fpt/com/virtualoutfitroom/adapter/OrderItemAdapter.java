package fpt.com.virtualoutfitroom.adapter;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import fpt.com.virtualoutfitroom.R;
import fpt.com.virtualoutfitroom.model.OrderItem;
import fpt.com.virtualoutfitroom.utils.CurrencyManagement;
import fpt.com.virtualoutfitroom.utils.RefineImage;

public class OrderItemAdapter extends RecyclerView.Adapter<OrderItemAdapter.RecyclerViewHolder>  {
    private Context mContext;
    private List<OrderItem> mListOrderItem;

    public OrderItemAdapter(Context mContext, List<OrderItem> mListOrderItem) {
        this.mContext = mContext;
        this.mListOrderItem = mListOrderItem;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.row_item_order_detail, viewGroup, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder,final int position) {
        String img = RefineImage.getUrlImage(mListOrderItem.get(position).getProduct().getProductImageList(), "img") ;
        if(img == null){
            holder.mImgProduct.setImageResource(R.mipmap.glasses);
        }
        Picasso.get().load(img).placeholder(R.mipmap.glasses).into(holder.mImgProduct);
        holder.mTxtProName.setText(mListOrderItem.get(position).getProduct().getProductName().toString());
        holder.mTxtQuantity.setText(mListOrderItem.get(position).getQuantity() + "");
        holder.mTxtPrice.setText(CurrencyManagement.getPrice(mListOrderItem.get(position).getPrice(),"đ"));
        String a = mListOrderItem.get(position).getProduct().getProductName();
        holder.mTxtProName.setText(mListOrderItem.get(position).getProduct().getProductName());
    }

    @Override
    public int getItemCount() {
        int count = (mListOrderItem != null) ? mListOrderItem.size() : 0;
        return count;
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        ImageView mImgProduct;
        TextView mTxtProName;
        TextView mTxtQuantity;
        TextView mTxtPrice;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            mImgProduct = (ImageView) itemView.findViewById(R.id.img_product_image_order);
            mTxtProName = (TextView) itemView.findViewById(R.id.txt_product_name_order);
            mTxtQuantity = (TextView) itemView.findViewById(R.id.txt_product_quantity_order);
            mTxtPrice = (TextView) itemView.findViewById(R.id.txt_product_price_order);
        }
    }

}
