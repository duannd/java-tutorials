package com.duanndz.models;

import lombok.*;

/**
 * duan.nguyen
 * Datetime 5/5/20 09:49
 */
@Getter
@Setter
@ToString(of = {"id", "firstName", "lastName"})
@EqualsAndHashCode(of = {"id"})
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private int id;
    private String firstName;
    private String lastName;

}
