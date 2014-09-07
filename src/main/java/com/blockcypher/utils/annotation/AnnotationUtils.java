package com.blockcypher.utils.annotation;

import org.apache.log4j.Logger;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Util to read @Path annotation on a class
 * @author <a href="mailto:seb.auvray@gmail.com">Sebastien Auvray</a>
 */
public class AnnotationUtils {

    private static final Logger logger = Logger.getLogger(AnnotationUtils.class);

    /**
     * Get the @Path of a class
     * @param transactionClass
     * @return
     */
    public static String getPath(Class transactionClass) {
        for (Annotation annotation : transactionClass.getAnnotations()) {
            Class<Annotation> type = (Class<Annotation>) annotation.annotationType();
            for (Method method : type.getDeclaredMethods()) {
                if ("javax.ws.rs.Path".equals(type.getName()) && "value".equals(method.getName())) {
                    try {
                        return (String) method.invoke(annotation);
                    } catch (IllegalAccessException e) {
                        logger.error("Error while reading @Path", e);
                    } catch (InvocationTargetException e) {
                        logger.error("Error while reading @Path", e);
                    }
                }
            }
        }
        return null;
    }

}
