package baptiste.rayer.master2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import baptiste.rayer.master2.Controller.ArrayAdapterListe;
import baptiste.rayer.master2.Controller.AsynchTaskImage;
import baptiste.rayer.master2.model.Film;

public class MainActivity extends AppCompatActivity {
    ArrayAdapterListe adapter;
    List<Film> lst;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView lv = (ListView) findViewById(R.id.lst_view);
        AsynchTaskImage async = new AsynchTaskImage();
        lst = new ArrayList<>();
        Button updateImage = (Button) findViewById(R.id.random_film);

        Film f = new Film();
        f.setNom("Test");
        lst.add(f);
        adapter = new ArrayAdapterListe( this, lst);
        lv.setAdapter(adapter);
        View.OnClickListener listenerUpdateFilm = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ExecutorService service =  Executors.newFixedThreadPool(5);
                for(Film film: lst) {
                    AsynchTaskImage atf = new AsynchTaskImage();
                    atf.executeOnExecutor(service, film);
                    adapter.notifyDataSetChanged();

                }
            }
        };
        updateImage.setOnClickListener(listenerUpdateFilm);

    }
}
