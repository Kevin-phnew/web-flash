package cn.enilu.flash.service.system;

import cn.enilu.flash.bean.entity.system.Link;
import cn.enilu.flash.dao.system.LinkRepository;
import cn.enilu.flash.service.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

/**
 * 连接服务
 *
 * @author fengshuonan
 * @date 2017-04-27 17:00
 */
@Service
public class LinkService extends BaseService<Link,Long,LinkRepository> {
    private Logger logger = LoggerFactory.getLogger(LinkService.class);
    @Resource
    LinkRepository LinkRepository;

    public void addLink(String linkName, String linkUrl) {
        //判断有没有该连接
        List<Link> links = LinkRepository.findByNameLike(linkName);
        if(links != null && links.size() > 0){
            return ;
        }

        //添加连接
        Link link = new Link();
        link.setName(linkName);
        link.setUrl(linkUrl);
        this.LinkRepository.save(link);

    }

    public void editLink(Long linkId, String linkName, String linkUrl) {
        //删除之前的连接
        this.delteLink(linkId);

        //重新添加新的连接
        this.addLink(linkName,linkUrl);

    }

    public void delteLink(Long linkId) {
        //删除这个连接
        LinkRepository.deleteById(linkId);

    }
    
    @Override
    public Link get(Long id) {
        Optional<Link> optional = LinkRepository.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;
    }

    public List<Link> findByNameLike(String name) {
        return LinkRepository.findByNameLike(name);
    }

}
