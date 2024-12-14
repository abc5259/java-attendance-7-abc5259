package attendance.controller;

import attendance.converter.Converter;
import java.util.function.Supplier;

public class InputTemplate {
    public <T> T execute(Supplier<String> inputSupplier, Converter<String, T> converter) {
        try {
            String input = inputSupplier.get();
            return converter.convert(input);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("[ERROR] " + e.getMessage());
        }
    }
}
