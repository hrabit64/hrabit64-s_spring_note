package com.hrabit64.hrabit64s_spring_note.domain.databaseSequence;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;

//ref : https://www.javaguides.net/2019/12/spring-boot-mongodb-auto-generated-field-example.html
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Document(collection = "database_sequences")
public class DatabaseSequence {
    @Id
    private String id;

    private long seq;

    @Builder
    public DatabaseSequence(String id, long seq) {
        this.id = id;
        this.seq = seq;
    }
}
