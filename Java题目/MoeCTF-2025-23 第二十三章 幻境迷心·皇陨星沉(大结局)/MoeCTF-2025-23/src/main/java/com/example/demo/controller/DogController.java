package com.example.demo.controller;
import com.example.demo.Dog.Dog;
import com.example.demo.Dog.DogService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping({"/dogs"})
public class DogController {
    public DogController(DogService dogService) {
        this.dogService = dogService;
    }
    private final DogService dogService;

    @GetMapping
    public List<Dog> getAllDogs() {
        return this.dogService.getAllDogs();
    }

    @PostMapping
    public Dog addDog(@RequestParam String name, @RequestParam String breed, @RequestParam int age) {
        return this.dogService.addDog(name, breed, age);
    }

    @PostMapping({"/{id}/feed"})
    public Dog feedDog(@PathVariable int id) {
        return this.dogService.feedDog(id);
    }

    @DeleteMapping({"/{id}"})
    public Dog removeDog(@PathVariable int id) {
        return this.dogService.removeDog(id);
    }

    @GetMapping({"/export"})
    public String exportDogs() {
        return this.dogService.exportDogsBase64();
    }

    @PostMapping({"/import"})
    public String importDogs(@RequestParam("data") String base64Data) {
        this.dogService.importDogsBase64(base64Data);
        return "导入成功！";
    }
}
