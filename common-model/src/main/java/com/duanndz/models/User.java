package com.duanndz.models;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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
    @NotNull
    @NotBlank
    private String firstName;
    @NotNull
    @NotBlank
    private String lastName;

}
