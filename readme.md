# PnxTest Demo Project
- 包含pnxtest核心功能的测试和用法示范(controller/pnxtest)，以及一个真实场景的测试(controller/realScenario)
- 有部分失败的测试用例是为了项目展示而特别设置的

```text
项目结构：
integrationTest                               //项目名
├── src                                       //源码文件夹  
│   └── main
│       └── java
│           └── com.pnxtest.integrationTest   //包名：请根据自己公司或组织的要求创建
│               └── config                    //测试运行配置：包括HTTP网关配置、执行配置等全局配置
│               └── controller                //测试用例  
│               └── entity                    //实例对象
│               └── repository                //测试仓库：测试步骤以及具体的测试功能逻辑放这里，用于复用
│               └── MyPnxTestApplication      //PnxTest主应用入口
├── test-config                               //测试环境或数据
├── test-outputting                           //测试执行结果
├── pom.xml                                   //项目公共依赖

```






