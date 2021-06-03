package com.pnxtest.integrationTest.controller.pnxtest.http;

import com.pnxtest.core.api.Test;
import com.pnxtest.core.assertions.PnxAssert;
import com.pnxtest.core.environment.PnxContext;
import com.pnxtest.core.api.Controller;
import com.pnxtest.core.api.DisplayName;
import com.pnxtest.http.PnxHttp;

import java.io.File;
import java.nio.file.Paths;

@Controller(module = "PnxHttp-POST")
public class PostTest {

    @Test
    @DisplayName("HTTP post method no parameters")
    public void testPostNoParameters(){
        String ret = PnxHttp.post("https://httpbin.org/post")
                .asString()
                .getBody();

        PnxAssert.assertThat(ret).isNotNull();
    }

    @Test
    @DisplayName("HTTP post method with formdata")
    public void testPostMethodWithFormData(){
        String ret = PnxHttp.post("https://httpbin.org/post")
                .field("name", "nicolas")
                .field("age", "30")
                .field("city", "上海")
                .asString()
                .getBody();

        PnxAssert.assertThat(ret).isNotNull();
    }

    @Test
    @DisplayName("File upload")
    public void testPostMethodWithFileUpload(){
        File file = Paths.get(PnxContext.getTestResultLocation(), "download", "nic2.jpg").toFile();

        String ret = PnxHttp.post("https://httpbin.org/post")
                .field("name", "nicolas")
                .field("age", "30")
                .field("city", "上海")
                .field("avatar", file)
                .asString()
                .getBody();

        PnxAssert.assertThat(ret).isNotNull();
    }


    @Test
    @DisplayName("string body")
    public void testPostMethodWithStringBody(){

        String ret = PnxHttp.post("https://httpbin.org/post")
                .body("PnxTest is the best test framework in the world")
                .asString()
                .getBody();

        PnxAssert.assertThat(ret).isNotNull();
    }

    @Test
    @DisplayName("byte[] body")
    public void testPostMethodWithByteBody(){

        String ret = PnxHttp.post("https://httpbin.org/post")
                .body(("PnxTest").getBytes())
                .asString()
                .getBody();

        PnxAssert.assertThat(ret).isNotNull();
    }

}
