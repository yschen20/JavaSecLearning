package com.example.demo.Dog;

import java.lang.reflect.Method;

public interface DogModel
{
    int getId();

    void setId(int paramInt);

    String getName();

    default Object wagTail(Object input, String methodName, Class[] paramTypes, Object[] args) {
        try {
            Class<?> cls = input.getClass();
            Method method = cls.getMethod(methodName, paramTypes);
            return method.invoke(input, args);
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }

    String getBreed();

    int getAge();

    void feed();
}
