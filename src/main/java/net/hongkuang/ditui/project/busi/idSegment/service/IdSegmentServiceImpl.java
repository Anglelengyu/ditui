package net.hongkuang.ditui.project.busi.idSegment.service;

import com.sohu.idcenter.IdWorker;
import net.hongkuang.ditui.common.utils.RandomUtil;
import net.hongkuang.ditui.project.busi.idSegment.domain.IdSegment;
import net.hongkuang.ditui.project.busi.idSegment.mapper.IdSegmentMapper;
import net.hongkuang.ditui.project.busi.idSegment.mapper.OrderIdSegmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 号段存储 服务层实现
 *
 * @author yj
 * @date 2019-01-04
 */
@Service
public class IdSegmentServiceImpl implements IIdSegmentService {
    @Autowired
    private IdSegmentMapper idSegmentMapper;
    @Autowired
    private OrderIdSegmentMapper orderIdSegmentMapper;
    private IdWorker idWorker = new IdWorker(0, 1, 0);

    @Override
    public String genTaskId() {
        IdSegment idSegment = new IdSegment();
        idSegmentMapper.insertIdSegment(idSegment);
        Long id = idSegment.getId();
        if (id.compareTo(100000000L) >= 0) {
            return RandomUtil.toFixdLengthString(id, 10);
        } else {
            return RandomUtil.toFixdLengthString(id, 8);
        }
    }

    @Override
    public String genOrderId() {
//		OrderIdSegment idSegment = new OrderIdSegment();
//		orderIdSegmentMapper.insertOrderIdSegment(idSegment);
//		Long id = idSegment.getId();
//		if (id.compareTo(100000000L) >= 0) {
//			return RandomUtil.toFixdLengthString(id, 10);
//		} else {
//			return RandomUtil.toFixdLengthString(id, 8);
//		}
        return idWorker.getId() + "";
    }

}
