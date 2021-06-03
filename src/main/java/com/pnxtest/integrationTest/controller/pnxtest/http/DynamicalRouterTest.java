package com.pnxtest.integrationTest.controller.pnxtest.http;

import com.pnxtest.core.api.Test;
import com.pnxtest.core.assertions.PnxAssert;
import com.pnxtest.core.api.Controller;
import com.pnxtest.core.api.DisplayName;
import com.pnxtest.http.api.HttpResponse;
import com.pnxtest.http.PnxHttp;

import java.util.Base64;

@Controller(module = "PnxHttp-动态路由")
public class DynamicalRouterTest {

    @Test
    @DisplayName("HTTP dynamical router")
    public void testRequestPathWithRouter(){
        HttpResponse<String> res = PnxHttp.get("https://httpbin.org//base64/{value}")
                .routeParam("value", Base64.getEncoder().encodeToString(("nicolaschen").getBytes()))
                .asString();

        PnxAssert.assertThat(res.getBody()).as("返回的数据不为空").isNotNull();
    }

    @Test
    @DisplayName("HTTP multiple dynamical routers")
    public void testRequestPathWithMultipleRouters(){
        HttpResponse<String> res = PnxHttp.get("https://httpbin.org//base64/{category}/{id}")
                .routeParam("category", "movie")
                .routeParam("id", "1908")
                .asString();

        PnxAssert.assertThat(res.getBody()).as("返回的数据不为空").isNotNull();
    }

}
