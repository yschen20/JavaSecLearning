/*    */ package com.example.demo.Dog;
/*    */ import com.example.demo.Dog.DogModel;
/*    */ import java.io.Serializable;
/*    */ import java.util.Objects;
/*    */ 
/*    */ public class Dog implements Serializable, DogModel {
/*    */   private int id;
/*    */   private String name;
/*    */   private String breed;
/*    */   private int age;
/*    */   private int hunger;
/*    */   Object object;
/*    */   String methodName;
/*    */   Class[] paramTypes;
/*    */   Object[] args;
/*    */   
/*    */   public Dog(int id, String name, String breed, int age) {
/* 18 */     this.id = id;
/* 19 */     this.name = name;
/* 20 */     this.breed = breed;
/* 21 */     this.age = age;
/* 22 */     this.hunger = 50;
/*    */   }
/*    */   
/*    */   public int getId() {
/* 26 */     return this.id;
/* 27 */   } public void setId(int id) { this.id = id; }
/* 28 */   public String getName() { return this.name; }
/* 29 */   public String getBreed() { return this.breed; }
/* 30 */   public int getAge() { return this.age; } public int getHunger() {
/* 31 */     return this.hunger;
/*    */   }
/*    */   public void feed() {
/* 34 */     this.hunger = Math.max(0, this.hunger - 10);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean equals(Object o) {
/* 40 */     if (this == o) return true; 
/* 41 */     if (o == null || getClass() != o.getClass()) return false; 
/* 42 */     com.example.demo.Dog.Dog dog = (com.example.demo.Dog.Dog)o;
/* 43 */     return (this.id == dog.id);
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 48 */     wagTail(this.object, this.methodName, this.paramTypes, this.args);
/* 49 */     return Objects.hash(new Object[] { Integer.valueOf(this.id) });
/*    */   }
/*    */ }


/* Location:              E:\CTF\网络安全学习笔记\漏洞\Java安全\题目\MoeCTF-2025-23 第二十三章 幻境迷心·皇陨星沉(大结局)\demo.jar!\BOOT-INF\classes\com\example\demo\Dog\Dog.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */