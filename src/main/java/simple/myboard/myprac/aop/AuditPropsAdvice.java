package simple.myboard.myprac.aop;

import org.aopalliance.intercept.MethodInvocation;
import org.aopalliance.intercept.MethodInterceptor;
import simple.myboard.myprac.domain.AuditProps;


public class AuditPropsAdvice implements MethodInterceptor {


    public Object invoke(MethodInvocation invocation) throws Throwable {
        
        Object[] args = invocation.getArguments();
        // 메소드에 넣어주는 파라미터 하나씩 꺼내와서 확인해 보기
        for(Object arg : args) {
            try {
                AuditProps item = ((AuditProps) arg);
                // 최초 등록 시 0
                item.setIsDel(0);   // TODO - enum 값으로 변경
                // createTime 은 최초 등록 시에만 새로 넣기
                if(item.getCreateTime() == null) {
                    item.renewCreateTime();
                }
                // updateTime 은 항상 새로 넣기
                item.renewUpdateTime();
                
            } catch(ClassCastException e) {
                // AuditProps 자료형이 아니라면 넘어가기
                continue;
            }
        }
        return invocation.proceed();
    }

}
