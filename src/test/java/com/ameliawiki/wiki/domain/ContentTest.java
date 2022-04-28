package com.ameliawiki.wiki.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ContentTest {

    Content content = new Content(1L, "hello");

    @BeforeEach
    void setUp() {
    }

    @Test
    void getId() {
        assertEquals(1L, content.getId());
    }

    @Test
    void setId() {
        content.setId(37L);
        assertEquals(37L, content.getId());
    }

    @Test
    void getContent() {
        assertEquals("hello", content.getContent());
    }

    @Test
    void setContent() {
        content.setContent("test");
        assertEquals("test", content.getContent());
    }
}