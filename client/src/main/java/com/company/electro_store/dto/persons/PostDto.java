package com.company.electro_store.dto.persons;

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
    public void addAction(WorkDto work){
        actions.add(work);
    }
}