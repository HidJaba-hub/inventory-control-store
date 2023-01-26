package com.company.electro_store.dto.persons;

import com.company.electro_store.entity.persons.Person;
import com.company.electro_store.entity.persons.Post;
import com.company.electro_store.entity.persons.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserDto {

    private Integer userId;
    private String login;
    private byte[] password;
    private Double minutes;
    private PersonDto person;

    private PostDto post;

    public UserDto(String login, byte[] password) {
        this.login = login;
        this.password = password;
        this.person = person;
    }
    public UserDto(User user) {
        this.userId = user.getUserId();
        this.login = user.getLogin();
        this.password=user.getPassword();
        this.minutes=user.getMinutes();
        this.login=user.getLogin();
        if(user.getPerson()!=null)this.person=new PersonDto(user.getPerson());
        if(user.getPost()!=null)this.post=new PostDto(user.getPost());
    }
    public UserDto(User user, boolean clear) {
        this.userId = user.getUserId();
        this.login = user.getLogin();
        this.password=user.getPassword();
        this.minutes=user.getMinutes();
        this.login=user.getLogin();
        if(clear)this.person=null;
        if(user.getPost()!=null)this.post=new PostDto(user.getPost());
    }
}
