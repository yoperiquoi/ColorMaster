package metier.persistance;

import javafx.collections.ObservableList;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class ChargementRecent {
    public void charger(ObservableList<Recent> lesFichiersObs){
        File file = new File(System.getProperty("user.dir").concat("/recent"));
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
