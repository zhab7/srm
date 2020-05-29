package com.jyzt.srm.common.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.*;


@Slf4j
public class SerializeUtils {

    // 1 KB
    private static final int buffer_size = 1024;


    public static byte[] serialize(Object object) {
        byte[] result;
        if (object == null) {
            return new byte[0];
        }
        if (!(object instanceof Serializable)) {
            throw new IllegalArgumentException(
                    SerializeUtils.class.getSimpleName() + " requires a Serializable payload "
                            + "but received an object of type [" + object.getClass().getName() + "]");
        }
        try {
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream(buffer_size);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteStream);
            objectOutputStream.writeObject(object);
            objectOutputStream.flush();
            result = byteStream.toByteArray();
        } catch (Exception ex) {
            log.error("", ex);
            // fixme 异常处理
            throw new RuntimeException("Failed to serialize");
        }
        return result;
    }

    public static Object deserialize(byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        try {
            ByteArrayInputStream byteStream = new ByteArrayInputStream(bytes);
            ObjectInputStream objectInputStream = new ObjectInputStream(byteStream);
            return objectInputStream.readObject();
        } catch (Exception e) {
            log.error("", e);
            // fixme 异常处理
            throw new RuntimeException("Failed to deserialize!");
        }
    }
}
