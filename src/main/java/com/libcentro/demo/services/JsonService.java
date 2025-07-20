package com.libcentro.demo.services;

import com.google.gson.Gson;
import com.libcentro.demo.services.interfaces.IjsonService;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

@Service
public class JsonService<T> implements IjsonService<T> {

    Gson gson;
    public JsonService() {
        this.gson = new Gson();
    }

    @Override
    public T loadFromFile ( String filePath, Class<T> clazz ) throws FileNotFoundException{
        try (FileReader reader = new FileReader (filePath)) {
            T obj = gson.fromJson(reader, clazz);
            System.out.println("Leído: " + obj); // Mostrará el contenido parseado
            return obj;
        } catch (IOException e) {
            throw new RuntimeException (e);
        }

    };

    @Override
    public void saveToFile ( String filePath, T obj ) throws IOException{
        try (FileWriter writer = new FileWriter (filePath)) {
            gson.toJson(obj, writer);
        }
    }

}
