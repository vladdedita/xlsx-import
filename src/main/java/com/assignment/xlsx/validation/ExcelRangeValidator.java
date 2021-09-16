package com.assignment.xlsx.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class ExcelRangeValidator implements ConstraintValidator<ValidExcelRange, String> {

    @Override
    public void initialize(ValidExcelRange constraintAnnotation) {
        //intentionally left empty
    }

    @Override
    public boolean isValid(String range, ConstraintValidatorContext constraintValidatorContext) {
        Pattern pattern = Pattern.compile("\\b[A-Z]+\\d+:[A-Z]+\\d+\\b");
        return pattern.matcher(range).matches();
    }
}
