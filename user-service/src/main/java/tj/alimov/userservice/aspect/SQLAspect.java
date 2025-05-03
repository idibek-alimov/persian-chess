package tj.alimov.userservice.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class SQLAspect {
    @Pointcut("execution(* tj.alimov.userservice.repository..*(..))")
    public void registrationMatch(){

    }

    @Before("registrationMatch()")
    public void beforeSqlExecution(){
        System.out.println("Before sql execution");
    }

    @After("registrationMatch()")
    public void afterSqlExecution(){
        System.out.println("After sql execution");
    }

    @AfterReturning(pointcut = "registrationMatch()", returning = "result")
    public void afterSqlExecutionReturning(Object result){
            System.out.println("Sql execution returning : " + result);
    }
    @AfterThrowing(pointcut = "registrationMatch()",throwing = "error")
    public void afterThrowing(Throwable error){
        System.out.println("Error accured during sql execution " + error.getMessage());
    }
    @Around("registrationMatch()")
    public Object aroundSqlExecution(ProceedingJoinPoint joinPoint) throws Throwable{
        long start = System.currentTimeMillis();
        Object result;
        try{
            result = joinPoint.proceed();
        }
        catch (Throwable throwable){
            System.out.println("Error execution SQL: " + throwable.getMessage());
            throw throwable;
        }
        long elapsedTime = System.currentTimeMillis() - start;
        System.out.println("Execution time: " + elapsedTime + " milliseconds");
        return result;
    }
}
