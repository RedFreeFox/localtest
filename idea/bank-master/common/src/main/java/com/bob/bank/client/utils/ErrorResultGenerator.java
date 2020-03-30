package com.bob.bank.client.utils;

import com.bob.bank.client.constants.ErrorCodeEnum;
import com.bob.bank.client.result.PojoResult;

import static com.bob.bank.client.constants.ErrorCodeEnum.REST_REQUEST_ERROR;

/**
 * @author wb-jjb318191
 * @create 2018-10-23 16:58
 */
public abstract class ErrorResultGenerator {

    /**
     * 生成简单类型的结果
     *
     * @param error
     * @param t
     * @param <T>
     * @return
     */
    public static <T> PojoResult<T> generatePojoResult(ErrorCodeEnum error, T t) {
        PojoResult<T> result = new PojoResult<>(t);
        result.setErrorMessage(error.getCode(), error.getLabel());
        return result;
    }

}
