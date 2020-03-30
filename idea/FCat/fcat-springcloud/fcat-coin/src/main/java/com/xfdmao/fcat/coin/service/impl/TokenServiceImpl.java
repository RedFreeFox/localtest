package com.xfdmao.fcat.coin.service.impl;

import com.xfdmao.fcat.coin.entity.Token;
import com.xfdmao.fcat.coin.mapper.TokenMapper;
import com.xfdmao.fcat.coin.service.TokenService;
import com.xfdmao.fcat.common.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

/**
 * Created by fier on 2018/09/20
 */
@Service
public class TokenServiceImpl extends BaseServiceImpl<TokenMapper,Token> implements TokenService {
}
