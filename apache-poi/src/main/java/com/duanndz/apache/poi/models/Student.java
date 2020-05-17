package com.duanndz.apache.poi.models;

import lombok.*;

import java.io.Serializable;

/**
 * Created By duan.nguyen at 5/17/20 8:40 AM
 */
@Getter
@Setter
@ToString(of = {"id", "name"})
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
public class Student implements Serializable {

    private static final Long serialVersionUID = 1L;

    private int id;
    private String name;
    private String email;

}
