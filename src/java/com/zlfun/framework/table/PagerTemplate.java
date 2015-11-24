/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zlfun.framework.table;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * $page,$offset,$total,$size
 *
 * @author hubin
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface PagerTemplate {

    public String begin() default "<ul>";

    public String item() default "<li>${page}</li>";

    public String end() default "</ul>";

    public String dot() default "</ul>";

    public String current() default "<li>${page}</li>";

    public String next() default "上一页";

    public String back() default "上一页";
}
