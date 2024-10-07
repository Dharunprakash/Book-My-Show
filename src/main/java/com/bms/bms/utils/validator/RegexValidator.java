package com.bms.bms.utils.validator;

public class RegexValidator implements Validator<String> {
    private final String regex;
    private final String message;

    public RegexValidator(String regex, String message) {
        this.regex = regex;
        this.message = message;
    }

    @Override
    public void validate(String value) throws Exception {
        if (!value.matches(regex)) {
            throw new Exception(message);
        }
    }
}