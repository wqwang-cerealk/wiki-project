package com.ameliawiki.wiki.service;

import com.ameliawiki.wiki.mapper.EbookSnapshotMapperCust;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class EbookSnapshotService {

    @Resource
    private EbookSnapshotMapperCust ebookSnapshotMapperCust;

    public void getSnapshot() {
        ebookSnapshotMapperCust.getSnapshot();
    }

}
