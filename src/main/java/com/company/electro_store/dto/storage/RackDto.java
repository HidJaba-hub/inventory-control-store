package com.company.electro_store.dto.storage;

import com.company.electro_store.temp.draganddrop.DragIconType;
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
    public DragIconType color;
    public Double coordX;
    public Double coordY;

    public RackDto(String name, Place place, DragIconType color) {

        rackId=new RackIdDto(name,place);
        this.color=color;
    }

    public enum Place{
        STORAGE("Склад"),
        HALL("Зал");
        public final String str;
        Place(String str){

            this.str=str;
        }
        public String getStr(){

            return str;
        }
    }

    public void addShell(ShellDto shell){
        shells.add(shell);
    }

}
