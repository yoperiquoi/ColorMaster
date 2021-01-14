package metier;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.File;
import java.io.Serializable;

public class Recent implements Serializable {
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

    public Recent(String fileName){
        setFileName(fileName);
    }


}
