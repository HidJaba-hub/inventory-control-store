package com.company.electro_store.temp.authorization;

import com.company.electro_store.dto.persons.PostDto;
import com.company.electro_store.dto.persons.WorkDto;
import com.company.electro_store.functions.Action;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class CheckButton{
    private static PostDto postDto;
    public static void setUser_actions(PostDto post){
        postDto=post;
    }
    public static void check(Action action, Button button){
        if(postDto==null)return;
        if(postDto.getName().equals("Администратор")) return;
        for(WorkDto user_action: postDto.getActions() ) {
            if (user_action.getAction().equals(action)) {
                button.setVisible(true);
                return;
            }
        }
        button.setVisible(false);
    }
    public static void checkAndDelete(Action action, Button button, VBox vBox){
        if(postDto==null)return;
        if(postDto.getName().equals("Администратор")) return;
        for(WorkDto user_action: postDto.getActions() ) {
            if (user_action.getAction().equals(action)) {
                button.setVisible(true);
                return;
            }
        }
        vBox.getChildren().remove(button);
    }
    public static HBox hboxList(Action action, HBox hBox){
        if(postDto==null)return null;
        if(postDto.getName().equals("Администратор")) return hBox;
        for(WorkDto user_action: postDto.getActions() ) {
            if (user_action.getAction().equals(action)) {
                return hBox;
            }
        }
        return null;
    }
}
