package com.duanndz.webflux.models;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(of = {"id", "name"})
public class User {

    private Long id;

    @NotBlank
    private String name;

}
