package com.meidi.repository;

import com.meidi.domain.GroupLaunch;
import com.meidi.domain.Order;
import com.meidi.repository.custom.OrderRepositoryCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;


/**
 * Created by luanpeng on 16/3/31.
 */
public interface OrderRepository extends PagingAndSortingRepository<Order,Integer>,OrderRepositoryCustom{

    Order findByOrderCode(String orderCode);

    Page<Order> findByWxOpenid(String wxOpenid,Pageable pageable);

    Order findByWxOpenidAndId(String wxOpenid,Integer id);

    List<Order> findByWxOpenidAndCommodityIdAndStateOrderByCreateTimeDesc(String wxOpenid,Integer commodityId,Integer state);

    List<Order> findByWxOpenidAndCommodityIdAndBookingFlagAndStateOrderByCreateTimeDesc(String wxOpenid,Integer commodityId,Integer bookingFlag,Integer state);

    List<Order> findByLaunchId(Integer id);

    Page<Order> findByFlag(Integer flag, Pageable pageable);

    Page<Order> findByFlagIsNot(Integer flag, Pageable pageable);
}
