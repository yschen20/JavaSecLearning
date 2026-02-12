/*    */ package com.example.demo.controller;
/*    */ import com.example.demo.Dog.Dog;
/*    */ import com.example.demo.Dog.DogService;
/*    */ import org.springframework.web.bind.annotation.*;

import java.util.List;
/*    */
/*    */
/*    */
/*    */

/*    */
/*    */ @RestController
/*    */ @RequestMapping({"/dogs"})
/*    */ public class DogController {
/*    */   public DogController(DogService dogService) {
/* 14 */     this.dogService = dogService;
/*    */   }
/*    */   private final DogService dogService;
/*    */   
/*    */   @GetMapping
/*    */   public List<Dog> getAllDogs() {
/* 20 */     return this.dogService.getAllDogs();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @PostMapping
/*    */   public Dog addDog(@RequestParam String name, @RequestParam String breed, @RequestParam int age) {
/* 28 */     return this.dogService.addDog(name, breed, age);
/*    */   }
/*    */ 
/*    */   
/*    */   @PostMapping({"/{id}/feed"})
/*    */   public Dog feedDog(@PathVariable int id) {
/* 34 */     return this.dogService.feedDog(id);
/*    */   }
/*    */ 
/*    */   
/*    */   @DeleteMapping({"/{id}"})
/*    */   public Dog removeDog(@PathVariable int id) {
/* 40 */     return this.dogService.removeDog(id);
/*    */   }
/*    */   
/*    */   @GetMapping({"/export"})
/*    */   public String exportDogs() {
/* 45 */     return this.dogService.exportDogsBase64();
/*    */   }
/*    */ 
/*    */   
/*    */   @PostMapping({"/import"})
/*    */   public String importDogs(@RequestParam("data") String base64Data) {
/* 51 */     this.dogService.importDogsBase64(base64Data);
/* 52 */     return "导入成功！";
/*    */   }
/*    */ }


/* Location:              E:\CTF\网络安全学习笔记\漏洞\Java安全\题目\MoeCTF-2025-23 第二十三章 幻境迷心·皇陨星沉(大结局)\demo.jar!\BOOT-INF\classes\com\example\demo\controller\DogController.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */