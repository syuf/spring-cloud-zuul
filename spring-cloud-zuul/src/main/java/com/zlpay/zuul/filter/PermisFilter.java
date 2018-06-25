package com.zlpay.zuul.filter;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class PermisFilter extends ZuulFilter{

	/**
	 * filterType方法的返回值为过滤器的类型，过滤器的类型决定了过滤器在哪个生命周期执行，
	 * pre表示在路由之前执行过滤器，其他可选值还有post、error、route和static，当然也可以自定义。
	 */
	@Override
    public String filterType() {
        return "pre";
    }
	/**
	 * filterOrder方法表示过滤器的执行顺序，当过滤器很多时，这个方法会有意义。
	 */
    @Override
    public int filterOrder() {
        return 0;
    }

    /**
     * shouldFilter方法用来判断过滤器是否执行，true表示执行，false表示不执行，
     * 在实际开发中，我们可以根据当前请求地址来决定要不要对该地址进行过滤，这里我直接返回true。
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * run方法则表示过滤的具体逻辑，假设请求地址中携带了token参数的话，则认为是合法请求，否则就是非法请求，
     * 如果是非法请求的话，首先设置ctx.setSendZuulResponse(false);表示不对该请求进行路由，然后设置响应码和响应值。
     */
    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        log.info(String.format("%s >>> %s", request.getMethod(), request.getRequestURL().toString()));
        Object accessToken = request.getParameter("token");
        if(accessToken == null) {
            log.warn("token is empty");
            ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(401);
            try {
                ctx.getResponse().getWriter().write("token is empty");
            }catch (Exception e){}

            return null;
        }
        log.info("ok");
        return null;
    }
}
