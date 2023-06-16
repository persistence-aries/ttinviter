package com.social.ttinviter.mapper;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UsersMapper {
    /**
     * 搜尋登入者
     */
    List<Map<String, Object>> findByAccount(String account, String password);
    
    /**
     * 註冊
     */
    int insert(String name, Integer age, String account, String password, String email, String createBy);
    
    
}
