package com.secondhand.common;

import java.util.Random;

/**
 * 6位随机数字验证码
 * Created by LeiMing on 2020/2/13 19:23
 */
public class CodeUtils {

    public static String getRandomCode(){
        StringBuilder code = new StringBuilder();
        int[] array = {0,1,2,3,4,5,6,7,8,9};
        Random rand = new Random();
        for (int i = 10; i > 1; i--) {
            int index = rand.nextInt(i);
            int tmp = array[index];
            array[index] = array[i - 1];
            array[i - 1] = tmp;
        }
        int result = 0;
        for(int i = 0; i < 6; i++){
            code.append(array[i]);
        }
        return code.toString();
    }


}
