package cn.enilu.flash.warpper;

import cn.enilu.flash.bean.entity.system.Dict;
import cn.enilu.flash.bean.entity.system.Link;
import cn.enilu.flash.service.system.impl.ConstantFactory;
import cn.enilu.flash.utils.ToolUtil;

import java.util.List;
import java.util.Map;

/**
 * link列表的包装
 *
 * @author fengshuonan
 * @date 2017年4月25日 18:10:31
 */
public class LinkWarpper extends BaseControllerWarpper {

    public LinkWarpper(Object list) {
        super(list);
    }

    @Override
    public void warpTheMap(Map<String, Object> map) {
        StringBuffer detail = new StringBuffer();
        Long id = (Long) map.get("id");
//        List<Link> links = ConstantFactory.me().findInDict(id);
//        if(dicts != null){
//            for (Dict dict : dicts) {
//                detail.append(dict.getNum() + ":" +dict.getName() + ",");
//            }
//            map.put("detail", ToolUtil.removeSuffix(detail.toString(),","));
//        }
    }

}
