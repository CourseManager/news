package com.leehom.news.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leehom.news.enums.ResultEnum;
import com.leehom.news.exception.NewsException;
import com.leehom.news.po.Link;
import com.leehom.news.po.User;
import com.leehom.news.service.LinkService;
import com.leehom.news.utils.ResultVOUtil;
import com.leehom.news.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(value = "/news/admin/link")
@RestController
@Slf4j
@Api(value = "/link",tags = "后台友情链接相关接口")
public class AdminLinkController {
    @Autowired
    private LinkService linkService;

    /**
     * 增加友情链接
     * @param linkName
     * @param linkUrl
     * @return
     */
    @PostMapping(value = "/insert")
    public ResultVO insert(@RequestParam("linkName") String linkName,
                           @RequestParam("linkUrl") String linkUrl){
        Link link = new Link();
        link.setLinkName(linkName);
        link.setLinkUrl(linkUrl);
        int result = linkService.insertLink(link);
        if(result != 1){
            throw new NewsException(ResultEnum.INSERT_TYPE_ERROR);
        }
        return ResultVOUtil.success(link);
    }

    /**
     * 根据linkId删除友情链接
     * @param linkId
     * @return
     */
    @GetMapping(value = "/delete")
    public ResultVO delete(@RequestParam("linkId") Integer linkId){
        Link link = linkService.selectLinkById(linkId);
        if(link == null){
            throw new NewsException(ResultEnum.TYPEID_ERROR);
        }
        int result = linkService.deleteLinkById(linkId);
        return ResultVOUtil.success(link);
    }

    /**
     * 根据linkId查找友情链接
     * @param linkId
     * @return
     */
    @GetMapping(value = "/select")
    public ResultVO select(@RequestParam("linkId") Integer linkId){
        Link link = linkService.selectLinkById(linkId);
        if(link == null){
            throw new NewsException(ResultEnum.SELECT_LINK_FAIL);
        }
        return ResultVOUtil.success(link);
    }

    /**
     * 查找所有友情链接
     * @return
     */
    @GetMapping(value = "/list")
    public ResultVO list(@RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                         @RequestParam(value = "pageSize",defaultValue = "10") int pageSize){
        PageHelper.startPage(pageNum,pageSize);
        List<Link> linkList = linkService.selectAll();
        PageInfo pageInfo = new PageInfo(linkList);
        return ResultVOUtil.success(pageInfo);
    }

    @GetMapping(value = "/count")
    public ResultVO count(){
        Integer result = linkService.countLink();
        return ResultVOUtil.success(result);
    }

    /**
     * 更新友情链接
     * @param link
     * @return
     */
    @PostMapping(value = "/update")
    public ResultVO update(@RequestBody Link link){
        Link result = linkService.selectLinkById(link.getLinkId());
        if(result == null){
            throw new NewsException(ResultEnum.SELECT_LINK_FAIL);
        }
        result.setLinkUrl(link.getLinkUrl());
        result.setLinkName(link.getLinkName());
        if(linkService.updateLink(link) != 1){
            throw new NewsException(ResultEnum.UPDATE_TYPE_ERROR);
        }
        return ResultVOUtil.success(linkService.selectLinkById(link.getLinkId()));
    }

}
