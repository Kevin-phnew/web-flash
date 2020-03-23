package cn.enilu.flash.api.controller.system;

import cn.enilu.flash.api.controller.BaseController;
import cn.enilu.flash.bean.core.BussinessLog;
import cn.enilu.flash.bean.dictmap.DictMap;
import cn.enilu.flash.bean.entity.system.Link;
import cn.enilu.flash.bean.enumeration.BizExceptionEnum;
import cn.enilu.flash.bean.enumeration.Permission;
import cn.enilu.flash.bean.exception.ApplicationException;
import cn.enilu.flash.bean.vo.front.Rets;
import cn.enilu.flash.service.system.LinkService;
import cn.enilu.flash.utils.BeanUtil;
import cn.enilu.flash.utils.StringUtil;
import cn.enilu.flash.warpper.LinkWarpper;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * LinkController
 *
 * @author enilu
 * @version 2018/11/17 0017
 */
@RestController
@RequestMapping("/link")
public class LinkController extends BaseController {
    @Autowired
    private LinkService linkService;

    /**
     * 获取所有连接列表
     */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    @RequiresPermissions(value = {Permission.LINK})
    public Object list(String name) {

        if(StringUtil.isNotEmpty(name)){
            List<Link> list = linkService.findByNameLike(name);
            return Rets.success(new LinkWarpper(BeanUtil.objectsToMaps(list)).warp());
        }
        List<Link> list = linkService.queryAll();
        return Rets.success(new LinkWarpper(BeanUtil.objectsToMaps(list)).warp());
    }

    @RequestMapping(method = RequestMethod.POST)
    @BussinessLog(value = "添加连接", key = "linkName",dict=DictMap.class)
    @RequiresPermissions(value = {Permission.LINK_ADD})
    public Object add(String linkName, String linkUrl) {
        if (BeanUtil.isOneEmpty(linkName, linkUrl)) {
            throw new ApplicationException(BizExceptionEnum.REQUEST_NULL);
        }
        linkService.addLink(linkName, linkUrl);
        return Rets.success();
    }

    @RequestMapping(method = RequestMethod.PUT)
    @BussinessLog(value = "修改连接", key = "linkName",dict= DictMap.class)
    @RequiresPermissions(value = {Permission.LINK_EDIT})
    public Object update(Long id,String linkName, String linkUrl) {
        if (BeanUtil.isOneEmpty(linkName, linkUrl)) {
            throw new ApplicationException(BizExceptionEnum.REQUEST_NULL);
        }
        linkService.editLink(id,linkName, linkUrl);
        return Rets.success();
    }


    @RequestMapping(method = RequestMethod.DELETE)
    @BussinessLog(value = "删除连接", key = "id",dict=DictMap.class)
    @RequiresPermissions(value = {Permission.LINK_EDIT})
    public Object delete(@RequestParam Long id) {
        linkService.delteLink(id);
        return Rets.success();
    }

}
