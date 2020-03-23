
package cn.enilu.flash.dao.system;


import cn.enilu.flash.bean.entity.system.Link;
import cn.enilu.flash.dao.BaseRepository;

import java.util.List;

public interface LinkRepository extends BaseRepository<Link,Long> {
    List<Link> findByNameLike(String name);
}
