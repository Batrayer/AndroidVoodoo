package baptiste.rayer.master2.Controller;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import baptiste.rayer.master2.MainActivity;
import baptiste.rayer.master2.model.Film;

/**
 * Created by Batra on 02/01/2018.
 */

public class AsynchTaskImage extends AsyncTask<Film, Object, Object> {
    MainActivity context;
    public AsynchTaskImage(MainActivity context){
        this.context = context;
    }

    @Override
    protected Object doInBackground(Film... objects) {
        Log.d("doinbackground", "hello");
        URL url = null;
        Bitmap bitmap;

        try {
            //SystemClock.sleep(7000);
            url = new URL("https://picsum.photos/200/200/?random");
            URLConnection conn = url.openConnection();
            bitmap = BitmapFactory.decodeStream(conn.getInputStream());
            objects[0].setImage(bitmap);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    protected void onPostExecute(Object o) {
        context.adapter.notifyDataSetChanged();
    }
}
