package com.hrabit64.hrabit64s_spring_note.config;

import com.hrabit64.hrabit64s_spring_note.service.ConfigsService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Getter
public class GlobalConfig {
    
    @Autowired
    private final ConfigsService configsService;
    private final String adminID;
    private final String defaultCategory;

    public GlobalConfig(ConfigsService configsService){
        this.configsService = configsService;
        this.adminID = configsService.findConfigsVal("admin_id");
        this.defaultCategory = configsService.findConfigsVal("default_category");
    }
    
}
