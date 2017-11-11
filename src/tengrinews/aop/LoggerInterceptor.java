package tengrinews.aop;

import java.lang.reflect.Method;
import java.util.Optional;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import org.jboss.logging.Logger;

import tengrinews.annotation.Loggable;

@Interceptor
public class LoggerInterceptor {
    
    Logger log = Logger.getLogger(getClass());
    
    @Resource
    SessionContext sessCtx;
    
    @AroundInvoke
    public Object intercept(InvocationContext ctx) throws Exception {
        Object obj = ctx.getTarget();
        Method method = ctx.getMethod();

        String username = Optional.ofNullable(sessCtx.getCallerPrincipal()).map(p -> p.getName()).orElse("unknown");
        String called = obj.getClass().getSimpleName() + "." + method.getName();
        
        
        Loggable isLoggable = obj.getClass().getAnnotation(Loggable.class);
        
        if (isLoggable != null && isLoggable.value()) {
            log.info(username + " -> " + called);
        }
        
        return ctx.proceed();
    }
}
