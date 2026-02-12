/*    */ package com.example.demo.Dog;
/*    */ 
/*    */ import java.lang.reflect.Method;
/*    */ 
/*    */ public interface DogModel
/*    */ {
/*    */   int getId();
/*    */   
/*    */   void setId(int paramInt);
/*    */   
/*    */   String getName();
/*    */   
/*    */   default Object wagTail(Object input, String methodName, Class[] paramTypes, Object[] args) {
/*    */     try {
/* 15 */       Class<?> cls = input.getClass();
/* 16 */       Method method = cls.getMethod(methodName, paramTypes);
/* 17 */       return method.invoke(input, args);
/* 18 */     } catch (Exception e) {
/* 19 */       e.printStackTrace();
/*    */       
/* 21 */       return null;
/*    */     } 
/*    */   }
/*    */   
/*    */   String getBreed();
/*    */   
/*    */   int getAge();
/*    */   
/*    */   void feed();
/*    */ }


/* Location:              E:\CTF\网络安全学习笔记\漏洞\Java安全\题目\MoeCTF-2025-23 第二十三章 幻境迷心·皇陨星沉(大结局)\demo.jar!\BOOT-INF\classes\com\example\demo\Dog\DogModel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */