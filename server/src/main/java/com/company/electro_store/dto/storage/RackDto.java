package com.company.electro_store.dto.storage;

import com.company.electro_store.entity.store.Rack;
import com.company.electro_store.entity.store.Shell;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class RackDto {
    private RackIdDto rackId;

    private List<ShellDto> shells=new ArrayList<>();
    private Rack.DragIconType color;
    private Double coordX, coordY;

    public RackDto(String name, Rack.Place place) {
        rackId=new RackIdDto(name,place);
    }

    public void addShell(ShellDto shell){
        shells.add(shell);
    }

    public RackDto(Rack rack){
        if(rack.getCoordX()!=null){
            int x;
        }
        this.coordY=rack.getCoordY();
        this.coordX=rack.getCoordX();
        this.color=rack.getColor();
        this.rackId=new RackIdDto(rack.getRackId());
        for(Shell shell:rack.getShells()){
            this.shells.add(new ShellDto(shell,true));
        }
    }

}
