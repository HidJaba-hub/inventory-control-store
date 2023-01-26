package com.company.electro_store.dto.persons;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import com.company.electro_store.functions.Action;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class WorkDto {

    private Integer workId;

    private Action action;

    private PostDto post;


    public WorkDto(Action action, PostDto post) {
        this.action=action;
        this.post=post;
    }
}