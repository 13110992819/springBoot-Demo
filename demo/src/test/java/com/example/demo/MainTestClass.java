package com.example.demo;

import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 应用模块名称<p>
 * 代码描述<p>
 * Company: 逍邦网络科技有限公司<p>
 *
 * @author chenshan
 * @version v1.0
 * @since 2021/1/26 16:02
 */
public class MainTestClass {
    public static void main(String[] args) {
    
        Map<String,List<String>> strMap = new HashMap<>();
        strMap.computeIfAbsent("test",key->new ArrayList<>()).add("test1");
        System.out.println("map1："+JSONObject.toJSON(strMap));
        //会有空指针问题
        //strMap.putIfAbsent("test2",new ArrayList<>()).add("test2");
        //System.out.println("map2："+JSONObject.toJSON(strMap));
        Map<String,Integer> intMap = new HashMap<>();
        intMap.merge("test",1,Integer::sum);
        intMap.merge("test",1,Integer::compareTo);
//        List<Integer> nums = new ArrayList();
//        nums.add(1);
//        nums.add(7);
//        nums.add(3);
//        nums.add(6);
//        nums.add(5);
//        nums.add(6);
//        pivotIndex(nums);
    }
    public static int pivotIndex(List<Integer> nums) {
        int lastNum = 0;
        int leftNum = 0;
        int rightNum = 0;
        int length = Integer.valueOf((int) Math.floor(nums.size() / 2));
        Boolean isLeftContinue = true;
        Boolean isRightContinue = true;
        int i = 0;
        int j = 0;
        while (isLeftContinue) {
            leftNum += nums.get(i);
            if ( length <= i ) {
                if ( leftNum == rightNum ) {
                    lastNum = i;
                } else {
                    lastNum = -1;
                }
                break;
            }
            isRightContinue  = true;
            while (isRightContinue) {
                rightNum += nums.get(nums.size() - i - 1);
            }
        }
    
        return lastNum;
    }
    
    /**
     * 向上取整
     * @return
     */
    public double ceil(double num){
        return Math.ceil(num);
    }
    
    /**
     * 向下取整
     * @param num
     * @return
     */
    public double floor(double num){
        return Math.floor(num);
    }
    
    /**
     * 四舍五入
     * @param num
     * @return
     */
    public double round(double num){
        return Math.round(num);
    }
    
    /**
     * 四舍五入
     * @param num
     * @return
     */
    public double round(float num){
        return Math.round(num);
    }
    
}
