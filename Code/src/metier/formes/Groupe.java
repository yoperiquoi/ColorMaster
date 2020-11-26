package metier.formes;

import java.util.LinkedList;
import java.util.List;

public class Groupe extends Forme{
    private List<Forme> groupe = new LinkedList<>();

    public Groupe(List<Forme> groupe){
        setGroupe(groupe);
    }

    public List<Forme> getGroupe() {
        return groupe;
    }

    public void setGroupe(List<Forme> groupe) {
        this.groupe = groupe;
    }
    public void ajouterForme(Forme f){
        groupe.add(f);
    }
    public void supprimerForme(Forme f){
        groupe.remove(f);
    }


}
