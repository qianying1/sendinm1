package com.gzsendi.sendinm.mapper;

import com.gzsendi.sendinm.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Mapper
@Repository("userMapper")
public interface UserMapper {
//    int deleteByPrimaryKey(Integer userId);

    @Transactional
    int insert(User record);

//    int insertSelective(User record);

//    User selectByPrimaryKey(Integer userId);

//    int updateByPrimaryKeySelective(User record);

//    int updateByPrimaryKey(User record);
}