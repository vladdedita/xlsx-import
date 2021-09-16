package com.assignment.xlsx.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ExcelRangeValidator.class)
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidExcelRange {
    String message() default "Please provide a valid excel range (Ex. A1:D20)";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
