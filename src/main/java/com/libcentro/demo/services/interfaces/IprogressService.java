package com.libcentro.demo.services.interfaces;

import java.util.List;
import java.util.function.Consumer;

public interface IprogressService<T> {
    void ejecutarProceso( List<T> items, Consumer<T> task );
}
