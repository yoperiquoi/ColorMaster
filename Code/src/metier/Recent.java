package metier;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.File;

public class Recent {
    private final StringProperty fileName = new SimpleStringProperty();
    public StringProperty fileNameProperty(){return fileName;}
    public String getFileName(){return fileName.get();}
    public void setFileName(String valeur){ fileName.set(valeur);}

    public String getApercu() {
        return apercu;
    }

    public void setApercu(String apercu) {
        this.apercu = apercu;
    }

    private String apercu;


}
