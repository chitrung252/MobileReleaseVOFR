package fpt.com.virtualoutfitroom.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import fpt.com.virtualoutfitroom.R;
import fpt.com.virtualoutfitroom.adapter.OrderAdapter;
import fpt.com.virtualoutfitroom.adapter.RecyclerViewApdapter;
import fpt.com.virtualoutfitroom.model.OrderHistory;
import fpt.com.virtualoutfitroom.presenter.orders.GetOrderPresenter;
import fpt.com.virtualoutfitroom.utils.BundleString;
import fpt.com.virtualoutfitroom.utils.SharePreferenceUtils;
import fpt.com.virtualoutfitroom.views.OrderView;

/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryFragment extends Fragment implements OrderView {
    private OrderAdapter mRcvOrderAdapter;
    private RecyclerView mRcvOrder;
    private List<OrderHistory> mListOrder;
    private View mView;
    private GetOrderPresenter mGetOrderPresenter;
    private LinearLayout mLnlHasHistory;
    private LinearLayout mLnlNoHistory;

    public HistoryFragment() {
        // Required empty public constructor
    }

    public static HistoryFragment newInstance() {
        HistoryFragment fragment = new HistoryFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_history, container, false);
        setLayout();
        return mView;
    }

    public void setLayout(){
        mRcvOrder = mView.findViewById(R.id.rvc_history);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,false);
        mRcvOrder.setLayoutManager(layoutManager);
        mListOrder = new ArrayList<>();
        mGetOrderPresenter = new GetOrderPresenter(getActivity(),this);
        String token = SharePreferenceUtils.getStringSharedPreference(getActivity(), BundleString.TOKEN);
        String accountId = SharePreferenceUtils.getStringSharedPreference(getActivity(), BundleString.USERID);
        mGetOrderPresenter.getOrder(token, accountId);
        mLnlHasHistory = mView.findViewById(R.id.lnl_has_history);
        mLnlNoHistory = mView.findViewById(R.id.lnl_no_history);
    }

    public void updateUI(){
        mRcvOrderAdapter = new OrderAdapter(getActivity(),mListOrder);
        mRcvOrder.setAdapter(mRcvOrderAdapter);
        mRcvOrderAdapter.setOnItemClickedListener(new OrderAdapter.OnItemClickedListener() {
            @Override
            public void onItemClicked(int position) {
                Toast.makeText(getActivity(),"ahuhu", Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public void getOrderSuccess(List<OrderHistory> orderHistory) {
        if(orderHistory != null){
            mLnlHasHistory.setVisibility(View.VISIBLE);
            mLnlNoHistory.setVisibility(View.GONE);
            mListOrder = orderHistory;
            updateUI();
        }else{
            mLnlHasHistory.setVisibility(View.GONE);
            mLnlNoHistory.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void getOrderFail(String message) {

    }
}
