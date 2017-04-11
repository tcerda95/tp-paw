package tp.paw.khet.webapp.form.constraints;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ FIELD, METHOD, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = NoDuplicateValidator.class)
@Documented
public @interface NoDuplicateVideos {
    String message() default "{tp.paw.khet.webapp.form.constraints.NoDuplicateVideos.message}";
    
    Class<?>[] groups() default {};
    
    Class<? extends Payload>[] payload() default {};    
}
