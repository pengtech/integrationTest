package com.pnxtest.integrationTest.controller.pnxtest.db;

import com.pnxtest.core.api.Test;
import com.pnxtest.core.assertions.PnxAssert;
import com.pnxtest.core.api.Controller;
import com.pnxtest.core.api.DisplayName;
import com.pnxtest.db.PnxSql;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller(module = "PnxSql")
public class DatabaseTest {
    @Test
    @DisplayName("从数据库获取首条数据，保存结果到Map")
    void testSelectOneRecordAsMap(){
        String sql = "select account_id, mobile from tb_account where account_id=:accountId and mobile=:mobile order by create_date desc limit 10";
        Optional<Map<String, Object>> stock = PnxSql.select(sql)
                .bind("accountId", 32000027188L)
                .bind("mobile", 16411111111L)
                .asOne();
        if(stock.isPresent()){
            Map<String, Object> rowData = stock.get();
            PnxAssert.assertThat(rowData.get("account_id")).isEqualTo(32000027188L);
        }
    }

    @Test
    @DisplayName("从数据库获取多个数据，保存结果到列表")
    void testSelectOneRecordAsList(){
        String sql = "select account_id, mobile from tb_account where mobile=:mobile order by create_date desc limit 10";
        List<Map<String, Object>> stock = PnxSql.select(sql)
                .bind("mobile", 16411111111L)
                .asList();
        PnxAssert.assertThat(stock.size()).isGreaterThan(0);
    }

    @Test
    @DisplayName("从数据库获取首条数据，保存结果到类")
    void testSelectOneRecordAsAccountBean(){
        String sql = "select account_id, mobile from tb_account where account_id=:accountId and mobile=:mobile order by create_date desc limit 10";
        Optional<Account> stock = PnxSql.select(sql)
                .bind("accountId", 32000027188L)
                .bind("mobile", 16411111111L)
                .asOne(Account.class);
        if(stock.isPresent()){
            Account account = stock.get();
            PnxAssert.assertThat(account.getAccountId()).isEqualTo(32000027188L);
        }
    }

    @Test
    @DisplayName("从数据库获取多个数据，保存结果到对象列表")
    void testSelectOneRecordAsListBean(){
        String sql = "select account_id, mobile from tb_account where mobile=:mobile order by create_date desc limit 10";
        List<Account> accounts = PnxSql.select(sql)
                .bind("mobile", 16411111111L)
                .asList(Account.class);
        PnxAssert.assertThat(accounts.size()).isGreaterThan(0);
    }

    @Test
    @DisplayName("更新数据")
    void testUpdate(){
        String sql = "update tb_account set mobile=:mobile where account_id=:accountId";
        int affectedRow = PnxSql.update(sql)
                .bind("accountId", 32000027218L)
                .bind("mobile", 19411111111L)
                .execute();
        PnxAssert.assertThat(affectedRow).isGreaterThan(0);
    }



}
