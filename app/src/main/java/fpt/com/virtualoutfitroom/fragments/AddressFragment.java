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

import java.util.ArrayList;
import java.util.List;

import fpt.com.virtualoutfitroom.R;
import fpt.com.virtualoutfitroom.adapter.AddressPaymentAdapter;
import fpt.com.virtualoutfitroom.model.Account;
import fpt.com.virtualoutfitroom.presenter.accounts.InformationAccountPresenter;
import fpt.com.virtualoutfitroom.views.GetInforAccountView;
public class AddressFragment extends Fragment implements View.OnClickListener, GetInforAccountView {
    private View mView;
    private EditText mEdtName,mEdtPhone,mEdtEmail,mEdtAddress;
    private LinearLayout mlnlName,mLnlEmail,mLnlPhone,mLnlAddress;
    private boolean isClickName = false,isClickEmail = false,isClickPhone = false,isClickAddress = false;
    private InformationAccountPresenter informationAccountPresenter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_address, container, false);
        initialView();
        initialData();
        return mView;
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
    }
    public  void initialData(){
        mlnlName.setOnClickListener(this);
        mLnlEmail.setOnClickListener(this);
        mLnlPhone.setOnClickListener(this);
        mLnlAddress.setOnClickListener(this);
        informationAccountPresenter = new InformationAccountPresenter(getActivity().getApplication(),this);
        informationAccountPresenter.getAccountFromRoom();
    }
    @Override
    public void onClick(View view) {
            int id = view.getId();
            switch (id){
              case   R.id.lnl_button_name:
                  if(isClickName  == false){
                      setTouchModeEditText(mEdtName,true);
                      isClickName = true;
                  }else {
                      setTouchModeEditText(mEdtName,false);
                      isClickName = false;
                  }
                  break;
                case R.id.lnl_button_email:
                    if(isClickEmail == false){
                        setTouchModeEditText(mEdtEmail,true);
                        isClickEmail = true;
                    }else {
                        setTouchModeEditText(mEdtEmail,false);
                        isClickEmail = false;
                    }
                    break;
                case R.id.lnl_button_phone:
                    if(isClickPhone  == false){
                        setTouchModeEditText(mEdtPhone,true);
                        isClickPhone = true;
                    }else {
                        setTouchModeEditText(mEdtPhone,false);
                        isClickPhone = false;
                    }
                    break;
                case R.id.lnl_button_address:
                    if(isClickAddress  == false){
                        setTouchModeEditText(mEdtAddress,true);
                        isClickAddress = true;
                    }else {
                        setTouchModeEditText(mEdtAddress,false);
                        isClickAddress = false;
                    }
                    break;
            }
    }
    public  void setTouchModeEditText(EditText mEdtext, boolean ischeck){
        if(ischeck == true){
            mEdtext.setEnabled(true);
            mEdtext.setFocusableInTouchMode(true);
            mEdtext.setCursorVisible(true);
        }
        else {
            mEdtext.setEnabled(false);
            mEdtext.setFocusableInTouchMode(false);
            mEdtext.setCursorVisible(false);
        }


    }
    @Override
    public void getInforSuccess(Account account) {
        mEdtName.setText(account.getFirstName() + " " + account.getLastName());
        mEdtEmail.setText(account.getEmail());
        mEdtPhone.setText(account.getPhoneNumber());
        mEdtAddress.setText(account.getAddress());
    }
    @Override
    public void getInforFail(String message) {

    }
}
