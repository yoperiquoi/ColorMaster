package metier.persistance;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.File;
import java.io.Serializable;

public class Recent implements Serializable {
    private final StringProperty fileName = new SimpleStringProperty();
    public StringProperty fileNameProperty(){return fileName;}
    public String getFileName(){return fileName.get();}
    public void setFileName(String valeur){ fileName.set(valeur);}

    private String nom;

    private Boolean enregistre;

    public Recent(String fileName,String nom, Boolean enregistre){
        setFileName(fileName);
        setNom(nom);
        setEnregistre(enregistre);
    }

    private void setEnregistre(Boolean enregistre) {
        this.enregistre=enregistre;
    }

    public String toString(){
        return getNom();
    }


    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public boolean getEnregistre() {
        return enregistre;
    }
}
