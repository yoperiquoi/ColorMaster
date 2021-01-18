package metier.persistance;

import javafx.collections.ObservableList;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * Classe définissant les méthode de sauvegarde des fichiers récents
 */
public class SauvegardeRecent {
    /**
     * Méthode permettant de sauvegarder des fichier récemment ouvert
     * @param lesFichiersObs liste des fichiers récemment ouvert
     */
    public static void sauver(ObservableList<Recent> lesFichiersObs){
        File file = new File(System.getProperty("user.dir").concat("/recent"));
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
            oos.writeInt(lesFichiersObs.size());
            for (Recent recent: lesFichiersObs) {
                oos.writeObject(recent.getFileName());
                oos.writeObject(recent.getNom());
                oos.writeObject(recent.getEnregistre());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
