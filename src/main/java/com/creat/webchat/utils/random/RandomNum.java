package com.creat.webchat.utils.random;

import java.util.Random;

/**
 * Created by whz on 2017/9/21.
 */
public class RandomNum {

    /**
     * 生成制定个数的随机数
     * @param count
     * @return 随机字符串
     */
    public String getNum(int count){
        StringBuilder result = new StringBuilder();
        Random random = new Random();
        for(int i = 0; i < count; i++){
            result.append(String.valueOf(random.nextInt(9)));
        }
        return result.toString();
    }
}
