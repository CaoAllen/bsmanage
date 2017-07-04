package com.spring.example.model;

import com.spring.example.domain.Post;
import com.spring.example.utils.DTOUtils;

import java.io.Serializable;

/**
 *
 * @author Allen Cao
 *
 */
public class PostForm implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private String title;

    private String content;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "PostForm{" + "title=" + title + ", content=" + content + '}';
    }

    public Post toEntity() {
        return DTOUtils.map(this, Post.class);
    }

}
