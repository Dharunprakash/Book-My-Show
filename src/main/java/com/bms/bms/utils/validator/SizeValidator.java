package com.bms.bms.utils.validator;

public class SizeValidator<T> implements Validator<T> {
    private final int min;
    private final int max;
    private final String message;

    public SizeValidator(int min, int max, String message) {
        this.min = min;
        this.max = max;
        this.message = message;
    }

    @Override
    public void validate(T value) throws Exception {
        System.out.println("SizeValidator: " + value);
        switch (value) {
            case String str -> {
                if (str.length() < min || str.length() > max) {
                    throw new Exception(message);
                }
            }
            case Integer i -> {
                int num = (int) value;
                if (num < min || num > max) {
                    throw new Exception(message);
                }
            }
            case Long l -> {
                long num = (long) value;
                if (num < min || num > max) {
                    throw new Exception(message);
                }
            }
            case Double v -> {
                double num = (double) value;
                if (num < min || num > max) {
                    throw new Exception(message);
                }
            }
            case Float v -> {
                float num = (float) value;
                if (num < min || num > max) {
                    throw new Exception(message);
                }
            }
            case Byte b -> {
                byte num = (byte) value;
                if (num < min || num > max) {
                    throw new Exception(message);
                }
            }
            case Short i -> {
                short num = (short) value;
                if (num < min || num > max) {
                    throw new Exception(message);
                }
            }
            case Character c -> {
                char ch = (char) value;
                if (ch < min || ch > max) {
                    throw new Exception(message);
                }
            }
            case Object[] arr -> {
                if (arr.length < min || arr.length > max) {
                    throw new Exception(message);
                }
            }
            case Iterable iterable -> {
                int count = 0;
                for (Object obj : iterable) {
                    count++;
                }
                if (count < min || count > max) {
                    throw new Exception(message);
                }
            }
            case null, default -> throw new Exception("Unsupported type");
        }
    }
}