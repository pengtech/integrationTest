package com.pnxtest.integrationTest.controller.pnxtest.http;

import com.pnxtest.core.api.Test;
import com.pnxtest.core.assertions.PnxAssert;
import com.pnxtest.core.api.Controller;
import com.pnxtest.core.api.DisplayName;
import com.pnxtest.http.api.HttpResponse;
import com.pnxtest.http.PnxHttp;

import java.io.File;


@Controller(module = "PnxHttp-GET")
public class GetTest {

     @Test
     void TestNoQueryString(){
        HttpResponse<String> res = PnxHttp.get("https://google.com")
                .asString();
         PnxAssert.assertThat(res.getStatus())
                 .as("应返回成功的状态码200")
                 .isEqualTo(200);

    }


    @Test
    public void TestGetContent(){
         HttpResponse<String> res = PnxHttp.get("https://httpbin.org/get").asString();
         PnxAssert.assertThat(res.getBody())
                .as("返回的数据不为空")
                .isNotNull();
    }



    @Test
    @DisplayName("get deny message")
    public void gzipMessage(){
        String ret = PnxHttp.get("https://httpbin.org/gzip")
                .asString()
                .getBody();

        PnxAssert.assertThat(ret).as("返回的数据不为空").isNotNull();
    }


    @Test
    @DisplayName("get html message")
    public void htmlMessage(){
        String ret = PnxHttp.get("https://httpbin.org/html")
                .asString()
                .getBody();

        PnxAssert.assertThat(ret).as("返回的数据不为空").isNotNull();
    }

    @Test
    @DisplayName("utf8 encoding message")
    public void utf8EncodingMessage(){
        String ret = PnxHttp.get("https://httpbin.org/encoding/utf8")
                .asString()
                .getBody();

        PnxAssert.assertThat(ret).as("返回的数据不为空").isNotNull();
    }


    @Test
    @DisplayName("image download")
    public void imageDownload(){
        File downloadFile = PnxHttp.get("https://httpbin.org/image/jpeg")
                .asFile("nicolas.avatar")
                .getBody();

        PnxAssert.assertThat(downloadFile.getName()).as("文件名不为空").isNotNull();

    }

    @Test
    @DisplayName("image download to test-data")
    public void imageDownloadWithDefaultDir(){
        File downloadFile = PnxHttp.get("https://httpbin.org/image/jpeg")
                .asFile("nic2.jpg")
                .getBody();

        PnxAssert.assertThat(downloadFile.getName()).isEqualTo("nic2.jpg");

    }

}
