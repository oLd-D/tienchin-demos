package com.guo.spel_demo;

import org.springframework.stereotype.Service;

@Service("bs")
public class BookService {
    public String sayBook() {
        return "这是一本好书。。。";
    }
}