package com.prowings.filter;

import java.io.*;
import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ReadListener;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class MyFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		System.out.println("Inside doFiletr Method!!!");

		// Logging logic
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		System.out.println("Request URL: " + httpRequest.getRequestURL());
		System.out.println("Request Method: " + httpRequest.getMethod());
		// Read request body and log it
//		String requestBody = getRequestBody(httpRequest);
//		System.out.println("Request Body: " + requestBody);
//		System.out.println("<<<<<<<<");

		chain.doFilter(request, response);
	}

	private String getRequestBody(HttpServletRequest request) {
		StringBuilder stringBuilder = new StringBuilder();
		BufferedReader bufferedReader = null;

		try {
			bufferedReader = new BufferedReader(new InputStreamReader(request.getInputStream()));
			char[] charBuffer = new char[128];
			int bytesRead;
			while ((bytesRead = bufferedReader.read(charBuffer)) != -1) {
				stringBuilder.append(charBuffer, 0, bytesRead);
			}
		} catch (IOException e) {
			e.printStackTrace();

		} 
		finally {
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			return stringBuilder.toString();
		}
	}

	class RequestWrapper extends HttpServletRequestWrapper {
		private final String body;

		public RequestWrapper(HttpServletRequest request) {
			super(request);
			body = getRequestBody(request);
		}

		@Override
		public BufferedReader getReader() throws IOException {
			return new BufferedReader(new InputStreamReader(getInputStream()));
		}

		@Override
		public ServletInputStream getInputStream() throws IOException {
			final byte[] bytes = body.getBytes();
			return new ServletInputStream() {
				private int lastIndexRetrieved = -1;
				private ReadListener readListener = null;

				@Override
				public boolean isFinished() {
					return (lastIndexRetrieved == bytes.length - 1);
				}

				@Override
				public boolean isReady() {
					// This implementation will never block
					return isFinished();
				}

				@Override
				public void setReadListener(ReadListener listener) {
					this.readListener = listener;
					if (!isFinished()) {
						try {
							listener.onDataAvailable();
						} catch (IOException e) {
							listener.onError(e);
						}
					} else {
						try {
							listener.onAllDataRead();
						} catch (IOException e) {
							listener.onError(e);
						}
					}
				}

				@Override
				public int read() throws IOException {
					if (isFinished()) {
						return -1;
					}
					int data = bytes[lastIndexRetrieved + 1];
					lastIndexRetrieved++;
					if (isFinished() && (readListener != null)) {
						readListener.onAllDataRead();
					}
					return data;
				}
			};
		}
	}

}
