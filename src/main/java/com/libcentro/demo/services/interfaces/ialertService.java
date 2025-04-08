package com.libcentro.demo.services.interfaces;

import java.util.function.Supplier;

public interface ialertService {
    void executeWithDialog (
            Supplier<Boolean> command,
            Runnable onSuccess,
            Runnable onFailure
    );
}
