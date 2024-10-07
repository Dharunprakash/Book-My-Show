package com.bms.bms.utils.validator;

public interface Validator<T> {
    void validate(T value) throws Exception;
}