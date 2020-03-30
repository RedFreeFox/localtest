package com.bob.elasticsearch.repository;

import java.util.Optional;
import java.util.function.Consumer;

import com.bob.elasticsearch.config.ElasticsearchTestConfiguration;
import com.bob.elasticsearch.model.Employee;
import com.google.gson.Gson;
import org.apache.lucene.queryparser.xml.builders.BooleanQueryBuilder;
import org.elasticsearch.action.ActionFuture;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * @author wb-jjb318191
 * @create 2019-07-16 15:07
 */
public class EmployeeRepositoryTest extends ElasticsearchTestConfiguration {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private TransportClient transportClient;

    @Test
    public void testIndex() {
        Employee employee = new Employee();
        employee.setId("00001");
        employee.setFirstName("Jiang");
        employee.setLastName("Bo");
        employee.setAge(31);
        employee.setAbout("java");
        Employee result = employeeRepository.index(employee);
        System.out.println(new Gson().toJson(result));
    }

    @Test
    public void testFindById() {
        Optional<Employee> result = employeeRepository.findById("00001");
        if (result.isPresent()) {
            System.out.println(new Gson().toJson(result.get()));
        }
    }

    /**
     * 删除索引
     */
    @Test
    public void testDeleteIndex(){
        DeleteRequest deleteRequest = new DeleteRequest("company");
        ActionFuture<DeleteResponse> response =  transportClient.delete(deleteRequest);
        System.out.println("....");
    }


    @Test
    public void testSearch() {
        Employee employee = new Employee();
        employee.setId("00001");
        Page<Employee> result = employeeRepository.searchSimilar(employee, new String[] {"firstName", "lastName"}, PageRequest.of(0, 1));
        System.out.println(new Gson().toJson(result.getContent()));
    }

    @Test
    public void testSearchByQuery(){
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        queryBuilder.must(QueryBuilders.termQuery("firstName","Jiang"));
        Iterable<Employee> result = employeeRepository.search(queryBuilder);
        result.forEach(employee -> System.out.println(new Gson().toJson(employee)));
    }

}
