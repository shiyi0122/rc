package com.hna.hka.archive.management.system.util;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.ValueFilter;

import java.util.Date;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.system.util
 * @ClassName: JsonUtils
 * @Author: 郭凯
 * @Description: json工具类
 * @Date: 2020/5/20 14:10
 * @Version: 1.0
 */
public class JsonUtils {

    public static String toString(Object obj){
        return JSON.toJSONString(obj, new ValueFilter() {
            /**
             * 将long 转换为字符串（解决id过长，页面解析错误问题）
             */
            @Override
            public Object process(Object object, String name, Object value) {
                Object strValue = value;
                if(value instanceof Long){
                    strValue = value.toString();
                }else if(value instanceof Date){
                    strValue = ((Date)value).getTime();
                }
                return strValue;
            }
        });

    }

}