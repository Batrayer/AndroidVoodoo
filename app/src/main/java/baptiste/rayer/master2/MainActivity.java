package baptiste.rayer.master2;

import android.os.AsyncTask;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import baptiste.rayer.master2.Controller.ArrayAdapterListe;
import baptiste.rayer.master2.Controller.AsynchTaskImage;
import baptiste.rayer.master2.Controller.HandlerThreadForImage;
import baptiste.rayer.master2.Controller.ThreadForFilm;
import baptiste.rayer.master2.model.Film;

public class MainActivity extends AppCompatActivity {
    public ArrayAdapterListe adapter;
    List<Film> lst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView lv = (ListView) findViewById(R.id.lst_view);
        lst = new ArrayList<>();
        Button updateImage = (Button) findViewById(R.id.random_film);

        Film f = new Film();
        adapter = new ArrayAdapterListe( this, lst);
        f.setNom("Die Hard");
        lst.add(f);
        f = new Film();

        f.setNom("Die Hard 2");
        lst.add(f);

        f = new Film();
        f.setNom("Die Hard with a Vengeance");
        lst.add(f);

        f = new Film();
        f.setNom("Live free or Die Hard");
        lst.add(f);

        f = new Film();
        f.setNom("A good day to Die Hard");
        lst.add(f);


        f = new Film();
        f.setNom("Die Hardest");
        lst.add(f);

        lv.setAdapter(adapter);

        View.OnClickListener listenerUpdateFilm = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(Film film: lst) {
                    AsynchTaskImage atf = new AsynchTaskImage(MainActivity.this);
                    //
                    atf.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, film);

                }
            }
        };
        updateImage.setOnClickListener(listenerUpdateFilm);


        HandlerThreadForImage htfi = new HandlerThreadForImage("handler");
        htfi.start();
        for(Film film: lst) {
            htfi.onLooperPrepared();
            htfi.getHandler().post(new ThreadForFilm(film, MainActivity.this));
        }
    }
}
