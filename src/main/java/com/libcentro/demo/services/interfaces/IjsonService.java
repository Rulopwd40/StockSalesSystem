package com.libcentro.demo.services.interfaces;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface IjsonService<T> {
    T loadFromFile( String filePath, Class<T> clazz ) throws FileNotFoundException;

    void saveToFile( String filePath, T obj ) throws IOException;
}
