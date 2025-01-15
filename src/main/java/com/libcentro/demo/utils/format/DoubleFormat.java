package com.libcentro.demo.utils.format;

import java.text.DecimalFormat;

public class DoubleFormat implements Format<Double> {

    private final DecimalFormat df = new DecimalFormat("#.##");

    @Override
    public String format ( Double data){
        return df.format(data);
    }
}
