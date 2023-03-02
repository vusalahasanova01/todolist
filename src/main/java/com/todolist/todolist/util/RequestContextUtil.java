package com.todolist.todolist.util;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public final class RequestContextUtil {

    private RequestContextUtil() {
    }

    public static Optional<HttpServletRequest> getServletRequest() {
        return Optional.ofNullable(RequestContextHolder.getRequestAttributes())
                .map(RequestContextUtil::mapServletRequestAttributes)
                .map(RequestContextUtil::mapHttpServletRequest);
    }

    private static ServletRequestAttributes mapServletRequestAttributes(
            RequestAttributes requestAttributes) {
        return ((ServletRequestAttributes) requestAttributes);
    }

    private static HttpServletRequest mapHttpServletRequest(
            ServletRequestAttributes servletRequestAttributes) {
        return servletRequestAttributes.getRequest();
    }

}
