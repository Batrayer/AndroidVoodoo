package baptiste.rayer.master2.Controller;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.net.URL;
import java.net.URLConnection;

import baptiste.rayer.master2.MainActivity;
import baptiste.rayer.master2.model.Film;

/**
 * Created by Batra on 02/01/2018.
 */

public class ThreadForFilm extends Thread {
    private Film row;
    private MainActivity mainActivity;

    public ThreadForFilm(Film row, MainActivity mainActivity){
        this.row = row;
        this.mainActivity = mainActivity;
    }

    public void run(){
        URL url = null;
        Bitmap bitmap;
        try {
            url = new URL("https://picsum.photos/200/200/?random");
            URLConnection conn = url.openConnection();
            bitmap = BitmapFactory.decodeStream(conn.getInputStream());
            row.setImage(bitmap);
        }catch(Exception e){
            e.printStackTrace();
        }

        mainActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mainActivity.adapter.notifyDataSetChanged();
            }
        });
    }


}
