package com.company.electro_store.dto.persons;

import com.company.electro_store.entity.persons.Post;
import com.company.electro_store.entity.persons.Work;
import com.company.electro_store.server.functions.Action;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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
    public WorkDto(Work work) {
        this.action=work.getAction();
        this.post=new PostDto(work.getPost(),true);
        this.workId=work.getWorkId();
    }
}