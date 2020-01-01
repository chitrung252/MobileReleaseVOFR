package fpt.com.virtualoutfitroom.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import fpt.com.virtualoutfitroom.R;
import fpt.com.virtualoutfitroom.adapter.CategoryArAdapter;
import fpt.com.virtualoutfitroom.adapter.RecyclerViewApdapter;
import fpt.com.virtualoutfitroom.model.Category;
import fpt.com.virtualoutfitroom.model.Product;
import fpt.com.virtualoutfitroom.utils.CurrencyManagement;
import fpt.com.virtualoutfitroom.utils.RefineImage;

public class BottomSheetCateAr extends BottomSheetDialogFragment implements View.OnClickListener {
    private BottomSheetBehavior mBehavior;
    private LinearLayout mLnlDimiss;
    private List<Category> mListCate;
    private RecyclerView mRcvCate;
    private CategoryArAdapter mRcvAdapter;
    private FragmentCateListener mFragment;

    @Override
    public void onStart() {
        super.onStart();
        mBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        android.support.design.widget.BottomSheetDialog dialog = (android.support.design.widget.BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        View view = View.inflate(getContext(), R.layout.bottom_sheet_cate_ar, null);
        initialView(view);
        dialog.setContentView(view);
        mBehavior = BottomSheetBehavior.from((View) view.getParent());
        initialData();
        return dialog;
    }

    public void initialView(View view) {
        mRcvCate = view.findViewById(R.id.rcv_cate);
        mLnlDimiss = view.findViewById(R.id.lnl_dismiss);
        mLnlDimiss.setOnClickListener(this);
        mListCate = new ArrayList<>();
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL,false);
        mRcvCate.setLayoutManager(layoutManager);
    }

    public void initialData(){
        Bundle bundle = this.getArguments();
        mListCate =(List<Category>) bundle.getSerializable("LISTCATE");
        updateUI();
    }
    private void updateUI(){
        mRcvAdapter = new CategoryArAdapter(getActivity(),mListCate);
        mRcvCate.setAdapter(mRcvAdapter);
        mRcvAdapter.setOnItemClickedListener(new RecyclerViewApdapter.OnItemClickedListener() {
            @Override
            public void onItemClicked(int position) {
                mFragment.sendDataCate(mListCate, mListCate.get(position));
                dismissBottomSheet();
            }
        });
    }
    private void  dismissBottomSheet(){
        getDialog().cancel();
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mFragment = (FragmentCateListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " You must implement FragmentCateListener");
        }
    }
    public interface FragmentCateListener {
        void sendDataCate(List<Category> listCate, Category category);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.lnl_dismiss: dismissBottomSheet();
            break;
        }
    }

}
