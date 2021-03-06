package com.spring.example.model;


import java.io.Serializable;


/**
 *
 * @author Allen Cao
 *
 */
public class CommentForm implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;


    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "CommentForm{ content=" + content + '}';
    }
}
