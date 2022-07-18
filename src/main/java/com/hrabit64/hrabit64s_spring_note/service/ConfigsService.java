package com.hrabit64.hrabit64s_spring_note.service;


import com.hrabit64.hrabit64s_spring_note.domain.setting.Configs;
import com.hrabit64.hrabit64s_spring_note.domain.setting.ConfigsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ConfigsService {

    @Autowired
    private final ConfigsRepository configsRepository;

    @Transactional(readOnly = true)
    public String findConfigsVal(String name){
        String val = null;
        try{
            val = configsRepository.findConfigsByName(name).getVal();
        }
        catch (IllegalArgumentException | NullPointerException e){
            return val;
        }
        return val;
    }

    @Transactional
    public void addSetting(String id,String val){
        configsRepository.save(new Configs(id,val));
    }
}
