package com.example.cache.config;

import com.example.cache.entity.Student;
import org.springframework.cache.interceptor.KeyGenerator;

import java.lang.reflect.Method;

/**
 * Cache-键值生成器
 *
 * @className: KeyGeneratorConfig
 * @description
 * @author: luffy
 * @date: 2020/4/29 23:53
 * @version:V1.0
 */
public class StudentCacheKeyGenerator implements KeyGenerator {
    @Override
    public Object generate(Object o, Method method, Object... objects) {
        StringBuilder key = new StringBuilder(CacheConst.CACHE_STUDENT_NAME + ":");
        if (objects.length > 0) {
            for (Object object : objects) {
                if(object instanceof Student){
                    key.append(((Student) object).getId());
                }else {
                    key.append(object.toString());
                }
                break;
            }
        }
        return key.toString();
    }
}
