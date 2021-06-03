package com.pnxtest.integrationTest.controller.pnxtest.core;

import com.pnxtest.core.api.*;
import com.pnxtest.core.assertions.PnxAssert;

import java.util.Map;

@Controller(module = "数据驱动", maintainer = "nicolas.chen")
public class DataDrivenTest {

    @Test
    @DataDriven(value = "testdata.txt", withHeader = true)
    @DisplayName("数据驱动.txt文件：使用map传递参数")
    @Description("使用csv或txt文件中的数据作为输入源\n所有数据集合的执行被当作一个测试用例\n当前只支持csv和txt文件")
    @Order(1)
    void testDataDrivenUsingTextFile(Map<String, Object> data){
        if(data.get("name").equals("Ming")){
            PnxAssert.assertThat(data.get("mobile")).isEqualTo("18222223333");
            PnxAssert.assertThat(data.get("city")).isEqualTo("shanghai");
        }
    }

    @Test
    @DataDriven(value = "testdata.txt", withHeader = true)
    @DisplayName("数据驱动.txt文件：依次传递多个参数")
    @Description("使用csv或txt文件中的数据作为输入源\n所有数据集合的执行被当作一个测试用例")
    @Order(2)
    void testDataDrivenUsingTextFile(String name, long mobile, String city){
        if(name.equals("Ming")){
            PnxAssert.assertThat(mobile).isEqualTo(18222223333L);
        }
    }

    @Test
    @DataDriven(value = "testdata_no_header.txt")
    @DisplayName("数据驱动.txt文件&没有header：依次传递多个参数")
    @Description("使用csv或txt文件中的数据作为输入源\n所有数据集合的执行被当作一个测试用例")
    @Order(3)
    void testDataDrivenUsingTextFileWithoutHeader(String name, long mobile, String city){
        if(name.equals("Ming")){
            PnxAssert.assertThat(mobile).isEqualTo(18222223333L);
        }
    }

    @Test
    @DataDriven(value = "testdata2.csv", withHeader = true)
    @DisplayName("数据驱动csv文件：使用map传参")
    @Description("使用csv或txt文件中的数据作为输入源\n所有数据集合的执行被当作一个测试用例\n当前只支持csv和txt文件")
    @Order(4)
    void testDataDrivenUsingCsvFile(Map<String, String> data){
        if(data.get("name").equals("David")){
            PnxAssert.assertThat(data.get("sex")).isEqualTo("male");
            PnxAssert.assertThat(data.get("city")).isEqualTo("shanghai");
        }
    }

    @Test
    @DataDriven(value = "testdata2_no_header.csv")
    @DisplayName("数据驱动csv文件&没有header：使用map传递参数")
    @Description("使用csv或txt文件中的数据作为输入源\n所有数据集合的执行被当作一个测试用例\n当前只支持csv和txt文件")
    @Order(5)
    void testDataDrivenUsingCsvFileWithoutHeader(Map<String, Object> data){
        if(data.get("col_0").equals("Ming")){
            PnxAssert.assertThat(data.get("col_1")).isEqualTo("18222223333");
            PnxAssert.assertThat(data.get("col_2")).isEqualTo("shanghai");
        }
    }


    @Test
    @DataDriven("testdata-not-existed.txt")
    @DisplayName("数据驱动文件不存在：该用例应该失败")
    @Description("使用csv或txt文件中的数据作为输入源\n所有数据集合的执行被当作一个测试用例\n当前只支持csv和txt文件")
    @Order(6)
    void testDataDrivenWhenFileNotFound(Map<String, String> data){

    }


    @Test
    @DataDriven("testdata3.json")
    @DisplayName("数据驱动不支持的格式json: 该用例应该失败")
    @Order(7)
    void testDataDrivenWhenJsonFileUnsupported(Map<String, String> data){
    }


}
