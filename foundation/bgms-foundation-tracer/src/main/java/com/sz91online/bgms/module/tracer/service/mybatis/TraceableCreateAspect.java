package com.sz91online.bgms.module.tracer.service.mybatis;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.beanutils.PropertyUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.Advised;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sz91online.bgms.module.tracer.domain.Tracer;
import com.sz91online.bgms.module.tracer.service.TracerService;
import com.sz91online.bgms.module.tracer.util.ClassInfoMap;
import com.sz91online.bgms.module.tracer.util.Traceable;
import com.sz91online.bgms.module.tracer.util.TracerConstants;
import com.sz91online.common.utils.PlDateTime;

@Aspect
@Component
public class TraceableCreateAspect {
    private static final Logger LOG = LoggerFactory.getLogger(TraceableCreateAspect.class);

    @Autowired
    private TracerService tracerService;

    @AfterReturning("execution(public * com.sz91online.**..*.saveWithSession(..)) && args(bean, username)")
    public void traceSaveActivity(JoinPoint joinPoint, Object bean, String username) {
        Advised advised = (Advised) joinPoint.getThis();
        Class<?> cls = advised.getTargetSource().getTargetClass();

        Traceable traceableAnnotation = cls.getAnnotation(Traceable.class);
        if (traceableAnnotation != null) {
            try {
                Tracer activity = constructActivity(cls, traceableAnnotation, bean, username,
                        TracerConstants.ACTION_CREATE);
                tracerService.save(activity);
            } catch (Exception e) {
                LOG.error("Error when save activity for save action of service " + cls.getName(), e);
            }
        }
    }

    @AfterReturning("execution(public * com.sz91online.**..*.removeWithSession(..)) && args(bean, username)")
    public void traceDeleteActivity(JoinPoint joinPoint, Object bean, String username) {
        Advised advised = (Advised) joinPoint.getThis();
        Class<?> cls = advised.getTargetSource().getTargetClass();

        Traceable traceableAnnotation = cls.getAnnotation(Traceable.class);
        if (traceableAnnotation != null) {
            try {
                Tracer activity = constructActivity(cls, traceableAnnotation, bean, username,
                        TracerConstants.ACTION_DELETE);
                tracerService.save(activity);
            } catch (Exception e) {
                LOG.error("Error when save activity for save action of service " + cls.getName(), e);
            }
        }
    }

    static Tracer constructActivity(Class<?> cls, Traceable traceableAnnotation, Object bean, String username, String action)
            throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        Tracer activity = new Tracer();
        activity.setModule(ClassInfoMap.getModule(cls));
        activity.setSubModule(ClassInfoMap.getType(cls));
        activity.setBusinessId((Long)(PropertyUtils.getProperty(bean, traceableAnnotation.idField())));
        activity.setCreatedTime(new GregorianCalendar().getTime());
        activity.setAction(action);
        activity.setCreatedUser(username);

        Object nameObj = PropertyUtils.getProperty(bean, traceableAnnotation.nameField());
        String nameField;
        if (nameObj instanceof Date) {
            nameField = PlDateTime.formatDate((Date) nameObj, PlDateTime.FORMAT_FUL);
        } else {
            nameField = nameObj.toString();
        }
        activity.setContent(nameField);

        if (!"".equals(traceableAnnotation.extraFieldName())) {
            Long extraTypeId = (Long) PropertyUtils.getProperty(bean, traceableAnnotation.extraFieldName());
            activity.setExtraId(extraTypeId);
        }
        return activity;
    }
}