package com.ameliawiki.wiki.service;

import com.ameliawiki.wiki.domain.Category;
import com.ameliawiki.wiki.domain.CategoryExample;
import com.ameliawiki.wiki.mapper.CategoryMapper;
import com.ameliawiki.wiki.util.CopyUtil;
import com.ameliawiki.wiki.util.SnowFlake;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import req.CategoryQueryReq;
import req.CategorySaveReq;
import resp.CategoryQueryResp;
import resp.PageResp;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CategoryService {

    private static final Logger LOG = LoggerFactory.getLogger(CategoryService.class);

    @Resource
    private CategoryMapper categoryMapper;

    @Resource
    private SnowFlake snowFlake;

    public PageResp<CategoryQueryResp> list(CategoryQueryReq req) {
        CategoryExample categoryExample = new CategoryExample();
        CategoryExample.Criteria criteria = categoryExample.createCriteria();
        PageHelper.startPage(req.getPage(), req.getSize());
        List<Category> categoryList =  categoryMapper.selectByExample(categoryExample);
        PageInfo<Category> pageInfo = new PageInfo<>(categoryList);
        LOG.info("total rows：{}", pageInfo.getTotal());
        LOG.info("total pages：{}", pageInfo.getPages());

//        List<CategoryResp> respList = new ArrayList<>();
//        for (Category category : categoryList) {
////            CategoryResp categoryResp = new CategoryResp();
////            BeanUtils.copyProperties(category, categoryResp);
//            CategoryResp categoryResp = CopyUtil.copy(category, CategoryResp.class);
//
//            respList.add(categoryResp);
//        }

        List<CategoryQueryResp> list = CopyUtil.copyList(categoryList, CategoryQueryResp.class);
        PageResp<CategoryQueryResp> pageResp = new PageResp<>();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(list);

        return pageResp;
    }

    //save
    public void save(CategorySaveReq req) {
        Category category = CopyUtil.copy(req, Category.class);
        if (ObjectUtils.isEmpty(req.getId())) {
            //是空就更新
            category.setId(snowFlake.nextId());
            categoryMapper.insert(category);
        } else {
            //update
            categoryMapper.updateByPrimaryKey(category);
        }
    }

    //delete
    public void delete(Long id) {
        categoryMapper.deleteByPrimaryKey(id);
    }
}
