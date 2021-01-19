package fenetre.dessinateur;

import fenetre.commande.DessinerCercle;
import fenetre.commande.ICommande;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import metier.formes.Cercle;
/**
 * Définition d'un dessinateur permettant de définir le cercle à dessiner
 */
public class DessinateurCercle extends Dessinateur {
    /**
     * Cercle qui va être défini
     */
    private Cercle cercle= new Cercle();

    /**
     * Méthode utilisant la commande pour dessiner le cercle
     * @param gc contexte graphique du canvas de l'application sur laquelle on dessine
     */
    @Override
    public void dessiner(GraphicsContext gc){
        //Pour dessiner nous utilisons la commande initialisé avec le cercle déjà défini
        commande= new DessinerCercle(cercle);
        commande.execute(gc);
    }

    /**
     * Méthode permettant la définition du cercle lorsque le bouton de la souris est pressé sur le canvas
     * @param event événement déclenché par le clic de la souris
     * @param gc contexte graphique du canvas de l'application sur laquelle on dessine
     * @param couleur couleur sélectionnée dans le sélecteur de couleur du trait de la figure
     * @param couleurRemplissage couleur sélectionnée dans le sélecteur de de couleur du remplissage de la figure
     */
    @Override
    public void definirFormeOnMousePressed(MouseEvent event, GraphicsContext gc, Color couleur, Color couleurRemplissage){
        //Pour ne pas se retrouver toujours avec la même référence du cercle on fait attention à bien instancié une nouvelle
        //fois le cercle avec rien de défini à l'intérieur
        cercle=new Cercle();
        //On défini ensuite la couleur du trait, du remplissage, sa largeur, et enfin on récupére les coordonnées
        //du point de départ de la figure
        gc.setStroke(couleur);
        cercle.setCouleurRemplissage(couleurRemplissage);
        cercle.setCouleur(couleur);
        cercle.setX((float)event.getX());
        cercle.setY((float)event.getY());
        cercle.setLargeurTrait((float)gc.getLineWidth());
        if(couleurRemplissage != Color.TRANSPARENT){
            cercle.setRempli(true);
        }
    }

    /**
     *  Méthode permettant la définition du cercle lorsque le bouton de la souris est relâché
     * @param event événement déclenché par le relâchement du clic de la souris
     */
    @Override
    public void definirFormeOnMouseReleased(MouseEvent event){
        //Formule encore une fois peu précise pour définir le rayon du cercle
        cercle.setRayon((float)(Math.abs(event.getX()-cercle.getX())+Math.abs(event.getY()-cercle.getY()))/2);
        if(cercle.getX()> event.getX()){
            cercle.setX((float)event.getX());
        }
        if(cercle.getY() > event.getY()){
            cercle.setY((float)event.getY());
        }
    }

    /**
     * Méthode permettant de récupérer le cercle défini
     * @return forme défini dans le dessinateur
     */
    public Cercle getForme(){
        return cercle;
    }
}
