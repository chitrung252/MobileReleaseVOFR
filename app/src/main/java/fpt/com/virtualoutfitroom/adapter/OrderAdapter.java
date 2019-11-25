package fpt.com.virtualoutfitroom.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import fpt.com.virtualoutfitroom.R;
import fpt.com.virtualoutfitroom.model.OrderHistory;
import fpt.com.virtualoutfitroom.utils.CurrencyManagement;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.RecyclerViewHolder> {
    private Context mContext;
    private List<OrderHistory> mListOrder;
    private OnItemClickedListener mListener;

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.row_item_history_order, viewGroup, false);
        return new RecyclerViewHolder(view);
    }

    public OrderAdapter(Context mContext, List<OrderHistory> mListOrder) {
        this.mContext = mContext;
        this.mListOrder = mListOrder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        holder.mTxtOrderName.setText(mListOrder.get(position).getOrderId());
        holder.mTxtTotal.setText(CurrencyManagement.getPrice(mListOrder.get(position).getTotal(), "đ"));
        int status = mListOrder.get(position).getStatus();
        if (status == 1) {
            holder.mTxtStatus.setText("Đang xác nhận");
            holder.mTxtStatus.setTextColor(holder.mTxtStatus.getResources().getColor(R.color.color_cus_orange));
        } else if (status == 2) {
            holder.mTxtStatus.setText("Thành công");
            holder.mTxtStatus.setTextColor(holder.mTxtStatus.getResources().getColor(R.color.color_cus_red));
        } else {
            holder.mTxtStatus.setText("Thất bại");
            holder.mTxtStatus.setTextColor(holder.mTxtStatus.getResources().getColor(R.color.color_cus_light_gray));
        }
        holder.mCVOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onItemClicked(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        int count = (mListOrder != null) ? mListOrder.size() : 0;
        return count;
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView mTxtOrderName;
        TextView mTxtTotal;
        TextView mTxtStatus;
        CardView mCVOrder;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            mTxtOrderName = itemView.findViewById(R.id.txt_order_name);
            mTxtTotal = itemView.findViewById(R.id.txt_total);
            mTxtStatus = itemView.findViewById(R.id.txt_status);
            mCVOrder = itemView.findViewById(R.id.card_view_row_order);
        }
    }

    public interface OnItemClickedListener {
        void onItemClicked(int position);
    }

    public void setOnItemClickedListener(OrderAdapter.OnItemClickedListener listener) {
        this.mListener = listener;
    }
}
