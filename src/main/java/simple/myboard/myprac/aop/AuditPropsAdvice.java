package simple.myboard.myprac.aop;

import org.aopalliance.intercept.MethodInvocation;
import org.aopalliance.intercept.MethodInterceptor;
import simple.myboard.myprac.vo.AuditProps;


public class AuditPropsAdvice implements MethodInterceptor {


    public Object invoke(MethodInvocation invocation) throws Throwable {

        Object[] args = invocation.getArguments();
        System.out.println("############# args = " + args);
        for(Object item : args) {
            ((AuditProps) item).renewCreateTime();
            System.out.println("############ item = " + item);
        }
        Object ret = invocation.proceed();
        return ret;
    }

}
