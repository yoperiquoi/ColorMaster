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
        cercle=new Cercle();
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
    @Override
    public Cercle getForme(){
        return cercle;
    }
}
