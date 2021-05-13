package ink.num.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class BeanUtil implements ApplicationContextAware {
    public static ApplicationContext applicationContext;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (BeanUtil.applicationContext == null){
            BeanUtil.applicationContext = applicationContext;
        }

    }

    //获取applicationContext
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }


    //通过class获取Bean.
    public static <T> T getBean(Class<T> clazz){
        return getApplicationContext().getBean(clazz);
    }


}
