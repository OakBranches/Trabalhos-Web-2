package br.ufscar.dc.dsw1.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Constraint(validatedBy = MultipartValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Multipart {
    String message() default "CNPJ is already registered";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}
