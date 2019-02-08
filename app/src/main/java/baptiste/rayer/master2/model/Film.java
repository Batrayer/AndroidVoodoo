package baptiste.rayer.master2.model;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * Created by Batra on 25/01/2019.
 */

public class Film implements Serializable {
    private String nom;
    private int idImage = -1;
    private Bitmap image = null;

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Bitmap getImage() {
        return image;
    }

    public String getNom() {
        return nom;
    }

    public int getIdImage() {
        return idImage;
    }

    public void setIdImage(int idImage) {
        this.idImage = idImage;
    }
}
