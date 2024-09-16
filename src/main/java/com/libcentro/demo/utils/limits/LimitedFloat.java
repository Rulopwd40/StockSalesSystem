package com.libcentro.demo.utils.limits;

public class LimitedFloat {
    final float min=0f;
    final float max=100f;

    LimitedFloat(float value) {
        if(value < min || value > max){
            throw new IllegalArgumentException("Value out of range");
        }
    }
}
