package com.yelpcamp.filter;

import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SanitizeHtmlFilter extends OncePerRequestFilter {


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if(isHtmlSafe(request)) {
            filterChain.doFilter(request, response);
            return;
        }
        response.sendError(HttpStatus.BAD_REQUEST.value(), "Html Tags not allowed in input");
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request){
        return request.getMethod().equals(HttpMethod.GET.name());
    }

    private static boolean isHtmlSafe(HttpServletRequest request) {
        var parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()){
            var parameterName = parameterNames.nextElement();
            var parameterValue = request.getParameter(parameterName);
            var escapedParameterValue = Jsoup.clean(parameterValue, Safelist.none());
            if(!parameterValue.equals(escapedParameterValue)) return false;
        }
        return true;
    }
}
