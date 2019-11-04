package fpt.com.virtualoutfitroom.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import fpt.com.virtualoutfitroom.R;

public class AddressPaymentAdapter extends RecyclerView.Adapter<AddressPaymentAdapter.RecyclerViewHolder> {
    private Context mContext;
    private List<String> mListAddrees;
    public AddressPaymentAdapter(Context mContexr, List<String> mListAddrees) {
        this.mContext = mContexr;
        this.mListAddrees = mListAddrees;
    }
    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.row_address_payment,viewGroup,false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder recyclerViewHolder, int pos) {
        recyclerViewHolder.mTxtNameAddress.setText(mListAddrees.get(pos));
    }
    @Override
    public int getItemCount() {
        int count = (mListAddrees != null) ? mListAddrees.size() : 0;
        return count;
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView mTxtNameAddress;
        public RecyclerViewHolder(View itemView) {
            super(itemView);
            mTxtNameAddress = itemView.findViewById(R.id.txt_address_name);
        }
    }
}
