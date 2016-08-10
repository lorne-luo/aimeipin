package com.meidi.repository;

import com.meidi.domain.BuyNotice;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by luanpeng on 16/5/13.
 */
public interface BuyNoticeRepository extends CrudRepository<BuyNotice, Integer> {

    BuyNotice findByFlag(int flag);
}
