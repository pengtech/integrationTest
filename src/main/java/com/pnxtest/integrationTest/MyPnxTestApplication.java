package com.pnxtest.integrationTest;

import com.pnxtest.core.PnxTest;
import com.pnxtest.core.api.PnxTestApplication;

@PnxTestApplication(
        envID = "default",
        suiteFile = "pnxtest.xml"
)
public class MyPnxTestApplication {
    public static void main(String[] args){
        PnxTest.run(MyPnxTestApplication.class, args);
    }

}
