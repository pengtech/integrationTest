package com.pnxtest.integrationTest.controller.pnxtest.core;

import com.pnxtest.core.api.*;
import com.pnxtest.core.assertions.PnxAssert;
import com.pnxtest.core.executors.CurrentTestInfo;
import com.pnxtest.core.helper.PnxHelper;

import java.util.concurrent.TimeUnit;

@Controller(module = "测试用例", maintainer = "nicolas.chen")
public class BasicTestCaseAttributesTest {

    @Test
    @DisplayName("一个标准的PnxTest测试用例")
    @Description("PnxTest测试用例开发规范：" +
            "\n1)请使用@Controller对用例进行归类" +
            "\n2)类名和方法名采用驼峰格式，尽量表达你要测试目标功能" +
            "\n3)@Controller里的测试方法要尽量简洁，具体的功能实现逻辑放到@Repository中，便于复用" +
            "\n4)使用@Steps引用@Repository中的步骤" +
            "\n5)编写用例标题、描述、优先级等信息，便于理解和后期维护" +
            "\n6)尽量使用PnxTest自带的功能函数，如PnxHelper.await" +
            "\n7)不要在用例中使用System.out.println直接打印信息，建议使用PnxSteps.info进行观察")
    @Level(Levels.L1)
    @Order(1)
    void testBasicAttributesOfATestCase(){
        PnxHelper.await(5, TimeUnit.SECONDS);
        PnxAssert.assertThat("pnx").isNotNull();
    }

    @Test
    @DisplayName("测试用例关联#Bug-123, 用于检查#Bug-123是否修复")
    @Description("PnxTest测试用例开发规范：" +
            "\n1)请使用@Controller对用例进行归类" +
            "\n2)类名和方法名采用驼峰格式，尽量表达你要测试目标功能" +
            "\n3)@Controller里的测试方法要尽量简洁，具体的功能实现逻辑放到@Repository中，便于复用" +
            "\n4)使用@Steps引用@Repository中的步骤" +
            "\n5)编写用例标题、描述、优先级等信息，便于理解和后期维护" +
            "\n6)尽量使用PnxTest自带的功能函数，如PnxHelper.await" +
            "\n7)不要在用例中使用System.out.println直接打印信息，建议使用PnxSteps.info进行观察")
    @Issue("bug-123")
    @Level(Levels.L2)
    @Order(2)
    void testDisplayNameWithIssue(){
        PnxAssert.assertThat("pnx").isNotNull();
    }

    @Test
    @DisplayName("测试用例关联#Bug-123、#task-456, 用于检查#Bug-123是否修复，标注task-456是否完成")
    @Description("PnxTest测试用例开发规范：" +
            "\n1)请使用@Controller对用例进行归类" +
            "\n2)类名和方法名采用驼峰格式，尽量表达你要测试目标功能" +
            "\n3)@Controller里的测试方法要尽量简洁，具体的功能实现逻辑放到@Repository中，便于复用" +
            "\n4)使用@Steps引用@Repository中的步骤" +
            "\n5)编写用例标题、描述、优先级等信息，便于理解和后期维护" +
            "\n6)尽量使用PnxTest自带的功能函数，如PnxHelper.await" +
            "\n7)不要在用例中使用System.out.println直接打印信息，建议使用PnxSteps.info进行观察")
    @Issue({"bug-123", "task-456"})
    @Level(Levels.L3)
    @Order(3)
    void testDisplayNameWithIssues(){
        PnxAssert.assertThat("pnx").isNotNull();
    }


    @Test
    @DisplayName("测试用例关联#Bug-321, 用于检查#Bug-321是否修复")
    @Description("PnxTest测试用例开发规范：" +
            "\n1)请使用@Controller对用例进行归类" +
            "\n2)类名和方法名采用驼峰格式，尽量表达你要测试目标功能" +
            "\n3)@Controller里的测试方法要尽量简洁，具体的功能实现逻辑放到@Repository中，便于复用" +
            "\n4)使用@Steps引用@Repository中的步骤" +
            "\n5)编写用例标题、描述、优先级等信息，便于理解和后期维护" +
            "\n6)尽量使用PnxTest自带的功能函数，如PnxHelper.await" +
            "\n7)不要在用例中使用System.out.println直接打印信息，建议使用PnxSteps.info进行观察")
    @Issue("bug-321")
    @Level(Levels.L3)
    @Order(4)
    @Disabled("bug-321没有完成，所以暂不执行")
    void testIfBug321Resolved(){
        PnxAssert.assertThat("pnx").isNotNull();
    }

    @Test
    @DisplayName("重复执行100次")
    @RepeatedCount(100)
    @Order(5)
    public void testRepeatedCountIs100(CurrentTestInfo currentTestInfo){
        PnxAssert.assertThat(currentTestInfo.getRepetitionIndex()).isGreaterThanOrEqualTo(0);
        PnxAssert.assertThat(currentTestInfo.getRepetitionCount()).isEqualTo(100);
    }

}
