package fenetre.dessinateur;

import fenetre.commande.ICommande;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import metier.formes.Forme;

/**
 * Définition d'un dessinateur pour chaque forme implémenter permettant de définir la forme
 * qui va être dessiné
 */
public class Dessinateur implements IDessine{
    /**
     * Forme à définir dans le dessinateur
     */
    private Forme forme;
    /**
     * Commande permettant de dessiner la forme
     */
    public ICommande commande;

    /**
     * Méthode utilisant la commande pour dessiner la figure
     * @param gc contexte graphique du canvas de l'application sur laquelle on dessine
     */
    @Override
    public void dessiner(GraphicsContext gc) {
    }

    /**
     * Méthode permettant la définition de la forme lorsque le bouton de la souris est pressé sur le canvas
     * @param event événement déclenché par le clic de la souris
     * @param gc contexte graphique du canvas de l'application sur laquelle on dessine
     * @param couleur couleur sélectionnée dans le sélecteur de couleur du trait de la figure
     * @param couleurRemplissage couleur sélectionnée dans le sélecteur de de couleur du remplissage de la figure
     */
    @Override
    public void definirFormeOnMousePressed(MouseEvent event, GraphicsContext gc, Color couleur, Color couleurRemplissage) {
    }

    /**
     *  Méthode permettant la définition de la forme lorsque le bouton de la souris est relâché
     * @param event événement déclenché par le relâchement du clic de la souris
     */
    @Override
    public void definirFormeOnMouseReleased(MouseEvent event) {
    }

    /**
     * Méthode permettant de récupérer la forme définie
     * @return forme défini dans le dessinateur
     */
    @Override
    public Forme getForme() {
        return forme;
    }

    /**
     * Méthode permettant de définir la forme à partir d'une forme déjà existante
     * @param forme forme déjà défini
     */
    public void setForme(Forme forme){ this.forme=forme; }

    /**
     * Méthode permettant de récupérer la commande correspondant au dessinateur
     * @return la commande correspondant au dessinateur
     */
    public ICommande getCommande() {
        return commande;
    }
}
