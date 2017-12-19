package com.jimo.test.apt;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by jimo on 17-8-27.
 * 注解会被编译器丢掉
 */
@Retention(RetentionPolicy.SOURCE)
//@Target(ElementType.TYPE)
public @interface ExtractInterface {
    public String value();
}
