package cn.enilu.flash.bean.entity.system;

import cn.enilu.flash.bean.entity.BaseEntity;
import lombok.Data;
import org.hibernate.annotations.Table;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;

/**
 * Created  on 2018/4/2 0002.
 *
 * @author enilu
 */
@Entity(name="t_sys_link")
@Table(appliesTo = "t_sys_link",comment = "连接")
@Data
@EntityListeners(AuditingEntityListener.class)
public class Link extends BaseEntity {
    @Column
    private String name;
    @Column
    private String url;

}
