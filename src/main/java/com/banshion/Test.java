package com.banshion;

import com.banshion.portal.util.PasswordUtil;


public class Test {

    public static void main(String[] a){
        System.out.println(PasswordUtil.getEncodePassWord("admin","6042ef4277a7c58f".getBytes()));
    }

}
