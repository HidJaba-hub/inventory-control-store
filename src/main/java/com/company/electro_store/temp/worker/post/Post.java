package com.company.electro_store.temp.worker.post;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Post {
    private final StringProperty postName;
    private  DoubleProperty cost;
    private final List<String> functions;
    private int post_id;
    public Post() {
        this(null,0.0,0,null);
    }
    public Post(String post, Double cost, Integer post_id, List<String> functions){
        this.postName=new SimpleStringProperty(post);
        this.cost=new SimpleDoubleProperty(cost);
        this.post_id=post_id;
        this.functions=functions;
    }
    public StringProperty postNameProperty() {
        return postName;
    }
    public DoubleProperty costProperty() {
        return cost;
    }
    public String getPostName(){
        return postName.get();
    }
    public Double getCost(){
        return cost.get();
    }
    public void setPostName(String postName){
        this.postName.set(postName);
    }
    public void setCost(String cost){
        this.cost.set(Double.parseDouble(cost));
    }
}
