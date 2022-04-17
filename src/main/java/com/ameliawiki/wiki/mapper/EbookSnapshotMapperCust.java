package com.ameliawiki.wiki.mapper;

import resp.StatisticResp;

import java.util.List;

public interface EbookSnapshotMapperCust {

    public void getSnapshot();
    List<StatisticResp> getStatistic();
    List<StatisticResp> get30Statistic();
}
