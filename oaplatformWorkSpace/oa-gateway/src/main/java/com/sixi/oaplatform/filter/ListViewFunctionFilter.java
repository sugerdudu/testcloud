package com.sixi.oaplatform.filter;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.sixi.oaplatform.common.constant.FunctionEnumConstant;
import com.sixi.oaplatform.config.request.VerifyAuthServletRequestWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

/**
 *
 * @author wangwei
 * @date 2018/3/25
 * 网关测试参数修改
 * 列表页过滤器
 */
@Component
@Slf4j
public class ListViewFunctionFilter extends ZuulFilter {


    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    /**
     * 再自定义的权限验证之后
     *
     * @return int
     */
    @Override
    public int filterOrder() {
        return 2;
    }

    /**
     * 只有功能类型是列表页的才需要进行拦截
     *
     *
     * @return boolean
     */
    @Override
    public boolean shouldFilter() {
        RequestContext request = RequestContext.getCurrentContext();
        HttpServletRequest servletRequest = request.getRequest();

        return FunctionEnumConstant.list_view.getTypeCode().equalsIgnoreCase(servletRequest.getHeader("functionType"));
    }


    /**
     * 在氢气参数中添加body内容
     *
     * @return
     */
    @Override
    public Object run() {

        try {
            RequestContext requestContext = RequestContext.getCurrentContext();
            HttpServletRequest servletRequest = requestContext.getRequest();

            BufferedReader reader = servletRequest.getReader();
            StringBuilder sb = new StringBuilder();
            String str;

            while ((str = reader.readLine()) != null){
                sb.append(str);
            }


            log.info("--------------------------------------------------------------");
            log.info("请求体:"+sb.toString());
            log.info("--------------------------------------------------------------");

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("userId",3405);
            jsonObject.put("class1Id",3);
            jsonObject.put("class2Id",27);

            String s = sb.toString();
            byte[] bodies = null;

            if (s.startsWith("{")){
                JSONObject json = JSONObject.parseObject(s);
                json.put("auth_role",jsonObject);


                bodies = JSONObject.toJSONBytes(json);
            }else if (s.startsWith("[")){
                JSONArray array = JSONArray.parseArray(s);

                for (Object o1 : array) {
                    JSONObject json = (JSONObject) o1;
                    json.put("auth_role",jsonObject);
                }

                bodies = JSONArray.toJSONBytes(array);
            }

            requestContext.setRequest(new VerifyAuthServletRequestWrapper(servletRequest,bodies));

            return null;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("抛出异常");
        }
    }
}
