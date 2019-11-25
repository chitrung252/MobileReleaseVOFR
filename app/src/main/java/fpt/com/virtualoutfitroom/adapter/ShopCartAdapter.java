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

import java.util.ArrayList;
import java.util.List;

import fpt.com.virtualoutfitroom.R;
import fpt.com.virtualoutfitroom.room.OrderItemEntities;
import fpt.com.virtualoutfitroom.utils.ChangeValue;
import fpt.com.virtualoutfitroom.utils.CurrencyManagement;
import fpt.com.virtualoutfitroom.utils.RefineImage;

public class ShopCartAdapter extends RecyclerView.Adapter<ShopCartAdapter.RecyclerViewHolder> {
    private Context mContext;
    private List<OrderItemEntities> mListItem;
    private RecyclerViewApdapter.OnItemClickedListener mListener;

    public ShopCartAdapter(Context mContext, List<OrderItemEntities> mListItem) {
        this.mContext = mContext;
        this.mListItem = mListItem;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.row_item_shop_cart,viewGroup,false);
        return new RecyclerViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder,final int position) {
        String img = RefineImage.getUrlImage(mListItem.get(position).getProduct().getProductImageList(),"img");
        if(img == null || img.length() <= 0){
            holder.imgProductImage.setImageResource(R.mipmap.glasses);
        }else{
            Picasso.get().load(img).placeholder(R.mipmap.glasses).into(holder.imgProductImage);
        }
        holder.txtName.setText(mListItem.get(position).getProduct().getProductName().toString());
        holder.txtPrice.setText("Giá: " + CurrencyManagement.getPrice(mListItem.get(position).getProduct().getProductPrice(),"đ"));
        holder.txtQuanlity.setText("SL: " + mListItem.get(position).getQuality()+"");
        holder.txtTempTotal.setText("Tổng: " +ChangeValue.formatDecimalPrice((double) mListItem.get(position).getTotal()));
        holder.lnlRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onItemClicked(position);
            }
        });
    }
    public void notifyChange(List<OrderItemEntities> orderItemEntities){
        mListItem = new ArrayList<>();
        mListItem = orderItemEntities;
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        int count = (mListItem != null) ? mListItem.size() : 0;
        return count;
    }
    public void removeItem(int position) {
        mListItem.remove(position);
        notifyItemRemoved(position);
    }


    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView txtName;
        TextView txtPrice,txtQuanlity;
        ImageView imgProductImage;
        LinearLayout lnlRoot;
        TextView txtTempTotal;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            imgProductImage = (ImageView) itemView.findViewById(R.id.img_product_image);
            txtName = (TextView) itemView.findViewById(R.id.txt_product_name);
            txtPrice = (TextView)itemView.findViewById(R.id.txt_product_price);
            txtQuanlity  = itemView.findViewById(R.id.txt_product_quality);
            lnlRoot = itemView.findViewById(R.id.lnl_row_product_shop_cart);
            txtTempTotal = itemView.findViewById(R.id.txt_temp_total);
        }
    }
    public interface OnItemClickedListener{
        void onItemClicked(int position);
    }
    public void setOnItemClickedListener(RecyclerViewApdapter.OnItemClickedListener listener){
        mListener= listener;
    }
}
