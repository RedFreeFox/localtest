package com.bob.bank.client.export.user;

import com.bob.bank.client.export.user.fallback.BankUserFeignFallback;
import com.bob.bank.client.model.BankUser;
import com.bob.bank.client.result.PojoResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author wb-jjb318191
 * @create 2018-10-19 17:45
 */
@FeignClient(value = "bank-user", fallback = BankUserFeignFallback.class)
public interface BankUserFeignService {

    /**
     * 创建用户
     *
     * @param bankUser
     * @return
     */
    @PostMapping("/bank/user")
    PojoResult<Boolean> createUser(BankUser bankUser);

    /**
     * 验证指定用户是存在和激活中
     *
     * @param userI
     * @return
     */
    @GetMapping("/bank/user/{id}")
    PojoResult<Boolean> checkIfUserApplicable(@PathVariable("id") Integer userI);

}