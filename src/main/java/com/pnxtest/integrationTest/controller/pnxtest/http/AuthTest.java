package com.pnxtest.integrationTest.controller.pnxtest.http;

import com.pnxtest.core.api.Test;
import com.pnxtest.core.assertions.PnxAssert;
import com.pnxtest.core.api.Controller;
import com.pnxtest.core.api.DisplayName;
import com.pnxtest.http.PnxHttp;

@Controller(module = "PnxHttp-Auth")
public class AuthTest {

    @Test
    @DisplayName("用户名与密码认证")
    void testBasicAuthWithUserNameAndPassword(){
        String ret = PnxHttp.get("https://httpbin.org/basic-auth")
                .basicAuth("nicolas", "pass1Word")
                .asString()
                .getBody();

        PnxAssert.assertThat(ret).isNotNull();

    }

    @Test
    @DisplayName("Bearer token认证")
    void testBasicAuthWithToken(){
        String ret = PnxHttp.get("https://httpbin.org/bearer")
                .bearAuth("=dasfasf#fasfff")
                .asString()
                .getBody();

        PnxAssert.assertThat(ret).isNotNull();

    }

}
