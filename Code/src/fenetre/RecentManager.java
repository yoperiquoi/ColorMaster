package fenetre;

import javafx.beans.Observable;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import metier.Recent;

import java.io.File;

public class RecentManager {
    private ObservableList<Recent> lesFichiersObs = FXCollections.observableArrayList();
    private ListProperty<Recent> lesFichiers = new SimpleListProperty<>();
        public ObservableList<Recent> getLesFichiers(){return lesFichiers.get();}
        public ListProperty<Recent> lesFichiersProperty(){return lesFichiers;}
        public void setLesFichiers(ObservableList<Recent> lesFichiers){this.lesFichiers.set(lesFichiers);}



}
