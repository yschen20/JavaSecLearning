/*    */ package com.example.demo.Dog;
/*    */ import com.example.demo.Dog.Dog;
import org.springframework.stereotype.Service;
/*    */ import java.io.*;
/*    */
/*    */
/*    */
/*    */ import java.util.*;

/*    */
/*    */ @Service
/*    */ public class DogService implements Serializable {
/* 11 */   private Map<Integer, Dog> dogs = new HashMap<>();
/* 12 */   private int nextId = 1;
/*    */ 
/*    */   
/*    */   public List<Dog> getAllDogs() {
/* 16 */     return new ArrayList<>(this.dogs.values());
/*    */   }
/*    */ 
/*    */   
/*    */   public Dog addDog(String name, String breed, int age) {
/* 21 */     Dog dog = new Dog(this.nextId++, name, breed, age);
/* 22 */     this.dogs.put(Integer.valueOf(dog.getId()), dog);
/* 23 */     return dog;
/*    */   }
/*    */ 
/*    */   
/*    */   public Dog feedDog(int id) {
/* 28 */     Dog dog = this.dogs.get(Integer.valueOf(id));
/* 29 */     if (dog != null) {
/* 30 */       dog.feed();
/*    */     }
/* 32 */     return dog;
/*    */   }
/*    */ 
/*    */   
/*    */   public Dog removeDog(int id) {
/* 37 */     return this.dogs.remove(Integer.valueOf(id));
/*    */   }
/*    */   public Object chainWagTail() {
/* 40 */     Object input = null;
/* 41 */     for (Dog dog : this.dogs.values()) {
/* 42 */       if (input == null) {
/* 43 */         input = dog.object;
/*    */       }
/* 45 */       Object result = dog.wagTail(input, dog.methodName, dog.paramTypes, dog.args);
/* 46 */       input = result;
/*    */     } 
/* 48 */     return input;
/*    */   }
/*    */ 
/*    */   
/*    */   public String exportDogsBase64() {
/* 53 */     try(ByteArrayOutputStream baos = new ByteArrayOutputStream(); 
/* 54 */         ObjectOutputStream oos = new ObjectOutputStream(baos)) {
/* 55 */       oos.writeObject(new ArrayList(this.dogs.values()));
/* 56 */       oos.flush();
/* 57 */       return Base64.getEncoder().encodeToString(baos.toByteArray());
/* 58 */     } catch (IOException e) {
/* 59 */       e.printStackTrace();
/* 60 */       return "";
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void importDogsBase64(String base64Data) {
/* 67 */     try(ByteArrayInputStream bais = new ByteArrayInputStream(Base64.getDecoder().decode(base64Data)); 
/* 68 */         ObjectInputStream ois = new ObjectInputStream(bais)) {
/* 69 */       Collection<Dog> imported = (Collection<Dog>)ois.readObject();
/* 70 */       for (Dog dog : imported) {
/* 71 */         dog.setId(this.nextId++);
/* 72 */         this.dogs.put(Integer.valueOf(dog.getId()), dog);
/*    */       } 
/* 74 */     } catch (IOException|ClassNotFoundException e) {
/* 75 */       e.printStackTrace();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              E:\CTF\网络安全学习笔记\漏洞\Java安全\题目\MoeCTF-2025-23 第二十三章 幻境迷心·皇陨星沉(大结局)\demo.jar!\BOOT-INF\classes\com\example\demo\Dog\DogService.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */