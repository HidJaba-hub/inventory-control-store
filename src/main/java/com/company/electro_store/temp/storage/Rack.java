package com.company.electro_store.temp.storage;

import com.company.electro_store.dto.storage.RackDto;
import com.company.electro_store.functions.Action;
import com.company.electro_store.temp.draganddrop.DragIconType;
import com.company.electro_store.temp.storage.Shell;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Rack {
    private final StringProperty rackName;
    private RackDto.Place place;
    private DragIconType color;
    private final StringProperty colorString;
    private ObservableList<Shell> shells;
    public Rack(){
        this(null,null,DragIconType.black,null);
    }
    public Rack(String rackName, RackDto.Place place, DragIconType color, ObservableList<Shell> shells){
        this.rackName=new SimpleStringProperty(rackName);
        this.place=place;
        this.color=color;
        this.colorString=new SimpleStringProperty(color.toString());
        this.shells=shells;
    }
    public StringProperty rackNameProperty(){
        return rackName;
    }
    public void setName(String name){
        this.rackName.set(name);
    }
}
