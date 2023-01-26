package com.company.electro_store.entity.store;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "rack")
public class Rack {
    @EmbeddedId
    private RackId rackId;

    @OneToMany(mappedBy = "rack", cascade = CascadeType.ALL, fetch=FetchType.EAGER)
    private List<Shell> shells;
    @Column(name="color")
    @Enumerated(EnumType.STRING)
    private DragIconType color;
    @Column(name="coordX")
    private Double coordX;
    @Column(name="coordY")
    private Double coordY;

    public Rack(String name, Place place) {
        rackId=new RackId(name,place);
    }

    public enum DragIconType {
        red,
        green,
        blue,
        black,
        yellow,
        purple,
        grey;
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

    public void addShell(Shell shell){
        shells.add(shell);
    }

}
