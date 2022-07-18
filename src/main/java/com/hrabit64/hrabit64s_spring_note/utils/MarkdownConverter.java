package com.hrabit64.hrabit64s_spring_note.utils;

import org.springframework.stereotype.Component;
import org.commonmark.node.*;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

@Component
public class MarkdownConverter {


    /**
     * parse markdown to html
     * @param target target document
     * @return parsing document
     */
    public String convert(String target){
        Parser parser = Parser.builder().build();
        Node document = parser.parse(target);
        HtmlRenderer renderer = HtmlRenderer.builder().build();
        return renderer.render(document);
    }
}
