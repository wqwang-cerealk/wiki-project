package com.ameliawiki.wiki.service;

import com.ameliawiki.wiki.domain.Test;
import com.ameliawiki.wiki.mapper.TestMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TestService {

    @Resource
    private TestMapper testMapper;

    public List<Test> list() {
        return testMapper.list();
    }
}
