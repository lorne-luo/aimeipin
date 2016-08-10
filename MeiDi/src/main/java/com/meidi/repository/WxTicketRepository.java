package com.meidi.repository;

import com.meidi.domain.WxTicket;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by luanpeng on 16/4/2.
 */
public interface WxTicketRepository extends CrudRepository<WxTicket,Integer> {
    WxTicket findByAppid(String appid);
}
