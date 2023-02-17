package com.sandro.basic.scan.filter;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.context.annotation.ComponentScan.*;
import static org.springframework.context.annotation.FilterType.*;


public class ComponentFilterAppConfigTest {

    @Test
    void filterScan() throws Exception {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(ComponentFilterAppConfig.class);
        String[] beanDefinitionNames = ac.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            BeanDefinition beanDefinition = ac.getBeanDefinition(beanDefinitionName);
            if (beanDefinition.getRole() == BeanDefinition.ROLE_APPLICATION) {
                System.out.println(beanDefinitionName);
            }
        }
        assertThatCode(() -> ac.getBean("beanA", BeanA.class))
                .doesNotThrowAnyException();
        assertThatThrownBy(() -> ac.getBean("beanB", BeanB.class))
                .isInstanceOf(NoSuchBeanDefinitionException.class);
    }

    @ComponentScan(
            includeFilters = @Filter(MyIncludeComponent.class),
            excludeFilters = @Filter(MyExcludeComponent.class)
    )
    static class ComponentFilterAppConfig {}
}
