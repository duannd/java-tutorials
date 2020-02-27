package com.duanndz.rabbitmq.models;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * Created By ngdduan@gmail.com at 2/26/20 4:52 PM
 */
@Data
public class Message {

    @NotBlank
    private String content;

}
