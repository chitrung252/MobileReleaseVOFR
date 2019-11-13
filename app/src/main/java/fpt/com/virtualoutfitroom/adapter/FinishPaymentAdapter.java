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
import fpt.com.virtualoutfitroom.model.ProductPayment;
import fpt.com.virtualoutfitroom.utils.ChangeValue;

public class FinishPaymentAdapter extends RecyclerView.Adapter<FinishPaymentAdapter.RecyclerViewHolder> {
    private Context mContext;
    private List<ProductPayment> data;

    public FinishPaymentAdapter(Context mContext, List<ProductPayment> data) {
        this.mContext = mContext;
        this.data = data;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.row_finish_payment,viewGroup,false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int pos) {
        Picasso.get().load(data.get(pos).getProductUrl()).into(holder.mImgProduct);
        holder.mTxtNameProduct.setText(data.get(pos).getPrductName());
        holder.mTxtPriceProduct.setText(ChangeValue.formatDecimalPrice(data.get(pos).getPriceProduct()));
        holder.mTxtQuantityProduct.setText(data.get(pos).getQuantiryProduct() + "");
    }
    @Override
    public int getItemCount() {
        int count = (data != null) ?data.size() : 0;
        return count;    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView mTxtNameProduct,mTxtPriceProduct,mTxtQuantityProduct;
        ImageView mImgProduct;
        public RecyclerViewHolder(View itemView) {
            super(itemView);
            mTxtNameProduct = itemView.findViewById(R.id.txt_name_product_payment);
            mTxtPriceProduct = itemView.findViewById(R.id.txt_price_payment);
            mTxtQuantityProduct = itemView.findViewById(R.id.txt_quantity_payment);
            mImgProduct = itemView.findViewById(R.id.image_view_product_payment);
        }
    }
}
