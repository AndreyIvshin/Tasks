package com.epam.clothshop.mapper;

import lombok.Data;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

@Data
@Component
public class MapperInitializer implements BeanFactoryPostProcessor {

    private String basePackage = "com.epam.clothshop.mapper";

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false) {
            @Override
            protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
                return super.isCandidateComponent(beanDefinition) || beanDefinition.getMetadata().isAbstract();
            }
        };
        provider.addIncludeFilter(new AnnotationTypeFilter(Mapper.class));
        provider.findCandidateComponents(basePackage).forEach(beanDefinition ->
                configurableListableBeanFactory.registerSingleton(beanDefinition.getBeanClassName(), init(beanDefinition.getBeanClassName())));
        System.out.println("hey!");
    }

    public Object init(String mapperClassName) throws BeansException {
        try {
            return Enhancer.create(Class.forName(mapperClassName), new MethodInterceptor() {
                @Override
                public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                    if (!method.isAnnotationPresent(Map.class)) {
                        return methodProxy.invokeSuper(o, objects);
                    } else {
                        if (method.getAnnotation(Map.class).value().equals(method.getReturnType())) {
                            return mapEntityToTransfer(objects[0], method.getReturnType().newInstance());
                        } else {
                            return mapTransferToEntity(objects[0], method.getReturnType().newInstance());
                        }
                    }
                }
            });
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Object mapEntityToTransfer(Object entity, Object transfer) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        for (Field field : transfer.getClass().getDeclaredFields()) {
            String name = field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
            String setterName = "set" + name;
            String getterName = "get" + name;
            Method setter = Arrays.stream(transfer.getClass().getMethods()).filter(
                    method -> method.getName().equals(setterName)).findAny().orElseThrow(NoSuchMethodException::new);
            Method getter = Arrays.stream(entity.getClass().getMethods()).filter(
                    method -> method.getName().equals(getterName)).findAny().orElseThrow(NoSuchMethodException::new);
            setter.invoke(transfer, getter.invoke(entity));
        }
        return transfer;
    }

    private Object mapTransferToEntity(Object transfer, Object entity) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        for (Field field : transfer.getClass().getDeclaredFields()) {
            String name = field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
            String setterName = "set" + name;
            String getterName = "get" + name;
            Method setter = Arrays.stream(entity.getClass().getMethods()).filter(
                    method -> method.getName().equals(setterName)).findAny().orElseThrow(NoSuchMethodException::new);
            Method getter = Arrays.stream(transfer.getClass().getMethods()).filter(
                    method -> method.getName().equals(getterName)).findAny().orElseThrow(NoSuchMethodException::new);
            setter.invoke(entity, getter.invoke(transfer));
        }
        return entity;
    }
}
