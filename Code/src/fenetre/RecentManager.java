package fenetre;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import metier.persistance.ChargementRecent;
import metier.persistance.Recent;
import metier.persistance.SauvegardeRecent;

/**
 * Classe assurant la gestion des fichier ouvert récemment
 */
public class RecentManager {
    private ObservableList<Recent> lesFichiersObs = FXCollections.observableArrayList();
    private ListProperty<Recent> lesFichiers = new SimpleListProperty<>(lesFichiersObs);
        public ObservableList<Recent> getLesFichiers(){return lesFichiers.get();}
        public ListProperty<Recent> lesFichiersProperty(){return lesFichiers;}
        public void setLesFichiers(ObservableList<Recent> lesFichiers){this.lesFichiers.set(lesFichiers);}


    public RecentManager(){
            this.charger();
    }

    /**
     * Assure le chargement des fichiers récent dans la liste observable depuis le fichier recent
     */
    public void charger(){
        //Charger les fichiers récents
        ChargementRecent chargementRecent = new ChargementRecent();
        chargementRecent.charger(lesFichiersObs);
    }

    /**
     * Assure la sauvegarder dans le fichier recent
     */
    public void sauvegarder(){
        SauvegardeRecent sauvegardeRecent = new SauvegardeRecent();
        sauvegardeRecent.sauver(lesFichiersObs);
    }

    /**
     * Assure l'ajout d'un élément à la liste et sa sauvegarde
     * @param recent fichier récent à ajouter
     */
    public void add(Recent recent){
            lesFichiersObs.add(recent);
            sauvegarder();
    }

    /**
     * Assure la suppression d'un élément à la liste et sa sauvegarde
     * @param recent fichier récent à supprimer
     */
    public void del(Recent recent){
            lesFichiersObs.remove(recent);
            sauvegarder();
    }
}
