package com.creat.webchat.dao;

import com.creat.webchat.mapper.UserAccountMapper;
import com.creat.webchat.mapper.UserInfoMapper;
import com.creat.webchat.po.UserAccount;
import com.creat.webchat.po.UserAccountExample;
import com.creat.webchat.po.UserInfo;
import com.creat.webchat.po.UserInfoExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by whz on 2017/10/19.
 */
@Repository
public class UserDAO {
    @Autowired
    private UserAccountMapper userAccountMapper;
    @Autowired
    private UserInfoMapper userInfoMapper;

    public UserInfo selectUserInfoByUserAccountId(Long userAccountId){
        UserInfoExample example = new UserInfoExample();
        UserInfoExample.Criteria criteria = example.createCriteria();
        criteria.andUserAccountIdEqualTo(userAccountId);
        List<UserInfo> userInfoList = userInfoMapper.selectByExample(example);
        if(userInfoList.size() > 0){
            return userInfoList.get(0);
        }else {
            return null;
        }
    }

    public void saveUserAccount(UserAccount userAccount){
        userAccountMapper.insert(userAccount);
    }

    public void saveUserInfo(UserInfo userInfo){
        userInfoMapper.insert(userInfo);
    }

    public UserInfo selectUserInfoByTel(String tel){
        UserInfoExample example = new UserInfoExample();
        UserInfoExample.Criteria criteria = example.createCriteria();
        criteria.andTelEqualTo(tel);
        List<UserInfo> userInfoList = userInfoMapper.selectByExample(example);
        if(userInfoList.size() > 0){
            return userInfoList.get(0);
        }else {
            return null;
        }
    }

    public UserAccount selectUserAccountByUserName(String userName){
        UserAccountExample example = new UserAccountExample();
        UserAccountExample.Criteria criteria = example.createCriteria();
        criteria.andUserNameEqualTo(userName);
        List<UserAccount> userAccount = userAccountMapper.selectByExample(example);
        if(userAccount.size() > 0){
            return userAccount.get(0);
        }else {
            return null;
        }
    }

}
