package fpt.com.virtualoutfitroom.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import fpt.com.virtualoutfitroom.R;
import fpt.com.virtualoutfitroom.model.Category;

public class CategoryArAdapter extends RecyclerView.Adapter<CategoryArAdapter.RecyclerViewHolder> {
    private Context mContext;
    private List<Category> mListCategory;
    private RecyclerViewApdapter.OnItemClickedListener mListener;

    public CategoryArAdapter(Context mContext, List<Category> mListCategory) {
        this.mContext = mContext;
        this.mListCategory = mListCategory;
    }


    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.row_item_category_ar,viewGroup,false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        holder.mTxtCategoryName.setText(mListCategory.get(position).getCategoryName());
        if (mListCategory.get(position).getCategoryImg() != null){
            Picasso.get().load(mListCategory.get(position).getCategoryImg()).into(holder.mImgVCategoryImage);
        }else{
            holder.mImgVCategoryImage.setImageResource(R.mipmap.glasses);
        }
        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onItemClicked(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        int count = (mListCategory != null) ? mListCategory.size() : 0;
        return count;
    }

    public void setOnItemClickedListener(RecyclerViewApdapter.OnItemClickedListener listener){
        mListener= listener;
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView mTxtCategoryName;
        ImageView mImgVCategoryImage;
        CardView mCardView;
        public RecyclerViewHolder(View itemView) {
            super(itemView);
            mImgVCategoryImage = itemView.findViewById(R.id.img_cate_view);
            mTxtCategoryName = itemView.findViewById(R.id.txt_category_name);
            mCardView = itemView.findViewById(R.id.card_view_cate);
        }
    }
}
