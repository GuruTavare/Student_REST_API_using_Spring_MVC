package com.prowings.Interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.ContentCachingRequestWrapper;

import com.prowings.exception.StudentNotFoundException;

public class MyWebInterceptor implements HandlerInterceptor {

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		System.out.println("Request processing completed.");
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// Log outgoing response details
		System.out.println("Outgoing Response Headers: ");
		response.getHeaderNames()
				.forEach(headerName -> System.out.println(headerName + ": " + response.getHeader(headerName)));
		System.out.println("Outgoing Response Status Code: " + response.getStatus());

		System.out.println("Handler execution completed.");
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// Log the request URL and method
		System.out.println(">>>>> Inside Logging interceptor!!!");
		System.out.println("Request URL: " + request.getRequestURL().toString());
		System.out.println("Request Method: " + request.getMethod());

		ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(request);

		// Read request body only for POST or PUT requests
		if (wrappedRequest.getMethod().equals("POST") || wrappedRequest.getMethod().equals("PUT")) {
			byte[] requestBody = wrappedRequest.getContentAsByteArray();
			String characterEncoding = wrappedRequest.getCharacterEncoding();
			if (characterEncoding == null) {
				characterEncoding = "UTF-8"; // Default to UTF-8 if character encoding is not set
			}
			System.out.println("Incoming Request Body: " + new String(requestBody, characterEncoding));
		}

//        if(10/2 == 5)
//        	throw new StudentNotFoundException();
		return true;
	}
}