package com.bms.bms.utils.validator;

public class NotNullValidator<T> implements Validator<T> {
    private final String message;

    public NotNullValidator(String message) {
        this.message = message;
    }

    @Override
    public void validate(T value) throws Exception {
        System.out.println("Validating not null"+value);
        if (value == null) {
            throw new Exception(message);
        }
    }
}