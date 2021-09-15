package com.assignment.xlsx.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.Locale;

public class StringCurrencySerializer extends JsonSerializer<Double> {

    NumberFormat formatter = NumberFormat.getCurrencyInstance(Locale.US);

    @Override
    public void serialize(Double value, JsonGenerator generator, SerializerProvider provider) throws IOException {
        generator.writeString(formatter.format(value));
    }
}
