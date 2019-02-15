package baptiste.rayer.master2;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import baptiste.rayer.master2.Controller.ArrayAdapterListe;
import baptiste.rayer.master2.Controller.AsynchTaskImage;
import baptiste.rayer.master2.Controller.FileWriter;
import baptiste.rayer.master2.Controller.FileWriterCrypt;
import baptiste.rayer.master2.Controller.HandlerThreadForImage;
import baptiste.rayer.master2.Controller.ThreadForFilm;
import baptiste.rayer.master2.model.Film;

public class MainActivity extends AppCompatActivity {
    public ArrayAdapterListe adapter;
    List<Film> lst;
    ThreadPoolExecutor thpe;
    BlockingQueue<Runnable> bcq;
    public static final int MY_PERMISSIONS_REQUEST_STORAGE = 1;
    public static final int MY_PERMISSIONS_REQUEST_CAMERA = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView lv = (ListView) findViewById(R.id.lst_view);
        lst = new ArrayList<>();

        Film f = new Film();
        adapter = new ArrayAdapterListe( MainActivity.this, lst);
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
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_STORAGE
            );
        }else{
            addAllListener();
            if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA},
                        MY_PERMISSIONS_REQUEST_CAMERA);
            }
        }
    }

    public void addAllListener() {
        Button updateImagePool = findViewById(R.id.add_film);
        Button updateImage = (Button) findViewById(R.id.random_film);
        Button export = (Button) findViewById(R.id.export);
        Button imports = (Button) findViewById(R.id.imports);
        Button removeAll = (Button) findViewById(R.id.delete_list);
        View.OnClickListener listenerUpdateFilm = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (Film film : lst) {
                    AsynchTaskImage atf = new AsynchTaskImage(MainActivity.this);
                    //
                    atf.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, film);

                }
            }
        };
        updateImage.setOnClickListener(listenerUpdateFilm);


        HandlerThreadForImage htfi = new HandlerThreadForImage("handler");
        htfi.start();
        for (Film film : lst) {
            htfi.onLooperPrepared();
            htfi.getHandler().post(new ThreadForFilm(film, MainActivity.this));
        }

        bcq = new LinkedBlockingDeque<Runnable>();
        thpe = new ThreadPoolExecutor(5, 5, 10, TimeUnit.SECONDS, bcq);

        View.OnClickListener listenerUpdateFilmPool = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (Film film : lst) {
                    thpe.execute(new ThreadForFilm(film, MainActivity.this));

                }
            }
        };
        updateImagePool.setOnClickListener(listenerUpdateFilmPool);
        View.OnClickListener listenerForExport = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FileWriterCrypt fw = new FileWriterCrypt();
                fw.ecriture((ArrayList) lst);
            }
        };
        export.setOnClickListener(listenerForExport);

        View.OnClickListener listenerImport = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FileWriterCrypt fw = new FileWriterCrypt();
                List<Film> lst2 = fw.lecture();
                lst.addAll(lst2);
                adapter.notifyDataSetChanged();
            }
        };
        imports.setOnClickListener(listenerImport);

        View.OnClickListener listenerForDeleteAll = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lst.clear();
                adapter.notifyDataSetChanged();
            }
        };
        removeAll.setOnClickListener(listenerForDeleteAll);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    addAllListener();
                } else {
                    finish();
                }
                return;
            }
            case MY_PERMISSIONS_REQUEST_CAMERA: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d("CAMERA", "SUCCESS");
                } else {
                    Log.d("CAMERA", "FAILURE");

                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }
}
