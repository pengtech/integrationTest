package com.pnxtest.integrationTest.controller.pnxtest.core;

import com.pnxtest.core.ApplicationKeys;
import com.pnxtest.core.api.*;
import com.pnxtest.core.assertions.PnxAssert;
import com.pnxtest.core.environment.PnxContext;

@Controller(module = "环境数据", maintainer = "nicolas.chen")
public class EnvironmentPropertiesTest {
    @Test
    @DisplayName("环境变量的设置")
    @Description("使用PnxContext进行上下文管理；对于一些敏感的数据，如连接密码，可以使用加密后的文本展示")
    void TestNormalProperties(){
        PnxContext.setProperty("checkedProperty", 1024);
        PnxAssert.assertThat(PnxContext.getInt("checkedProperty")).isEqualTo(1024);

        PnxContext.setProperty("checkedProperty", 1024L);
        PnxAssert.assertThat(PnxContext.getLong("checkedProperty")).isEqualTo(1024L);

        PnxContext.setProperty("checkedProperty", 0);
        PnxAssert.assertThat(PnxContext.getBoolean("checkedProperty")).isFalse();


        PnxContext.setProperty("checkedProperty", "false");
        PnxAssert.assertThat(PnxContext.getBoolean("checkedProperty")).isFalse();

        PnxContext.setProperty("checkedProperty", false);
        PnxAssert.assertThat(PnxContext.getBoolean("checkedProperty")).isFalse();


        PnxContext.setProperty("checkedProperty", "on");
        PnxAssert.assertThat(PnxContext.getBoolean("checkedProperty")).isTrue();

        PnxContext.setProperty("checkedProperty", "pnxTest");
        PnxAssert.assertThat(PnxContext.getString("checkedProperty")).isEqualTo("pnxTest");

        PnxContext.setProperty("checkedPropertyClone", "${checkedProperty}");
        PnxAssert.assertThat(PnxContext.getString("checkedPropertyClone")).isEqualTo("pnxTest");

        PnxContext.removeProperty("checkedProperty");
        PnxAssert.assertThat(PnxContext.getString("checkedProperty")).isNull();

    }

    @Test
    @DisplayName("环境变量操作 - 数据简单加密")
    @Description("使用PnxContext进行上下文管理；对于一些敏感的数据，如连接密码，可以使用加密后的文本展示")
    void TestEncryptedProperties(){
        ICryptoConfig crypto = PnxContext.getBean(ApplicationKeys.PNX_CRYPTO_IMPL, ICryptoConfig.class);
        String plainText = "PnxTest.secret2021";
        String cipherText = crypto.encrypt(plainText);
        PnxAssert.assertThat(crypto.decrypt(cipherText)).as("加解密验证").isEqualTo(plainText);

        PnxContext.setProperty("secretPass", "secret."+cipherText);

        PnxAssert.assertThat(PnxContext.getString("secretPass"))
                .as("secret.cipherText属性变量自动解密")
                .isNotNull()
                .isEqualTo(plainText);

    }

}
