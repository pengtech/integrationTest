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

package com.pnxtest.integrationTest.controller.pnxtest.core;

import com.pnxtest.core.api.*;
import com.pnxtest.core.assertions.PnxAssert;

import java.util.ArrayList;
import java.util.List;

@Controller(module = "PnxAssert")
public class AssertionTest {

    @Test
    @DisplayName("PnxAssert基本功能演示")
    @Level(Levels.L1)
    void testAssertionPrimitiveFunctions(){

        //基本数据类型
        boolean bActual = false;
        PnxAssert.assertThat(bActual).isFalse();

        //int
        int intActual = 100;
        PnxAssert.assertThat(intActual).isEqualTo(100);
        PnxAssert.assertThat(intActual).isGreaterThan(0);
        PnxAssert.assertThat(intActual).isGreaterThan(0).isEqualTo(100);

        Integer intActual1 = 100;
        PnxAssert.assertThat(intActual1).isEqualTo(100);
        PnxAssert.assertThat(intActual1).isGreaterThan(0);
        PnxAssert.assertThat(intActual1).isGreaterThan(0).isEqualTo(100);

        //long
        long longActual = 100;
        PnxAssert.assertThat(longActual).isEqualTo(100L);
        PnxAssert.assertThat(longActual).isGreaterThan(0L);
        PnxAssert.assertThat(longActual).isGreaterThan(0L).isEqualTo(100L);

        Long longActual1 = 100L;
        PnxAssert.assertThat(longActual1).isEqualTo(100L);
        PnxAssert.assertThat(longActual1).isGreaterThan(0L);
        PnxAssert.assertThat(longActual1).isGreaterThan(0L).isEqualTo(100L);

        //short
        short shortActual = 100;
        PnxAssert.assertThat(shortActual).isEqualTo((short)100);
        PnxAssert.assertThat(shortActual).isGreaterThan((short)0);
        PnxAssert.assertThat(shortActual).isGreaterThan((short)0).isEqualTo((short)100);

        Short shortActual1 = 100;
        PnxAssert.assertThat(shortActual1).isEqualTo((short)100);
        PnxAssert.assertThat(shortActual1).isGreaterThan((short)0);
        PnxAssert.assertThat(shortActual1).isGreaterThan((short)0).isEqualTo((short)100);

        //float
        float floatActual = 100.2f;
        PnxAssert.assertThat(floatActual).isEqualTo(100.2f);
        PnxAssert.assertThat(floatActual).isGreaterThan(0f);
        PnxAssert.assertThat(floatActual).isGreaterThan(0f).isEqualTo(100.2f);

        Float floatActual1 = 100.2F;
        PnxAssert.assertThat(floatActual1).isEqualTo(100.2F);
        PnxAssert.assertThat(floatActual1).isGreaterThan(0F);
        PnxAssert.assertThat(floatActual1).isGreaterThan(0F).isEqualTo(100.2F);

        double actual = 1.3;
        double operand = 1;
        double error = 0.5;

        PnxAssert.assertThat(actual).isCloseTo(operand,error);


        //字符串
        String strActual = "PnxTest is awesome!";

        PnxAssert.assertThat(strActual).isEqualTo("PnxTest is awesome!");
        PnxAssert.assertThat(strActual).isEqualToIgnoringCase("PnxTest is awesome!");
        PnxAssert.assertThat(strActual).isNotNull();
        PnxAssert.assertThat(strActual).startsWith("Pnx").endsWith("awesome!");
        PnxAssert.assertThat(strActual).hasLength(19);
        PnxAssert.assertThat(strActual).contains("awesome");

        PnxAssert.assertThat(strActual).isNotNull()
                .startsWith("Pnx")
                .endsWith("awesome!")
                .hasLength(19);

        //数组
        int[] intArr = new int[]{10, 20, 30};
        PnxAssert.assertThat(intArr)
                .isNotEmpty()
                .contains(10, 20, 30)
                .containsInAnyOrder(20, 10, 30)
                .hasSize(3);

        Integer[] intArr1 = new Integer[]{10, 20, 30};
        PnxAssert.assertThat(intArr1)
                .isNotEmpty()
                .contains(10, 20, 30)
                .containsInAnyOrder(20, 10, 30)
                .hasSize(3);

        long[] longArr = new long[]{10, 20, 30};
        PnxAssert.assertThat(longArr)
                .isNotEmpty()
                .contains(10L, 20L, 30L)
                .containsInAnyOrder(20L, 10L, 30L)
                .hasSize(3);

        Long[] longArr1 = new Long[]{10L, 20L, 30L};
        PnxAssert.assertThat(longArr1)
                .isNotEmpty()
                .contains(10L, 20L, 30L)
                .containsInAnyOrder(20L, 10L, 30L)
                .hasSize(3);

        String[] strArr = new String[]{"PnxTest", "Awesome"};
        PnxAssert.assertThat(strArr)
                .isNotEmpty()
                .hasSize(2)
                .hasItem("PnxTest")
                .contains("PnxTest", "Awesome");

        //集合
        List<String> strList = new ArrayList<>();
        strList.add("aa");
        strList.add("bb");
        strList.add("cc");

        PnxAssert.assertThat(strList)
                .hasSize(3)
                .isNotEmpty()
                .consistsOf("cc", "aa", "bb")
                .consistsOfOrdered("aa", "bb", "cc")
                .contains("aa", "bb")
                .doesNotContain("dd");


        List<Integer> intList = new ArrayList<>();
        intList.add(10);
        intList.add(11);
        intList.add(12);

        PnxAssert.assertThat(intList)
                .isNotEmpty()
                .hasSize(3)
                .consistsOf(11, 12, 10);
    }

    @Test
    @DisplayName("展示一个断言失败的用例")
    void shouldBeFailed(){
        List<Integer> actual = new ArrayList<>();
        actual.add(10);
        actual.add(11);
        actual.add(12);

        PnxAssert.assertThat(actual)
                .as("输入的金额必须多于5元")
                .isNotEmpty()
                .hasSize(2)
                .consistsOf(10, 12, 11);
    }
}
