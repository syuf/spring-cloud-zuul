package com.zlpay.zuul.hystrix;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

@Component
public class ZuulFallback implements FallbackProvider {

	@Override
	public ClientHttpResponse fallbackResponse(String route, Throwable cause) {
		return new ClientHttpResponse() {
			
			@Override
			public HttpHeaders getHeaders() {
				HttpHeaders headers = new HttpHeaders();
				//和body中的内容编码一致，否则容易乱码
		        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		        return headers;

			}
			
			@Override
			public InputStream getBody() throws IOException {
				JSONObject r = new JSONObject();
				try {
//					r.put("state", "9999");
					r.put("msg", "系统繁忙,稍后再试");
				} catch (JSONException e) {
					e.printStackTrace();
				}
				return new ByteArrayInputStream(r.toString().getBytes("UTF-8"));
			}
			
			@Override
			public String getStatusText() throws IOException {
				// TODO Auto-generated method stub
				return HttpStatus.OK.getReasonPhrase();
			}
			
			@Override
			public HttpStatus getStatusCode() throws IOException {
				// TODO Auto-generated method stub
				return HttpStatus.OK;
			}
			
			@Override
			public int getRawStatusCode() throws IOException {
				// TODO Auto-generated method stub
				return HttpStatus.OK.value();
			}
			
			@Override
			public void close() {
				// TODO Auto-generated method stub
				
			}
		};
	}

	@Override
	public String getRoute() {
		//*表示所有服务都回退
		return "*";
	}
	
}
