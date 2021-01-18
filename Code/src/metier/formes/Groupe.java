package metier.formes;

import java.util.LinkedList;
import java.util.List;

/**
 * Classe définissant un groupe de forme
 */
public class Groupe extends Forme{
    /**
     * Liste de forme constituant un groupe de forme
     */
    private List<Forme> groupe = new LinkedList<>();

    /**
     * Constructeur permettant l'ajout direct d'un groupe
     * @param groupe groupe à créer
     */
    public Groupe(List<Forme> groupe){
        setGroupe(groupe);
    }

    /**
     * Méthode permettant de récupérer la liste
     * @return la liste de forme
     */
    public List<Forme> getGroupe() {
        return groupe;
    }

    /**
     * Méthode permettant de définir le groupe
     * @param groupe une liste forme
     */
    public void setGroupe(List<Forme> groupe) {
        this.groupe = groupe;
    }

    /**
     * Méthode permettant d'ajouter des formes
     * @param f forme
     */
    public void ajouterForme(Forme f){
        groupe.add(f);
    }

    /**
     * Méthode permettant supprimer des formes
     * @param f forme
     */
    public void supprimerForme(Forme f){
        groupe.remove(f);
    }

    public Forme getForme(int x){return groupe.get(x);}

}
