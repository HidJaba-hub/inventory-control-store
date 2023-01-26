package com.company.electro_store.dto.persons;

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
    }
}
