package fenetre.dessinateur;

import fenetre.commande.DessinerDessin;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import metier.formes.Dessin;

/**
 * Définition d'un dessinateur permettant de définir le dessin à dessiner
 * Cette forme est différente des autre car elle est défini par des tableau de coordonnées x et y
 */
public class DessinateurDessin extends Dessinateur implements IDessinePendant{
    /**
     * Dessin qui va être défini
     */
    Dessin dessin = new Dessin();

    /**
     * Méthode permettant la définition du dessin lorsque le bouton de la souris est pressé sur le canvas
     * @param event événement déclenché par le clic de la souris
     * @param gc contexte graphique du canvas de l'application sur laquelle on dessine
     * @param couleur couleur sélectionnée dans le sélecteur de couleur du trait de la figure
     * @param couleurRemplissage couleur sélectionnée dans le sélecteur de de couleur du remplissage de la figure
     */
    @Override
    public void definirFormeOnMousePressed(MouseEvent event, GraphicsContext gc, Color couleur, Color couleurRemplissage){
        //Pour ne pas se retrouver toujours avec la même référence du dessin on fait attention à bien instancié une nouvelle
        //fois le dessin avec rien de défini à l'intérieur
        dessin=new Dessin();
        //On défini ensuite la couleur du trait, du remplissage, sa largeur, et enfin on récupére les coordonnées
        //du point de départ de la figure
        gc.setStroke(couleur);
        dessin.setCouleur(couleur);
        dessin.setX((float)event.getX());
        dessin.setY((float)event.getY());
        dessin.setLargueurTrait((float)gc.getLineWidth());
        gc.beginPath();
    }

    /**
     * Méthode permettant la définition de la figure pendant que l'utilisateur déplace la souris sur le canvas
     * @param e événement déclenché par le déplacement de la souris
     * @param gc contexte graphique du canvas de l'application sur laquelle on dessine
     */
    public void definirPendantFigure(MouseEvent e,GraphicsContext gc){
        //A chaque fois qu'on se déplace on ajoute les coordonnée au path du contexte graphique
        gc.lineTo(e.getX(),e.getY());
        //On ajout ensuite les deux nouvelle coordonnées au dessin pour pouvoir le retracer/effacer en faisant notre propre path
        dessin.pointsY.add((float)e.getY());
        dessin.pointsX.add((float)e.getX());
        gc.stroke();
    }


    /**
     *  Méthode permettant la définition du carré lorsque le bouton de la souris est relâché
     * @param e événement déclenché par le relâchement du clic de la souris
     */
    public void definirFormeOnMouseReleased(MouseEvent e){
        //On les ajoute aux tableaux du dessin
        dessin.pointsY.add((float)e.getY());
        dessin.pointsX.add((float)e.getX());
    }

    public void dessiner(GraphicsContext gc){
        //On ajoute au path du gc les dernières coordonnées
        gc.lineTo(dessin.pointsX.get(dessin.pointsX.size()-1),dessin.pointsY.get(dessin.pointsY.size()-1));
        gc.stroke();
        gc.closePath();
        //On instancie la commande permettant de retracer
        commande= new DessinerDessin(dessin);
    }

    /**
     * Méthode permettant de récupérer le dessin défini
     * @return forme défini dans le dessinateur
     */
    public Dessin getForme(){
        return dessin;
    }
}
