package baptiste.rayer.master2.Controller;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import baptiste.rayer.master2.model.Film;


public class FileWriter {
    public void ecriture(ArrayList<Film> liste){
        ObjectOutputStream oos = null;
        try {
            File path = Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_MOVIES);
            File file = new File(path, "/" + "films");
            FileOutputStream fichier = new FileOutputStream(file);
            for(Film f: liste) {
                f.setImage(null);
            }
            oos = new ObjectOutputStream(fichier);
            oos.writeObject(liste);
            oos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (oos != null) {
                    oos.flush();
                    oos.close();
                }
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public ArrayList<Film> lecture(){
        ObjectInputStream oos = null;
        try {
            File path = Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_MOVIES);
            File file = new File(path, "/" + "films");
            FileInputStream fichier = new FileInputStream(file);
            oos = new ObjectInputStream(fichier);
            try {
                Log.d("listenerimport", "What's up");

                return (ArrayList<Film>) oos.readObject();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (oos != null) {
                    oos.close();
                }
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
