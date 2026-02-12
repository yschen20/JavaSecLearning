package com.example.demo.Dog;
import com.example.demo.Dog.Dog;
import org.springframework.stereotype.Service;
import java.io.*;

import java.util.*;

@Service
public class DogService implements Serializable {
    private Map<Integer, Dog> dogs = new HashMap<>();
    private int nextId = 1;

    public List<Dog> getAllDogs() {
        return new ArrayList<>(this.dogs.values());
    }

    public Dog addDog(String name, String breed, int age) {
        Dog dog = new Dog(this.nextId++, name, breed, age);
        this.dogs.put(Integer.valueOf(dog.getId()), dog);
        return dog;
    }

    public Dog feedDog(int id) {
        Dog dog = this.dogs.get(Integer.valueOf(id));
        if (dog != null) {
            dog.feed();
        }
        return dog;
    }

    public Dog removeDog(int id) {
        return this.dogs.remove(Integer.valueOf(id));
    }
    public Object chainWagTail() {
        Object input = null;
        for (Dog dog : this.dogs.values()) {
            if (input == null) {
                input = dog.object;
            }
            Object result = dog.wagTail(input, dog.methodName, dog.paramTypes, dog.args);
            input = result;
        }
        return input;
    }

    public String exportDogsBase64() {
        try(ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            oos.writeObject(new ArrayList(this.dogs.values()));
            oos.flush();
            return Base64.getEncoder().encodeToString(baos.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    public void importDogsBase64(String base64Data) {
        try(ByteArrayInputStream bais = new ByteArrayInputStream(Base64.getDecoder().decode(base64Data));
            ObjectInputStream ois = new ObjectInputStream(bais)) {
            Collection<Dog> imported = (Collection<Dog>)ois.readObject();
            for (Dog dog : imported) {
                dog.setId(this.nextId++);
                this.dogs.put(Integer.valueOf(dog.getId()), dog);
            }
        } catch (IOException|ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
