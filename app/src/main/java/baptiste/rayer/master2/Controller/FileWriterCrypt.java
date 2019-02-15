package baptiste.rayer.master2.Controller;

import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.spec.SecretKeySpec;

import baptiste.rayer.master2.model.Film;
/**
 * Created by Batra on 08/02/2019.
 */

public class FileWriterCrypt {
    private SecretKeySpec keyspec;
    private Cipher cipher;
    private String secretKey = "0123456789abcdef";

    public FileWriterCrypt() {
        try {
            keyspec = new SecretKeySpec(secretKey.getBytes(), "AES");
            cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void ecriture(ArrayList<Film> liste){
        ObjectOutputStream outputStream = null;
        try {
            for(Film f: liste) {
                f.setImage(null);
            }
            File path = Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_MOVIES);
            File file = new File(path, "/" + "films");

            try {
                cipher.init(Cipher.ENCRYPT_MODE, keyspec);
                FileOutputStream fichier = new FileOutputStream(file);

                CipherOutputStream cos = new CipherOutputStream(fichier, cipher);
                outputStream = new ObjectOutputStream(cos);
                outputStream.writeObject(liste);
                outputStream.close();
            } catch (Exception e) {
                throw new Exception("[encrypt] " + e.getMessage());
            }
            outputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (outputStream!= null) {
                    outputStream.flush();
                    outputStream.close();
                }
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public ArrayList<Film> lecture(){
        ObjectInputStream oos = null;
        try {
            cipher.init(Cipher.DECRYPT_MODE, keyspec);
            File path = Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_MOVIES);
            File file = new File(path, "/" + "films");
            FileInputStream fichier = new FileInputStream(file);

            CipherInputStream cis = new CipherInputStream(fichier, cipher);
            oos = new ObjectInputStream(cis);

            try {
                return (ArrayList<Film>) oos.readObject();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
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

