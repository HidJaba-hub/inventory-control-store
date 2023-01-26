package com.company.electro_store.dto.persons;

import com.company.electro_store.entity.persons.Post;
import com.company.electro_store.entity.persons.User;
import com.company.electro_store.entity.persons.Work;
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
public class PostDto {

    private Integer post_id;

    private String name;

    private Double cost;

    private List<WorkDto> actions=new ArrayList<>();


    public PostDto(String name, Double cost) {
        actions=new ArrayList<>();
        this.name = name;
        this.cost = cost;
    }
    public PostDto(Post post) {
        for (Work work: post.getActions()) {
            this.actions.add(new WorkDto(work));
        }
        this.name=post.getName();
        this.cost=post.getCost();
        this.post_id=post.getPost_id();
    }
    public PostDto(Post post, boolean clear) {
        this.name=post.getName();
        this.cost=post.getCost();
        this.post_id=post.getPost_id();
    }
    public void addAction(WorkDto work){
        actions.add(work);
    }
}