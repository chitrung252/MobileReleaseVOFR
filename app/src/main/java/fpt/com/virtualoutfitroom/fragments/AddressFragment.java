package fpt.com.virtualoutfitroom.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import fpt.com.virtualoutfitroom.R;
import fpt.com.virtualoutfitroom.adapter.AddressPaymentAdapter;
import fpt.com.virtualoutfitroom.model.Account;
import fpt.com.virtualoutfitroom.presenter.accounts.InformationAccountPresenter;
import fpt.com.virtualoutfitroom.room.AccountItemEntities;
import fpt.com.virtualoutfitroom.utils.FragmentSentData;
import fpt.com.virtualoutfitroom.views.GetInforAccountView;
public class AddressFragment extends Fragment implements View.OnClickListener, GetInforAccountView {
    private View mView;
    private EditText mEdtName,mEdtPhone,mEdtEmail,mEdtAddress;
    private LinearLayout mlnlName,mLnlEmail,mLnlPhone,mLnlAddress;
    private TextView mTxtName,mTxtPhone,mTxtEmail,mTxtAddress;
    private boolean isClickName = false,isClickEmail = false,isClickPhone = false,isClickAddress = false;
    private Account mAccount;
    private String name,email,phone,address;
    private InformationAccountPresenter informationAccountPresenter;
    private FirstFragmentListener mFragmentSentData;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_address, container, false);
        return mView;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialView();
        initialData();
    }

    public AddressFragment() {
    }
    public static Fragment newInstance(){
        AddressFragment fragment = new AddressFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
    public  void initialView(){
        mEdtName = mView.findViewById(R.id.edt_name_payment);
        mEdtEmail = mView.findViewById(R.id.edt_email_payment);
        mEdtPhone = mView.findViewById(R.id.edt_phone_payment);
        mEdtAddress = mView.findViewById(R.id.edt_address_payment);
        mlnlName = mView.findViewById(R.id.lnl_button_name);
        mLnlEmail = mView.findViewById(R.id.lnl_button_email);
        mLnlPhone = mView.findViewById(R.id.lnl_button_phone);
        mLnlAddress = mView.findViewById(R.id.lnl_button_address);
        mTxtName = mView.findViewById(R.id.txt_name_payment);
        mTxtEmail = mView.findViewById(R.id.txt_email_payment);
        mTxtPhone = mView.findViewById(R.id.txt_phone_payment);
        mTxtAddress = mView.findViewById(R.id.txt_address_payment);
    }
    public  void initialData(){
         mlnlName.setOnClickListener(this::onClick);
         mLnlEmail.setOnClickListener(this::onClick);
         mLnlPhone.setOnClickListener(this::onClick);
         mLnlAddress.setOnClickListener(this::onClick);
        informationAccountPresenter = new InformationAccountPresenter(getActivity().getApplication(),this);
        informationAccountPresenter.getAccountFromRoom();

    }
    @Override
    public void onClick(View view) {
            int id = view.getId();
            switch (id){
              case   R.id.lnl_button_name:
                  setEditTextNamẹ̣();
                  break;
                case R.id.lnl_button_email:
                    setEditTextNEmaiḷ̣();
                    break;
                case R.id.lnl_button_phone:
                    setEditTextPhonẹ();
                    break;
                case R.id.lnl_button_address:
                    setEditTextAddress();
                    break;
            }
    }
    public void setEditTextNamẹ̣(){
        if(isClickName == true){
            name =  mEdtName.getText().toString()+"";
            mTxtName.setText(name);
            sendData(name,email,phone,address);
            mEdtName.setVisibility(View.GONE);
            mTxtName.setVisibility(View.VISIBLE);
            isClickName = false;
        }
        else{
            isClickName = true;
            mEdtName.setText(mTxtName.getText().toString()+"");
            mTxtName.setVisibility(View.GONE);
            mEdtName.setVisibility(View.VISIBLE);
        }
    }

    public void setEditTextNEmaiḷ̣(){
        if(isClickEmail == true){
            email = mEdtEmail.getText().toString()+"";
            mTxtEmail.setText(email);
            sendData(name,email,phone,address);
            mEdtEmail.setVisibility(View.GONE);
            mTxtEmail.setVisibility(View.VISIBLE);
            isClickEmail = false;
        }
        else{
            isClickEmail = true;
            mEdtEmail.setText(mTxtEmail.getText().toString()+"");
            mTxtEmail.setVisibility(View.GONE);
            mEdtEmail.setVisibility(View.VISIBLE);
        }
    }
    public void setEditTextPhonẹ(){
        if(isClickPhone == true){
            phone = mEdtPhone.getText().toString()+"";
            mTxtPhone.setText(phone);
            sendData(name,email,phone,address);
            mEdtPhone.setVisibility(View.GONE);
            mTxtPhone.setVisibility(View.VISIBLE);
            isClickPhone = false;
        }
        else{
            isClickPhone = true;
            mEdtPhone.setText(mTxtPhone.getText().toString()+"");
            mTxtPhone.setVisibility(View.GONE);
            mEdtPhone.setVisibility(View.VISIBLE);
        }
    }
    public void setEditTextAddress(){
        if(isClickAddress == true){
            address = mEdtAddress.getText().toString()+"";
            mTxtAddress.setText(address);
            sendData(name,email,phone,address);
            mEdtAddress.setVisibility(View.GONE);
            mTxtAddress.setVisibility(View.VISIBLE);
            isClickAddress = false;
        }
        else{
            isClickAddress = true;
            mEdtAddress.setText(mTxtAddress.getText().toString()+"");
            mTxtAddress.setVisibility(View.GONE);
            mEdtAddress.setVisibility(View.VISIBLE);
        }
    }
    @Override
    public void getInforSuccess(Account account) {

    }
    @Override
    public void getInforFail(String message) {

    }

    @Override
    public void getAccountFromRoom(AccountItemEntities accountItemEntities) {
        mAccount = accountItemEntities.getAccount();
        mTxtName.setText(mAccount.getFirstName() + " " + mAccount.getLastName());
        mTxtEmail.setText(mAccount.getEmail());
        mTxtPhone.setText(mAccount.getPhoneNumber());
        mTxtAddress.setText(mAccount.getAddress());
    }

    private  void sendData(String name,String email,String phone,String address){
       mFragmentSentData.sendData(name,email,phone,address);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mFragmentSentData = (FirstFragmentListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " You must implement FirstFragmentListener");
        }
    }
    public interface FirstFragmentListener {
        void sendData(String name,String email,String phone,String address);
    }
}

