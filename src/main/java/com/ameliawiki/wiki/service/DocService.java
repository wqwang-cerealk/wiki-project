package com.ameliawiki.wiki.service;

import com.ameliawiki.wiki.domain.Content;
import com.ameliawiki.wiki.domain.Doc;
import com.ameliawiki.wiki.domain.DocExample;
import com.ameliawiki.wiki.exception.BusinessException;
import com.ameliawiki.wiki.exception.BusinessExceptionCode;
import com.ameliawiki.wiki.mapper.ContentMapper;
import com.ameliawiki.wiki.mapper.DocMapper;
import com.ameliawiki.wiki.mapper.DocMapperCust;
import com.ameliawiki.wiki.util.CopyUtil;
import com.ameliawiki.wiki.util.RedisUtil;
import com.ameliawiki.wiki.util.RequestContext;
import com.ameliawiki.wiki.util.SnowFlake;
import com.ameliawiki.wiki.websocket.WebSocketServer;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import req.DocQueryReq;
import req.DocSaveReq;
import resp.DocQueryResp;
import resp.PageResp;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DocService {

    private static final Logger LOG = LoggerFactory.getLogger(DocService.class);

    @Resource
    private DocMapper docMapper;

    @Resource
    private DocMapperCust docMapperCust;

    @Resource
    private ContentMapper contentMapper;

    @Resource
    private SnowFlake snowFlake;

    @Resource
    public RedisUtil redisUtil;

    @Resource
    public WsService wsService;

    public PageResp<DocQueryResp> list(DocQueryReq req) {
        DocExample docExample = new DocExample();
        docExample.setOrderByClause("sort asc");
        DocExample.Criteria criteria = docExample.createCriteria();
        PageHelper.startPage(req.getPage(), req.getSize());
        List<Doc> docList =  docMapper.selectByExample(docExample);
        PageInfo<Doc> pageInfo = new PageInfo<>(docList);
        LOG.info("total rows：{}", pageInfo.getTotal());
        LOG.info("total pages：{}", pageInfo.getPages());

        List<DocQueryResp> list = CopyUtil.copyList(docList, DocQueryResp.class);
        PageResp<DocQueryResp> pageResp = new PageResp<>();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(list);

        return pageResp;
    }

    public List<DocQueryResp> all(Long ebookId) {
        DocExample docExample = new DocExample();
        docExample.createCriteria().andEbookIdEqualTo(ebookId);
        docExample.setOrderByClause("sort asc");
        List<Doc> docList =  docMapper.selectByExample(docExample);

        List<DocQueryResp> list = CopyUtil.copyList(docList, DocQueryResp.class);

        return list;
    }

    //save
    @Transactional
    public void save(DocSaveReq req) {
        Doc doc = CopyUtil.copy(req, Doc.class);
        Content content = CopyUtil.copy(req, Content.class);
        if (ObjectUtils.isEmpty(req.getId())) {
            //是空就更新
            doc.setId(snowFlake.nextId());
            doc.setViewCount(0);
            doc.setVoteCount(0);
            docMapper.insert(doc);

            content.setId(doc.getId());
            contentMapper.insert(content);
        } else {
            //update
            docMapper.updateByPrimaryKey(doc);
            int count = contentMapper.updateByPrimaryKeyWithBLOBs(content);
            if (count == 0) {
                contentMapper.insert(content);
            }
        }
    }

    //delete
    public void delete(Long id) {
        docMapper.deleteByPrimaryKey(id);
    }

    public void delete(List<String> ids) {
        DocExample docExample = new DocExample();
        DocExample.Criteria criteria = docExample.createCriteria();
        criteria.andIdIn(ids);
        docMapper.deleteByExample(docExample);
    }

    public String findContent(Long id) {
        Content content = contentMapper.selectByPrimaryKey(id);
        //increase view count
        docMapperCust.increaseViewCount(id);
        if (ObjectUtils.isEmpty(content)) {
            return "";
        } else{
            return content.getContent();
        }
    }

    /**
     * vote
     */
    public void vote(Long id) {
        String ip = RequestContext.getRemoteAddr();
        if (redisUtil.validateRepeat("DOC_VOTE" + id + "_" + ip, 3600 * 24)) {
            docMapperCust.increaseVoteCount(id);
        } else {
            throw new BusinessException(BusinessExceptionCode.VOTE_REPEAT);
        }

        Doc docDb = docMapper.selectByPrimaryKey(id);
        String logId = MDC.get("LOG_ID");
        wsService.sendInfo(docDb.getName() + " is liked!", logId);
    }

    public void updateEbookInfo() {
        docMapperCust.updateEbookInfo();
    }
}
