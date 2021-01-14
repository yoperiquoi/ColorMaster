package metier.persistance;

import javafx.collections.ObservableList;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class SauvegardeRecent {
    public void sauver(ObservableList<Recent> lesFichiersObs){
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
