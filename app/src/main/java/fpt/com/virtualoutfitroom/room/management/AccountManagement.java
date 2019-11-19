package fpt.com.virtualoutfitroom.room.management;

import android.app.Application;
import android.database.sqlite.SQLiteConstraintException;
import android.os.AsyncTask;

import fpt.com.virtualoutfitroom.room.AccountItemEntities;
import fpt.com.virtualoutfitroom.room.dao.AccountDAO;
import fpt.com.virtualoutfitroom.room.database.VOFRDatabase;

public class AccountManagement {
    private AccountDAO accountDAO;
    private Application application;

    public AccountManagement(Application application) {
        this.application = application;
        VOFRDatabase vofrDatabase = VOFRDatabase.getDatabase(application);
        accountDAO = vofrDatabase.accountDao();
    }
    public interface DataCallBack{
        void onSuccess(AccountItemEntities account);
        void  onFail(String message);
    }
    private class AddOrderItemAsync extends AsyncTask<AccountItemEntities,Void,Void> {
        private AccountDAO accountDAO;
        @Override
        protected Void doInBackground(AccountItemEntities... accountItemEntities) {
            accountDAO.insertAccount(accountItemEntities);
            return null;
        }

        public AddOrderItemAsync(AccountDAO accountDAO) {
            this.accountDAO = accountDAO;
        }
    }
    private class GetAccountItemAsync extends AsyncTask<AccountItemEntities,Void,Void>{
        private AccountDAO accountDAO;
        private AccountItemEntities accountE;
        private DataCallBack dataCallBack;

        public GetAccountItemAsync(AccountDAO accountDAO, DataCallBack dataCallBack) {
            this.accountDAO = accountDAO;
            this.dataCallBack = dataCallBack;
        }

        @Override
        protected Void doInBackground(AccountItemEntities... accountItemEntities) {
            try {
                accountE = accountDAO.getAccount();

            }catch (Exception e){

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(accountE != null){
                dataCallBack.onSuccess(accountE);
            }else{
                dataCallBack.onFail("Not Found Product");
            }
        }
    }
    private class DeleteAllAccountAsync extends AsyncTask<AccountItemEntities, Void, Void> {
        private AccountDAO mDaoAsync;

        public DeleteAllAccountAsync(AccountDAO mDaoAsync) {
            this.mDaoAsync = mDaoAsync;
        }

        @Override
        protected Void doInBackground(AccountItemEntities... accountItemEntities) {
            try {
                mDaoAsync.deleleAllAccount();
            } catch (SQLiteConstraintException e) {

            }
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

        }
    }

    private class UpdateCustomerAsyn extends AsyncTask<AccountItemEntities, Void, Void> {
        private AccountDAO mAccountDaoAsync;
        public UpdateCustomerAsyn(AccountDAO mAccountDao) {
            this.mAccountDaoAsync = mAccountDao;
        }


        @Override
        protected Void doInBackground(AccountItemEntities... accountItemEntities) {
            try {
               mAccountDaoAsync.updateAccount(accountItemEntities);
            } catch (SQLiteConstraintException e) {
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }

    public void updateAccount(AccountItemEntities accountItemEntities) {
        UpdateCustomerAsyn updateCustomerAsyn = new UpdateCustomerAsyn(accountDAO);
        updateCustomerAsyn.execute(accountItemEntities);
    }

    public void deleteAllAccount() {
        DeleteAllAccountAsync deleteAsync = new DeleteAllAccountAsync(accountDAO);
        deleteAsync.execute();
    }
    public void addAccountItem(AccountItemEntities accountItemEntities){
        AddOrderItemAsync addOrderItemAsync = new AddOrderItemAsync(accountDAO);
        addOrderItemAsync.execute(accountItemEntities);
    }
    public void getAccountItem(DataCallBack dataCallBack){
        GetAccountItemAsync getAccountItemAsync = new GetAccountItemAsync(accountDAO, dataCallBack);
        getAccountItemAsync.execute();
    }
}
