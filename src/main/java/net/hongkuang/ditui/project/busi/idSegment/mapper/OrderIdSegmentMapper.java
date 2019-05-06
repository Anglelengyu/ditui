package net.hongkuang.ditui.project.busi.idSegment.mapper;


import net.hongkuang.ditui.project.busi.idSegment.domain.OrderIdSegment;

/**
 * 号段存储 数据层
 *
 * @author yj
 * @date 2019-01-04
 */
public interface OrderIdSegmentMapper {
    /**
     * 新增号段存储
     *
     * @param idSegment 号段存储信息
     * @return 结果
     */
    public int insertOrderIdSegment(OrderIdSegment idSegment);


}