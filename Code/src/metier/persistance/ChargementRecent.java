package metier.persistance;

import javafx.collections.ObservableList;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

/**
 * Classe responsable du chargement des fichiers récemment ouvert
 */
public class ChargementRecent {
    /**
     * Méthode permettant de charger les fichiers ouverts récemment
     * @param lesFichiersObs Liste des fichiers
     */
    public static void charger(ObservableList<Recent> lesFichiersObs){
        File file = new File(System.getProperty("user.dir").concat("/recent"));
        //Si l'utilisateur à supprimer touts les fichiers récent il ne reste plus que 4 caractères dans le fichier
        //Si c'est le cas il n'y a pas besoin de le lire
        if ((file.length()>4)) {
            try {
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
                ArrayList<Recent> lesFichiers = new ArrayList<>();
                int size = ois.readInt();
                for (int i = 0; i < size; i++) {
                    lesFichiers.add(new Recent((String) ois.readObject(), (String) ois.readObject(),(Boolean) ois.readObject()));
                }
                lesFichiersObs.addAll(lesFichiers);
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
