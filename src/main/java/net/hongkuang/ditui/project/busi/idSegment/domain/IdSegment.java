package net.hongkuang.ditui.project.busi.idSegment.domain;

import net.hongkuang.ditui.framework.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;


/**
 * 号段存储表 busi_id_segment
 *
 * @author yj
 * @date 2019-01-04
 */
public class IdSegment extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**  */
    private Long id;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .toString();
    }
}
