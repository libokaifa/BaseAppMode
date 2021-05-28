package com.libo.base.autoservice;

import java.util.ServiceLoader;

/**
 * /**
 *
 * @Author libo
 * @create 2021/5/28 9:32 上午
 * @describe:
 */
public class LiboServiceLoader {

    public LiboServiceLoader() {
    }

    public static <T> T load(Class<T> service) {

        try {
            return ServiceLoader.load(service).iterator().next();
        } catch (Exception e) {
            return  null;
        }

    }
}
