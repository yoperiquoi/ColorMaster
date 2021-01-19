package fenetre.dessinateur;

import javafx.scene.input.MouseEvent;

/**
 * Interface définissant une forme défini lorsque le clic de la souris est relâché
 */
public interface IDessineOnMouseReleased {
    /**
     *  Méthode permettant la définition de la forme lorsque le bouton de la souris est relâché
     * @param event événement déclenché par le relâchement du clic de la souris
     */
    void definirFormeOnMouseReleased(MouseEvent event);

}
