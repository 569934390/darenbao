package com.compses.service.api.token;

import com.compses.util.QiNiuUtil;
import org.springframework.stereotype.Service;

/**
 * Created by jocelynsuebb on 16/7/26.
 */
@Service
public class TokenService {


    public String getQiNiuUploadToken(){
        String token = QiNiuUtil.getQiNiuUploadToken();
        return token;
    }
}
