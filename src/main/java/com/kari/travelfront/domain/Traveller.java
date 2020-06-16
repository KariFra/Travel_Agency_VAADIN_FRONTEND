package com.kari.travelfront.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Traveller {

    private Long id;
    private String firstName;
    private String lastName;
    private String mail;
    private String password;
    private String role;
    private String avatarUrl;
    private List<Long> tripsId;


    @Override
    public String toString() {
        return "Traveller{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", mail='" + mail + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                ", avatarUrl='" + avatarUrl + '\'' +
                ", trips=" + tripsId +'\'' +
                '}';
    }
}
