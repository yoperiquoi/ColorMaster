package metier.persistance;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.File;
import java.io.Serializable;

/**
 * Classe permettant de définir un fichier récent
 */
public class Recent implements Serializable {
    private final StringProperty fileName = new SimpleStringProperty();
    public StringProperty fileNameProperty(){return fileName;}
    public String getFileName(){return fileName.get();}
    public void setFileName(String valeur){ fileName.set(valeur);}

    private String nom;

    private Boolean enregistre;

    /**
     * Constructeur
     * @param fileName Chemin du fichier
     * @param nom nom du fichier
     * @param enregistre si le fichier est enregistré ou non
     */
    public Recent(String fileName,String nom, Boolean enregistre){
        setFileName(fileName);
        setNom(nom);
        setEnregistre(enregistre);
    }

    /**
     * Méthode permettant de définir enregistre
     * @param enregistre si le fichier est déjà enregistré
     */
    private void setEnregistre(Boolean enregistre) {
        this.enregistre=enregistre;
    }

    /**
     * Méthode permettant de récupérer le nom du fichier
     * @return nom du fichier
     */
    public String getNom() {
        return nom;
    }

    /**
     * Méthode permettant de définir le nom du fichier
     * @param nom nom du fichier
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Méthode permettant de savoir si le fichier est enregistré
     * @return si le fichier est enregistré ou non
     */
    public boolean getEnregistre() {
        return enregistre;
    }
}
