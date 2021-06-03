/*
 *  Copyright (c) 2020-2021
 *  This file is part of PnxTest framework.
 *
 *  PnxTest is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Affero Public License version 3 as
 *  published by the Free Software Foundation
 *
 *  PnxTest is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Affero Public License for more details.
 *
 *  You should have received a copy of the GNU Affero Public License
 *  along with PnxTest.  If not, see <http://www.gnu.org/licenses/>.
 *
 *  For more information, please contact the author at this address:
 *  chen.baker@gmail.com
 *
 */

package com.pnxtest.integrationTest.config;

import com.pnxtest.core.api.Configuration;
import com.pnxtest.core.util.StringUtil;
import com.pnxtest.http.HttpConfig;
import com.pnxtest.http.api.IHttpConfig;
import com.pnxtest.http.internal.HttpGateway;
import com.pnxtest.http.internal.HttpMethod;
import org.apache.http.*;
import org.apache.http.protocol.HttpContext;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;


/**
 * 这是一个http统一网关的演示例子
 * nicolas.chen
 */

@Configuration
public class HttpGatewayConfig implements IHttpConfig {
    @Override
    public com.pnxtest.http.HttpConfig accept() {
        MyHttpGateway myHttpGateway = new MyHttpGateway();

        return HttpConfig.builder()
                .header("clientId", "PnxTest") //添加一个header
                .connectionTimeout(5000) //设置连接超时时间5s
                .socketTimeout(5000) //设置读取内容超时时间
                .gateway(myHttpGateway) //设置统一网关
                .build();
    }


    class MyHttpGateway extends HttpGateway{
        @Override
        public void process(HttpRequest httpRequest, HttpContext httpContext) throws HttpException, IOException {
            //这里可以request做统一拦截处理

            /* 增加请求签名
             * ===============
             * 签名原文规则：
             * ===============
             * URI(去域名(baseUrl)，去左右空格，全小写) +
             * QueryString(key&value, 按key升序，去左右空格，全小写) +
             * Header(key&value,只取[device-id,timestamp,token]升序，去左右空格，全小写) +
             * Body raw(去左右空格，全小写)
             * 或者 form-data(key&value，按key升序，去左右空格，全小写)
             * 或者 x-www-form-urlencoded(key&value，按key升序，去左右空格，全小写)
             * ===============
             * 签名算法：
             * ===============
             * 根据自己公司的接口签名算法来进行加密处理
             *
             */

            String url = httpRequest.getRequestLine().getUri();
            String reqMethod = httpRequest.getRequestLine().getMethod();

            try{
                StringBuilder signPlainData = new StringBuilder();
                URI uri = new URI(url);

                //handle uri
                String path = uri.getPath();
                String pathWithoutApi = path.replaceFirst("/api", "");
                signPlainData.append(pathWithoutApi);

                //handle queryString
                Map<String, List<String>> queryStrings = parseQueryString(uri.getRawQuery());
                List<String> queryStringKeys = new ArrayList<>(queryStrings.keySet());
                Collections.sort(queryStringKeys); //升序排列
                for(String key: queryStringKeys){
                    for(String v: queryStrings.get(key)) {
                        signPlainData.append(key);
                        signPlainData.append(v);
                    }
                }

                //handle headers
                Header[] headers = httpRequest.getAllHeaders();
                Arrays.sort(headers, new Comparator<Header>() {
                    public int compare(Header o1, Header o2){
                        return o1.getName().compareToIgnoreCase(o2.getName());
                    }
                });

                for(Header header:headers){
                    String hName = header.getName();
                    String hValue = header.getValue();
                    if(hName.equalsIgnoreCase("device-id")
                            || hName.equalsIgnoreCase("timestamp")
                            || hName.equalsIgnoreCase("token")){
                        signPlainData.append(hName);
                        signPlainData.append(hValue);
                    }
                }

                //handle body
                if (!reqMethod.equalsIgnoreCase(HttpMethod.GET.name())) {
                    HttpEntity reqEntity = ((HttpEntityEnclosingRequest) httpRequest).getEntity();
                    if (reqEntity != null && reqEntity.getContentLength() > 0) {
                        String reqBody = convertInputStreamToString(reqEntity.getContent());
                        if(!StringUtil.isEmpty(reqBody)) {
                            String contentType = null;
                            if(reqEntity.getContentType() != null){
                                String[] contentTypes = reqEntity.getContentType().getValue().split(";", 2);
                                contentType = contentTypes[0];
                            }

                            if ("application/x-www-form-urlencoded".equalsIgnoreCase(contentType)) {
                                Map<String, List<String>> formdataMap = parseQueryString(reqBody.trim());
                                List<String> fieldKeyList = new ArrayList<>(formdataMap.keySet());
                                Collections.sort(fieldKeyList);
                                for (String key : fieldKeyList) {
                                    for (String v : formdataMap.get(key)) {
                                        signPlainData.append(key);
                                        signPlainData.append(v);
                                    }
                                }
                            } else {
                                signPlainData.append(reqBody);
                            }
                        }
                    }
                }

                String plainText = signPlainData.toString(); //签名原文
                String sign = calculateSign(plainText); //加密签名
                httpRequest.addHeader("sign", sign); //添加签名到http请求头

            }catch (IOException | URISyntaxException e){
                //
            }
        }

        @Override
        public void process(HttpResponse httpResponse, HttpContext httpContext) throws HttpException, IOException {
            //这里可以response做统一拦截处理
        }


        private String calculateSign(String plainText) {
            //根据自身公司算法来计算，这里只是一个简单base64加密, 并没有使用私钥
            return Base64.getEncoder().encodeToString(plainText.toLowerCase().getBytes(StandardCharsets.UTF_8));

        }

        private Map<String, List<String>> parseQueryString(String queryString) throws UnsupportedEncodingException {
            if(StringUtil.isEmpty(queryString) || StringUtil.isBlank(queryString)){
                return new LinkedHashMap<String, List<String>>(0);
            }

            final Map<String, List<String>> queryPairs = new LinkedHashMap<String, List<String>>();
            final String[] pairs = queryString.split("&");

            for (String pair : pairs) {
                final int idx = pair.indexOf("=");
                final String key = idx > 0 ? URLDecoder.decode(pair.substring(0, idx), "UTF-8") : pair;
                if (!queryPairs.containsKey(key)) {
                    queryPairs.put(key, new LinkedList<String>());
                }

                final String value = (idx > 0 && pair.length() > idx + 1) ? URLDecoder.decode(pair.substring(idx + 1), "UTF-8") : null;
                queryPairs.get(key).add(value);
            }

            return queryPairs;
        }

        private String convertInputStreamToString(InputStream inputStream){
            try {
                ByteArrayOutputStream result = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                for (int length; (length = inputStream.read(buffer)) != -1; ) {
                    result.write(buffer, 0, length);
                }
                result.close();
                return result.toString("UTF-8");
            }catch (IOException e){
                //ignore
            }

            return "";
        }
    }

}
