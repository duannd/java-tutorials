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

    @NotNull(message = "firstName must not be null")
    @NotBlank(message = "firstName must not be blank")
    private String firstName;

    @NotNull(message = "lastName must not be null")
    @NotBlank(message = "lastName must not be blank")
    private String lastName;

}
