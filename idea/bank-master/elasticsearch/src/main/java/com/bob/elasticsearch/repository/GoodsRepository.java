package com.bob.elasticsearch.repository;

import com.bob.elasticsearch.model.GoodsInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;

/**
 * @author wb-jjb318191
 * @create 2019-04-17 15:35
 */
@Component
public interface GoodsRepository extends ElasticsearchRepository<GoodsInfo,Long> {
}