package com.hrabit64.hrabit64s_spring_note.domain.setting;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfigsRepository extends MongoRepository<Configs,String> {
    Configs findConfigsByName (String name);
}
