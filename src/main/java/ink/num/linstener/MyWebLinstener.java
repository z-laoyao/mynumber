package ink.num.linstener;

import ink.num.util.ContinueckRead;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;

/**
 * @WebListener 表示当前类是一个web监听器
 * @Component 表示当前类是一个spring的bean要注入spring容器中交由spring进行管理
 * implements ServletRequestListener  实现web监听器 实现具体监听事件
 *
 */
//@WebListener

//@Component
public class MyWebLinstener
        implements ServletRequestListener
{
//    @Override
    public void requestDestroyed(ServletRequestEvent servletRequestEvent) {

        System.out.println("应用程序销毁");

    }

//    @Override
    public void requestInitialized(ServletRequestEvent servletRequestEvent) {
        System.out.println("应用程序初始化");
        ContinueckRead.kaishi();

    }
}
