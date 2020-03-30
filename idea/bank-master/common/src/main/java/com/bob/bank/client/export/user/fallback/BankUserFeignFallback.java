package com.bob.bank.client.export.user.fallback;

import com.bob.bank.client.constants.ErrorCodeEnum;
import com.bob.bank.client.export.user.BankUserFeignService;
import com.bob.bank.client.model.BankUser;
import com.bob.bank.client.result.PojoResult;
import com.bob.bank.client.utils.ErrorResultGenerator;
import org.springframework.stereotype.Service;

import static com.bob.bank.client.constants.ErrorCodeEnum.SYSTEM_ERROR;

/**
 * @author wb-jjb318191
 * @create 2018-10-23 14:21
 */
@Service
public class BankUserFeignFallback implements BankUserFeignService {

    @Override
    public PojoResult<Boolean> createUser(BankUser bankUser) {
        return ErrorResultGenerator.generatePojoResult(SYSTEM_ERROR, false);
    }

    @Override
    public PojoResult<Boolean> checkIfUserApplicable(Integer userId) {
        return ErrorResultGenerator.generatePojoResult(SYSTEM_ERROR, false);
    }
}
