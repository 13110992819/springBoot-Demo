package com.example.demo;

import com.alibaba.fastjson.JSONObject;

/**
 * 应用模块名称<p>
 * 代码描述<p>
 * Company: 逍邦网络科技有限公司<p>
 *
 * @author chenshan
 * @version v1.0
 * @since 2021/3/1 17:13
 */
public class Parent {
    public static void main(String[] args) {
        Class<?>[] teamClass = MainTestClass.class.getClasses();
        System.out.println(JSONObject.toJSON(teamClass));
    }
}
