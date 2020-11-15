package com.epam.clothshop.mapper;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class MapperInitializer implements BeanFactoryPostProcessor, ApplicationContextAware {

    private String basePackage = "com.epam.clothshop.mapper";
    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

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
    }

    public Object init(String mapperClassName) throws BeansException {
        try {
            return Enhancer.create(Class.forName(mapperClassName), new MethodInterceptor() {
                @Override
                public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                    if (Arrays.asList(Object.class.getMethods()).contains(method)) {
                        return methodProxy.invokeSuper(o, objects);
                    } else {
                        return map(method.getReturnType().newInstance(), objects[0]);
                    }
                }
            });
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Object map(Object returnObject, Object paramObject) throws Throwable {
        Object transfer = null;
        Object entity = null;
        if (paramObject.getClass().getDeclaringClass() != null && paramObject.getClass().getDeclaringClass().isAnnotationPresent(Mapper.class)) {
            transfer = paramObject;
            entity = returnObject;
            return mapTransferToEntity(transfer, entity);
        } else {
            if (returnObject.getClass().getDeclaringClass() != null && returnObject.getClass().getDeclaringClass().isAnnotationPresent(Mapper.class)) {
                transfer = returnObject;
                entity = paramObject;
                return mapEntityToTransfer(entity, transfer);
            } else {
                throw new IllegalArgumentException();
            }
        }
    }


    private Object mapEntityToTransfer(Object entity, Object transfer) throws Throwable {
        for (Field field : transfer.getClass().getDeclaredFields()) {
            String name = field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
            String setterName = "set" + name;
            String getterName = "get" + name;
            Method setter = Arrays.stream(transfer.getClass().getMethods()).filter(
                    method -> method.getName().equals(setterName)).findAny().orElseThrow(NoSuchMethodException::new);
            Method getter = Arrays.stream(entity.getClass().getMethods()).filter(
                    method -> method.getName().equals(getterName)).findAny().orElseThrow(NoSuchMethodException::new);
            if (!field.getType().equals(List.class)) {
                setObject(setter, transfer, getter, entity);
            } else {
                Field entityField = Arrays.stream(entity.getClass().getDeclaredFields()).filter(ef -> ef.getName().equals(field.getName()))
                        .findAny().orElseThrow(NoSuchFieldException::new);
                Class returnClass = (Class) ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0];
                Class paramClass = (Class) ((ParameterizedType) entityField.getGenericType()).getActualTypeArguments()[0];
                if (returnClass.getDeclaringClass() != null && returnClass.getDeclaringClass().isAnnotationPresent(Mapper.class)) {
                    setList(setter, transfer, getter, entity, returnClass, paramClass, applicationContext.getBean(returnClass.getDeclaringClass()));
                } else {
                    throw new IllegalArgumentException();
                }
            }
        }
        return transfer;
    }

    private Object mapTransferToEntity(Object transfer, Object entity) throws Throwable {
        for (Field field : transfer.getClass().getDeclaredFields()) {
            String name = field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
            String setterName = "set" + name;
            String getterName = "get" + name;
            Method setter = Arrays.stream(entity.getClass().getMethods()).filter(
                    method -> method.getName().equals(setterName)).findAny().orElseThrow(NoSuchMethodException::new);
            Method getter = Arrays.stream(transfer.getClass().getMethods()).filter(
                    method -> method.getName().equals(getterName)).findAny().orElseThrow(NoSuchMethodException::new);
            if (!field.getType().equals(List.class)) {
                setObject(setter, entity, getter, transfer);
            } else {
                Field entityField = Arrays.stream(entity.getClass().getDeclaredFields()).filter(ef -> ef.getName().equals(field.getName()))
                        .findAny().orElseThrow(NoSuchFieldException::new);
                Class returnClass = (Class) ((ParameterizedType) entityField.getGenericType()).getActualTypeArguments()[0];
                Class paramClass = (Class) ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0];
                if (paramClass.getDeclaringClass() != null && paramClass.getDeclaringClass().isAnnotationPresent(Mapper.class)) {
                    setList(setter, entity, getter, transfer, returnClass, paramClass, applicationContext.getBean(paramClass.getDeclaringClass()));
                } else {
                    throw new IllegalArgumentException();
                }
            }
        }
        return entity;
    }

    private void setObject(Method setter, Object setterObject, Method getter, Object getterObject) throws Throwable {
        setter.invoke(setterObject, getter.invoke(getterObject));
    }

    private void setList(Method setter, Object setterObject, Method getter, Object getterObject, Class returnClass, Class paramClass, Object mapper) throws Throwable {
        Method map = Arrays.stream(mapper.getClass().getMethods())
                .filter(method -> method.getReturnType().equals(returnClass) && method.getParameterTypes()[0].equals(paramClass))
                .findAny().orElseThrow(NoSuchMethodError::new);
        List list = new ArrayList();
        for (Object object : new ArrayList<Object>((List) getter.invoke(getterObject))) {
            list.add(map.invoke(mapper, object));
        }
        setter.invoke(setterObject, list);
    }
}
