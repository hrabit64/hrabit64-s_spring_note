package com.hrabit64.hrabit64s_spring_note.domain.setting;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;


//ref : https://www.javaguides.net/2019/12/spring-boot-mongodb-auto-generated-field-example.html
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Document(collection = "configs")
public class Configs {
    @Id
    private String id;

    @Field("CONFIGS_NAME")
    private String name;

    @Field("CONFIGS_VAL")
    private String val;

    @Builder
    public Configs(String id, String val) {
        this.id = id;
        this.val = val;
    }
}
