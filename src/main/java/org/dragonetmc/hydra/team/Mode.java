package org.dragonetmc.hydra.team;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Mode {

    ModeType value();

    int min() default 1;

    int max() default 1;

    int amount() default 1;
}
