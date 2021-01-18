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
    /**
     * ObservableList permettant d'afficher la liste des fichiers récent dans la vue
     */
    private final ObservableList<Recent> lesFichiersObs = FXCollections.observableArrayList();

    /**
     * Liste contenant les fichiers récents
     */
    private final ListProperty<Recent> lesFichiers = new SimpleListProperty<>(lesFichiersObs);

    /**
     * Méthode permettant de récupérer une listes des fichiers
     * @return la liste observables des fichiers
     */
    public ObservableList<Recent> getLesFichiers(){return lesFichiers.get();}

    /**
     * Méthode permettant de récupérer la liste property de la liste observable
     * @return la liste property de la liste observable
     */
    public ListProperty<Recent> lesFichiersProperty(){return lesFichiers;}

    /**
     * Méthode permettant de définir les fichiers
     * @param lesFichiers liste observable des fichiers à définir
     */
    public void setLesFichiers(ObservableList<Recent> lesFichiers){this.lesFichiers.set(lesFichiers);}


    /**
     * Constructeur du managfer de fichier récent
     */
    public RecentManager(){
            this.charger();
    }

    /**
     * Assure le chargement des fichiers récent dans la liste observable depuis le fichier recent
     */
    public void charger(){
        //Charger les fichiers récents
        ChargementRecent.charger(lesFichiersObs);
    }

    /**
     * Assure la sauvegarder dans le fichier recent
     */
    public void sauvegarder(){
        SauvegardeRecent.sauver(lesFichiersObs);
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
