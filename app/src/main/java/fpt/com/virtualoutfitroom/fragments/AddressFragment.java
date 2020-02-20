package fpt.com.virtualoutfitroom.fragments;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import fpt.com.virtualoutfitroom.R;
import fpt.com.virtualoutfitroom.activities.PaymentActivity;
import fpt.com.virtualoutfitroom.model.Account;
import fpt.com.virtualoutfitroom.model.OrderHistory;
import fpt.com.virtualoutfitroom.presenter.accounts.InformationAccountPresenter;
import fpt.com.virtualoutfitroom.room.AccountItemEntities;
import fpt.com.virtualoutfitroom.utils.RegexHelper;
import fpt.com.virtualoutfitroom.views.GetInforAccountView;

public class AddressFragment extends Fragment implements GetInforAccountView {
    private View mView;
    private EditText mEdtName, mEdtPhone, mEdtEmail, mEdtAddress, mEdtDescription;
    private AccountItemEntities mAccount;
    private String name, email, phone, address;
    private InformationAccountPresenter informationAccountPresenter;
    private FirstFragmentListener mFragmentSentData;
    private OrderHistory order;

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

    public static Fragment newInstance() {
        AddressFragment fragment = new AddressFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public void initialView() {
        mEdtName = mView.findViewById(R.id.edt_name_payment);
        mEdtName.addTextChangedListener(new CustomTextWatcher(mEdtName, "name"));
        mEdtName.setOnTouchListener(new CustomTextWatcher(mEdtName));
        mEdtEmail = mView.findViewById(R.id.edt_email_payment);
        mEdtEmail.addTextChangedListener(new CustomTextWatcher(mEdtEmail,"email"));
        mEdtEmail.setOnTouchListener(new CustomTextWatcher(mEdtEmail));
        mEdtPhone = mView.findViewById(R.id.edt_phone_payment);
        mEdtPhone.addTextChangedListener(new CustomTextWatcher(mEdtPhone,"phone"));
        mEdtPhone.setOnTouchListener(new CustomTextWatcher(mEdtPhone));
        mEdtAddress = mView.findViewById(R.id.edt_address_payment);
        mEdtAddress.addTextChangedListener(new CustomTextWatcher(mEdtAddress,"address"));
        mEdtAddress.setOnTouchListener(new CustomTextWatcher(mEdtAddress));
        mEdtDescription = mView.findViewById(R.id.edt_description_payment);
        mEdtDescription.setOnTouchListener(new CustomTextWatcher(mEdtDescription));
        order = new OrderHistory();
    }

    public void initialData() {
        informationAccountPresenter = new InformationAccountPresenter(getActivity().getApplication(), this);
        informationAccountPresenter.getAccountFromRoom();

    }

    @Override
    public void getInforSuccess(Account account) {

    }

    @Override
    public void getInforFail(String message) {

    }

    @Override
    public void getAccountFromRoom(AccountItemEntities accountItemEntities) {
        mAccount = accountItemEntities;
        name = mAccount.getAccount().getFirstName() + " " + mAccount.getAccount().getLastName();
        phone = mAccount.getAccount().getPhoneNumber();
        email = mAccount.getAccount().getEmail();
        address = mAccount.getAccount().getAddress();
        mEdtName.setText(name);
        mEdtEmail.setText(email);
        mEdtPhone.setText(phone);
        mEdtAddress.setText(address);
    }

    public void getData() {
        order.setFull_name(mEdtName.getText().toString());
        order.setEmail(mEdtEmail.getText().toString());
        order.setPhone_number(mEdtPhone.getText().toString());
        order.setAddress(mEdtAddress.getText().toString());
        order.setDescription(mEdtDescription.getText().toString().trim());
        mFragmentSentData.sendData(order);
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
        void sendData(OrderHistory order);
    }

    private class CustomTextWatcher implements TextWatcher,View.OnTouchListener {
        private EditText mEditText;
        private String mName;

        public CustomTextWatcher(EditText e, String mName) {
            this.mEditText = e;
            this.mName = mName;
        }

        public CustomTextWatcher(EditText e) {
            this.mEditText = e;
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        public void afterTextChanged(Editable s) {
            String param = mEditText.getText().toString().trim();
            boolean check = true;
            mEditText.setBackgroundResource(R.drawable.edit_text_cus_background);
            if (mName == "name") {
                if (!RegexHelper.checkSpecChar(param)) {
                    mEditText.setError("Tên không chứa các kí tự và số");
                    mEditText.setBackgroundResource(R.drawable.edit_text_cus_border);
                    check = false;
                }
            }
            if (mName == "email") {
                if (!RegexHelper.checkEmail(param)) {
                    mEditText.setError("Email không đúng định dạng");
                    check = false;
                    mEditText.setBackgroundResource(R.drawable.edit_text_cus_border);
                }
            }
            if (mName == "phone") {
                if (!RegexHelper.checkPhoneNumber(param)) {
                    mEditText.setError("Số điện thoại không hợp lệ");
                    check = false;
                    mEditText.setBackgroundResource(R.drawable.edit_text_cus_border);
                }
            }
            if (mEditText.getText().toString().length() <= 0) {
                mEditText.setError("Trường này không được để trống");
                check = false;
                mEditText.setBackgroundResource(R.drawable.edit_text_cus_border);
            }
            ((PaymentActivity) getActivity()).changeStateBtnNext(check);
        }

        public void setDefaultValue() {
            if (!mEdtName.getBackground().getConstantState().equals(getActivity().getResources().getDrawable(R.drawable.edit_text_cus_border).getConstantState())) {
                mEdtName.setBackgroundResource(R.drawable.edit_text_cus_background);
            }
            if (!mEdtAddress.getBackground().getConstantState().equals(getActivity().getResources().getDrawable(R.drawable.edit_text_cus_border).getConstantState())) {
                mEdtAddress.setBackgroundResource(R.drawable.edit_text_cus_background);
            }
            if (!mEdtEmail.getBackground().getConstantState().equals(getActivity().getResources().getDrawable(R.drawable.edit_text_cus_border).getConstantState())) {
                mEdtEmail.setBackgroundResource(R.drawable.edit_text_cus_background);
            }
            if (!mEdtPhone.getBackground().getConstantState().equals(getActivity().getResources().getDrawable(R.drawable.edit_text_cus_border).getConstantState())) {
                mEdtPhone.setBackgroundResource(R.drawable.edit_text_cus_background);
            }
            if (!mEdtDescription.getBackground().getConstantState().equals(getActivity().getResources().getDrawable(R.drawable.edit_text_cus_border).getConstantState())) {
                mEdtDescription.setBackgroundResource(R.drawable.edit_text_cus_background);
            }
        }

        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            setDefaultValue();
            if (!mEditText.getBackground().getConstantState().equals(getActivity().getResources().getDrawable(R.drawable.edit_text_cus_border).getConstantState())) {
                //compare drawable
                mEditText.setBackgroundResource(R.drawable.edit_text_cus_blu_border);
            }
            return false;
        }
    }
}

