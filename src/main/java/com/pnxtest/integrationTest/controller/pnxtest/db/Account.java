package com.pnxtest.integrationTest.controller.pnxtest.db;

import com.pnxtest.db.api.Column;
import com.pnxtest.db.api.Entity;

@Entity
public class Account{
    @Column(name="mobile")
    private long mobile;
    @Column(name="account_id")
    private long accountId;

    public long getMobile() {
        return mobile;
    }

    public void setMobile(long mobile) {
        this.mobile = mobile;
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }
}
