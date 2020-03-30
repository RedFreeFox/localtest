package com.bob.elasticsearch.repository;

import com.bob.elasticsearch.model.Employee;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;

/**
 * @author wb-jjb318191
 * @create 2019-04-17 14:38
 */
@Component
public interface EmployeeRepository extends ElasticsearchRepository<Employee, String> {

    /**
     * 查询雇员信息
     *
     * @param id
     * @return
     */
    Employee queryEmployeeById(String id);

}