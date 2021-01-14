package fenetre;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import metier.persistance.ChargementRecent;
import metier.persistance.Recent;
import metier.persistance.SauvegardeRecent;

public class RecentManager {
    private ObservableList<Recent> lesFichiersObs = FXCollections.observableArrayList();
    private ListProperty<Recent> lesFichiers = new SimpleListProperty<>(lesFichiersObs);
        public ObservableList<Recent> getLesFichiers(){return lesFichiers.get();}
        public ListProperty<Recent> lesFichiersProperty(){return lesFichiers;}
        public void setLesFichiers(ObservableList<Recent> lesFichiers){this.lesFichiers.set(lesFichiers);}


    public RecentManager(){
            this.charger();
    }

    public void charger(){
        //Charger les fichiers récents
        ChargementRecent chargementRecent = new ChargementRecent();
        chargementRecent.charger(lesFichiersObs);
    }

    public void sauvegarder(){
        SauvegardeRecent sauvegardeRecent = new SauvegardeRecent();
        sauvegardeRecent.sauver(lesFichiersObs);
    }

    public void add(Recent recent){
            lesFichiersObs.add(recent);
            sauvegarder();
    }

    public void del(Recent recent){
            lesFichiersObs.remove(recent);
            sauvegarder();
    }
}
