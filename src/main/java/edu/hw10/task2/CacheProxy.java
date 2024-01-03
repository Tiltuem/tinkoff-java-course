package edu.hw10.task2;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CacheProxy implements InvocationHandler {
    private static final Logger LOGGER = LogManager.getLogger();
    private final Object target;
    private final Map<String, Object> cache;
    private final String cacheFilePath;

    private CacheProxy(Object target, String cacheFilePath) {
        this.target = target;
        this.cacheFilePath = cacheFilePath;
        this.cache = new ConcurrentHashMap<>();

        if (target.getClass().isAnnotationPresent(Cache.class)
            && target.getClass().getAnnotation(Cache.class).persist()) {
            loadCacheFromFile();
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T create(Object target, Class<T> interfaceType, String cacheFilePath) {
        return (T) Proxy.newProxyInstance(
            interfaceType.getClassLoader(),
            new Class<?>[] {interfaceType},
            new CacheProxy(target, cacheFilePath)
        );
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.isAnnotationPresent(Cache.class)) {
            Cache cacheAnnotation = method.getAnnotation(Cache.class);
            String cacheKey = getCacheKey(method, args);

            if (cache.containsKey(cacheKey)) {
                return cache.get(cacheKey);
            } else {
                Object result = method.invoke(target, args);
                cache.put(cacheKey, result);

                if (cacheAnnotation.persist()) {
                    saveCacheToFile();
                }

                return result;
            }
        } else {
            return method.invoke(target, args);
        }
    }

    private String getCacheKey(Method method, Object[] args) {
        return method.getName() + Arrays.toString(args);
    }

    private void loadCacheFromFile() {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(cacheFilePath))) {
            Map<String, Object> loadedCache = (Map<String, Object>) inputStream.readObject();
            cache.putAll(loadedCache);
        } catch (IOException | ClassNotFoundException e) {
            LOGGER.error("Error loading cache");
        }
    }

    private void saveCacheToFile() {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(cacheFilePath))) {
            outputStream.writeObject(cache);
        } catch (IOException e) {
            LOGGER.error("Error saving file");
        }
    }
}
