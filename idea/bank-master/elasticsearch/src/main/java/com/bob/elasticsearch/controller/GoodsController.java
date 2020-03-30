package com.bob.elasticsearch.controller;

import java.util.ArrayList;
import java.util.List;

import com.bob.elasticsearch.model.GoodsInfo;
import com.bob.elasticsearch.repository.GoodsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author wb-jjb318191
 * @create 2019-04-17 15:35
 */
@RestController
public class GoodsController {

    @Autowired
    private GoodsRepository goodsRepository;

    //http://localhost:8888/save
    @GetMapping("save")
    public String save() {
        GoodsInfo goodsInfo = new GoodsInfo(System.currentTimeMillis(),
            "商品" + System.currentTimeMillis(), "这是一个测试商品");
        goodsRepository.save(goodsInfo);
        return "success";
    }

    //http://localhost:8888/delete?id=1525415333329
    @GetMapping("delete")
    public String delete(Long id) {
        goodsRepository.deleteById(id);
        return "success";
    }

    //http://localhost:8888/update?id=1525417362754&name=修改&description=修改
    @GetMapping("update")
    public String update(long id, String name, String description) {
        GoodsInfo goodsInfo = new GoodsInfo(id,
            name, description);
        goodsRepository.save(goodsInfo);
        return "success";
    }

    //http://localhost:8888/getOne?id=1525417362754
    @GetMapping("getOne")
    public GoodsInfo getOne(long id) {
        GoodsInfo goodsInfo = goodsRepository.findById(id).get();
        return goodsInfo;
    }

    @GetMapping("/goodinfos")
    public List<GoodsInfo> getAll(){
        List<GoodsInfo> goodsInfos = new ArrayList<>();
        goodsRepository.findAll().forEach(goodsInfo -> goodsInfos.add(goodsInfo));
        return goodsInfos;
    }


    //每页数量
    private Integer PAGESIZE = 10;

    //http://localhost:8888/getGoodsList?query=商品
    //http://localhost:8888/getGoodsList?query=商品&pageNumber=1
    //根据关键字"商品"去查询列表，name或者description包含的都查询
    @GetMapping("getGoodsList")
    public List<GoodsInfo> getList(Integer pageNumber, String query) {
        if (pageNumber == null) {
            pageNumber = 0;
        }
        //es搜索默认第一页页码是0
        //SearchQuery searchQuery = getEntitySearchQuery(pageNumber, PAGESIZE, query);
        //Page<GoodsInfo> goodsPage = goodsRepository.search(searchQuery);
        //return goodsPage.getContent();
        return null;
    }

}
