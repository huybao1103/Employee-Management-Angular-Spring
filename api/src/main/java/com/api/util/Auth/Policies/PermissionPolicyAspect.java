package com.api.util.Auth.Policies;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

@Aspect
@Component
public class PermissionPolicyAspect {

    @Autowired
    private PolicySecurityService policySecurityService;

    @Around("@annotation(com.api.util.Auth.Policies.PolicySecurityService.HasAllClaims)")
    public Object checkAllPermissions(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            Method method = getMethod(joinPoint);
            PolicySecurityService.HasAllClaims annotation = method.getAnnotation(PolicySecurityService.HasAllClaims.class);
            
            if (annotation != null) {
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

                List<String> requiredClaims = Arrays.asList(annotation.claims());
                
                // Check if user has all required claims
                if (!policySecurityService.hasAll(authentication, requiredClaims)) {
                    throw new AccessDeniedException("Access Denied: User does not have required permissions");
                }
            }
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            throw new AccessDeniedException("Error checking permissions: " + e.getMessage(), e);
        }

        // Proceed with the method execution
        return joinPoint.proceed();
    }

    @Around("@annotation(com.api.util.Auth.Policies.PolicySecurityService.HasAnyClaims)")
    public Object checkAnyPermissions(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            Method method = getMethod(joinPoint);
            PolicySecurityService.HasAnyClaims annotation = method.getAnnotation(PolicySecurityService.HasAnyClaims.class);
            
            if (annotation != null) {
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

                List<String> requiredClaims = Arrays.asList(annotation.claims());
                
                // Check if user has any of the required claims
                if (!policySecurityService.hasAny(authentication, requiredClaims)) {
                    throw new AccessDeniedException("Access Denied: User does not have required permissions");
                }
            }
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            throw new AccessDeniedException("Error checking permissions: " + e.getMessage(), e);
        }

        // Proceed with the method execution
        return joinPoint.proceed();
    }

    /**
     * Get the target method from the join point
     */
    private Method getMethod(ProceedingJoinPoint joinPoint) throws NoSuchMethodException {
        String methodName = joinPoint.getSignature().getName();
        Class<?>[] parameterTypes = ((org.aspectj.lang.reflect.MethodSignature) joinPoint.getSignature())
                .getParameterTypes();
        return joinPoint.getTarget().getClass().getMethod(methodName, parameterTypes);
    }
}
