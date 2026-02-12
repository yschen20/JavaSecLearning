package com.example.demo.Dog;
import com.example.demo.Dog.DogModel;
import java.io.Serializable;
import java.util.Objects;

public class Dog implements Serializable, DogModel {
    private int id;
    private String name;
    private String breed;
    private int age;
    private int hunger;
    Object object;
    String methodName;
    Class[] paramTypes;
    Object[] args;

    public Dog(int id, String name, String breed, int age) {
        this.id = id;
        this.name = name;
        this.breed = breed;
        this.age = age;
        this.hunger = 50;
    }

    public int getId() {
        return this.id;
    } public void setId(int id) { this.id = id; }
    public String getName() { return this.name; }
    public String getBreed() { return this.breed; }
    public int getAge() { return this.age; } public int getHunger() {
        return this.hunger;
    }
    public void feed() {
        this.hunger = Math.max(0, this.hunger - 10);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        com.example.demo.Dog.Dog dog = (com.example.demo.Dog.Dog)o;
        return (this.id == dog.id);
    }

    public int hashCode() {
        wagTail(this.object, this.methodName, this.paramTypes, this.args);
        return Objects.hash(new Object[] { Integer.valueOf(this.id) });
    }
}