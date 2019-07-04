package com.wbd.spring.security.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 1.自定义注解声明
 * @author zgh
 *
 */

/**
 * @Retention
   SOURCE, 注释只在源代码级别保留，编译时被忽略
   CLASS, 注释将被编译器在类文件中记录但在运行时不需要JVM保留。这是默认的行为.
   RUNTIME, 注释将被编译器记录在类文件中在运行时保留VM，因此可以反读。
    @see java.lang.reflect.AnnotatedElement
     
 **/

/**
 * @Target({ElementType.TYPE}) 注解
 * ElementType 这个枚举类型的常量提供了一个简单的分类：
 * 注释可能出现在Java程序中的语法位置（这些常量与元注释类型(@Target)一起指定在何处写入注释的合法位置）
 */


@Documented
@Retention(RetentionPolicy.RUNTIME)//注解策略，运行时使用
@Target(ElementType.METHOD) //注解用着方法上
@Inherited //标注了自定义注解的类， 那么目标类的子类方法也拥有父类方法注解功能
public @interface CustomAnnotation {

	String requestUrl();
}
