package fpt.com.virtualoutfitroom.utils;

import fpt.com.virtualoutfitroom.model.Account;

public interface FragmentSentData {
    void sendData(Account account);
    void sendData(String name,String email,String phone,String adđress);
}
