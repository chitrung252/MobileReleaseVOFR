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

import java.util.List;

import fpt.com.virtualoutfitroom.R;
import fpt.com.virtualoutfitroom.room.OrderItemEntities;

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
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.row_item_shop_cart,viewGroup,false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder,final int position) {
        holder.imgProductImage.setImageResource(R.mipmap.glasses);
        holder.txtName.setText(mListItem.get(position).getProduct().getProductName().toString());
        holder.txtPrice.setText(mListItem.get(position).getProduct().getProductPrice().toString());
        holder.lnlRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onItemClicked(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        int count = (mListItem != null) ? mListItem.size() : 0;
        return count;
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView txtName;
        TextView txtPrice;
        ImageView imgProductImage;
        LinearLayout lnlRoot;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            imgProductImage = (ImageView) itemView.findViewById(R.id.img_product_image);
            txtName = (TextView) itemView.findViewById(R.id.txt_product_name);
            txtPrice = (TextView)itemView.findViewById(R.id.txt_product_price);
            lnlRoot = itemView.findViewById(R.id.lnl_row_product_shop_cart);
        }
    }
    public interface OnItemClickedListener{
        void onItemClicked(int position);
    }
    public void setOnItemClickedListener(RecyclerViewApdapter.OnItemClickedListener listener){
        mListener= listener;
    }
}
