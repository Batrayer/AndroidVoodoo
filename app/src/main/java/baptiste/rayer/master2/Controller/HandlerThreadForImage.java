package baptiste.rayer.master2.Controller;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

/**
 * Created by Batra on 01/02/2019.
 */

public class HandlerThreadForImage extends HandlerThread{
    Handler handler;
    public HandlerThreadForImage(String name){
        super(name);
    }


    @Override
    public void onLooperPrepared() {
        Log.d("HandlerThreadForImage", "handle message");

        handler = new Handler(getLooper()) {
            @Override
            public void handleMessage(Message msg) {
            }
        };
    }

    public Handler getHandler() {
        return handler;
    }
}
