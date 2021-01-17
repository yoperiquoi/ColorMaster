package fenetre.dessinateur;

import fenetre.commande.DessinerCarre;
import fenetre.commande.ICommande;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import metier.formes.Carre;
import metier.formes.Forme;
/**
 * Définition d'un dessinateur permettant de définir le carré à dessiner
 */
public class DessinateurCarre extends Dessinateur{
    /**
     * Carré qui va être défini
     */
    private Carre carre = new Carre();

    /**
     * Méthode utilisant la commande pour dessiner le carre
     * @param gc contexte graphique du canvas de l'application sur laquelle on dessine
     */
    @Override
    public void dessiner(GraphicsContext gc) {
        //Pour dessiner nous utilisons la commande initialisé avec le carré déjà défini
        commande=new DessinerCarre(carre);
        commande.execute(gc);
    }


    /**
     * Méthode permettant la définition du carré lorsque le bouton de la souris est pressé sur le canvas
     * @param event événement déclenché par le clic de la souris
     * @param gc contexte graphique du canvas de l'application sur laquelle on dessine
     * @param couleur couleur sélectionnée dans le sélecteur de couleur du trait de la figure
     * @param couleurRemplissage couleur sélectionnée dans le sélecteur de de couleur du remplissage de la figure
     */
    @Override
    public void definirFormeOnMousePressed(MouseEvent event, GraphicsContext gc, Color couleur, Color couleurRemplissage) {
        //Pour ne pas se retrouver toujours avec la même référence du carré on fait attention à bien instancié une nouvelle
        //fois le carré avec rien de défini à l'intérieur
        carre=new Carre();
        //On défini ensuite la couleur du trait, du remplissage, sa largeur, et enfin on récupére les coordonnées
        //du point de départ de la figure
        gc.setStroke(couleur);
        carre.setLargeurTrait((float)gc.getLineWidth());
        carre.setCouleur(couleur);
        carre.setCouleurRemplissage(couleurRemplissage);
        if (carre.getCouleurRemplissage() != Color.TRANSPARENT) {
            carre.setRempli(true);
        }
        carre.setX((float) event.getX());
        carre.setY((float) event.getY());
    }

    /**
     *  Méthode permettant la définition du carré lorsque le bouton de la souris est relâché
     * @param event événement déclenché par le relâchement du clic de la souris
     */
    @Override
    public void definirFormeOnMouseReleased(MouseEvent event) {
        //Formule pas très précise pour la définition du coté car plutôt limité
        carre.setCote((float)Math.abs(event.getX()-carre.getX()));
        if (event.getX()<carre.getX()){
            carre.setX((float)event.getX());
        }
        if(event.getY()<carre.getY()){
            carre.setY((float)event.getY());
        }
    }

    /**
     * Méthode permettant de récupérer le carre défini
     * @return forme défini dans le dessinateur
     */
    @Override
    public Forme getForme() {
        return carre;
    }
}
