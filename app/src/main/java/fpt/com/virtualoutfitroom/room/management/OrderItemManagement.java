package fpt.com.virtualoutfitroom.room.management;

import android.app.Application;
import android.os.AsyncTask;

import java.util.List;

import fpt.com.virtualoutfitroom.room.OrderItemEntities;
import fpt.com.virtualoutfitroom.room.dao.OrderDAO;
import fpt.com.virtualoutfitroom.room.database.VOFRDatabase;

public class OrderItemManagement {
    private OrderDAO orderDAO;
    private Application application;

    public OrderItemManagement(Application application) {
        this.application = application;
        VOFRDatabase vofrDatabase = VOFRDatabase.getDatabase(application);
        orderDAO = vofrDatabase.orderDAO();
    }

    public interface DataCallBack{
      void onSuccess(List<OrderItemEntities> list);
       void  onFail(String message);
    }
    private class AddOrderItemAsync extends AsyncTask<OrderItemEntities,Void,Void>{
        private OrderDAO orderDAO;
        @Override
        protected Void doInBackground(OrderItemEntities... orderItemEntities) {
            orderDAO.insertOrderItem(orderItemEntities);
            return null;
        }

        public AddOrderItemAsync(OrderDAO orderDAO) {
            this.orderDAO = orderDAO;
        }
    }
    private class GetOrderItemAsync extends AsyncTask<List<OrderItemEntities>,Void,Void>{
        private OrderDAO orderDAO;
        private List<OrderItemEntities> listItemEntities;
        private DataCallBack dataCallBack;

        public GetOrderItemAsync(OrderDAO orderDAO, DataCallBack dataCallBack) {
            this.orderDAO = orderDAO;
            this.dataCallBack = dataCallBack;
        }

        @Override
        protected Void doInBackground(List<OrderItemEntities>... lists) {
           try {
               listItemEntities = orderDAO.getListOrderItem();

           }catch (Exception e){

           }
           return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(listItemEntities != null){
                dataCallBack.onSuccess(listItemEntities);
            }else{
                dataCallBack.onFail("Not Found Product");
            }
        }
    }
     public void addOrderItem(OrderItemEntities orderItemEntities){
        AddOrderItemAsync addOrderItemAsync = new AddOrderItemAsync(orderDAO);
        addOrderItemAsync.execute(orderItemEntities);


     }
     public void getOrderItem(DataCallBack dataCallBack){
        GetOrderItemAsync getOrderItemAsync = new GetOrderItemAsync(orderDAO, dataCallBack);
        getOrderItemAsync.execute();
     }
    public void deleteOrderItem(OrderItemEntities orderItem) {
        DeleteOrderItemAsyn deleteOrderItemAsyn = new DeleteOrderItemAsyn(orderDAO);
        deleteOrderItemAsyn.execute(orderItem);
    }
    private class DeleteOrderItemAsyn extends AsyncTask<OrderItemEntities, Void, Void> {
        private OrderDAO orderDAO;

        public DeleteOrderItemAsyn(OrderDAO orderDAO) {
            this.orderDAO = orderDAO;
        }

        @Override
        protected Void doInBackground(OrderItemEntities... orderItem) {
            orderDAO.deleteOrderItem(orderItem);
            return null;
        }
    }

}
