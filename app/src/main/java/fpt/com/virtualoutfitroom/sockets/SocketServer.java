package fpt.com.virtualoutfitroom.sockets;

import android.util.Log;

import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONObject;

import java.net.URISyntaxException;

import fpt.com.virtualoutfitroom.webservice.ConfigApi;

public class SocketServer {

    public Socket mSocket;

    public SocketServer() {
        try {
            mSocket = IO.socket(ConfigApi.SOCKET_URL);
        } catch (URISyntaxException e) {
        }
    }

    public void connect() {
        mSocket.connect();
        Log.i("connect", "connected");
    }

    public void emitOrder(JSONObject roomInfo) {
        mSocket.emit("add-new-order", roomInfo);
    }

}
