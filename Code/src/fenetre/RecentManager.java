package fenetre;

import javafx.beans.Observable;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import metier.Recent;

import java.io.*;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class RecentManager {
    private ObservableList<Recent> lesFichiersObs = FXCollections.observableArrayList();
    private ListProperty<Recent> lesFichiers = new SimpleListProperty<>();
        public ObservableList<Recent> getLesFichiers(){return lesFichiers.get();}
        public ListProperty<Recent> lesFichiersProperty(){return lesFichiers;}
        public void setLesFichiers(ObservableList<Recent> lesFichiers){this.lesFichiers.set(lesFichiers);}


    public RecentManager(){
            lesFichiersObs.add(new Recent("/home/Documents/test"));
            this.sauvegarder();
    }

    public void charger(){
            /*
            //Charger les fichiers récents
            File file = new File(System.getProperty("user.dir").concat("/recent"));
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
            List<Recent> tab =(Array) ois.readObject();
            for (i=0;i<tab.)
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        */

    }

    public void sauvegarder(){
            File file = new File(System.getProperty("user.dir").concat("/recent"));
            try {
                ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
                oos.writeObject(new ArrayList<Recent>(lesFichiersObs));
            } catch (IOException e) {
                e.printStackTrace();
            }
    }
}
