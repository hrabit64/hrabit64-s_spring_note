package com.hrabit64.hrabit64s_spring_note;

import lombok.NoArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@NoArgsConstructor
public class TestUtils {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    public boolean isListSame(List<?> targetListA , List<?> targetListB){

        if(targetListA.size() != targetListB.size()) return false;
        for (int i = 0; i < targetListA.size(); i++) {
            if(!targetListA.get(i).toString().equals(targetListB.get(i).toString())) {
                logger.debug("{}",targetListA.get(i).toString());
                logger.debug("{}",targetListB.get(i).toString());
                return false;
            }
        }
        return true;
    }
}
