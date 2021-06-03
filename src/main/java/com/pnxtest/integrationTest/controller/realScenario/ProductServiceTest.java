package com.pnxtest.integrationTest.controller.realScenario;

import com.pnxtest.core.api.Controller;
import com.pnxtest.core.api.DisplayName;
import com.pnxtest.core.api.Test;
import com.pnxtest.core.assertions.PnxAssert;
import com.pnxtest.core.steps.Steps;
import com.pnxtest.integrationTest.entity.Product;
import com.pnxtest.integrationTest.repository.ProductRepository;

@Controller(module = "ProductService")
public class ProductServiceTest {
    @Steps
    ProductRepository productRepository;

    @Test
    @DisplayName("创建商品并上架")
    void testGoodsLoadAndUnLoad(){
        //Step1: 创建商品
        Product product = new Product();
        product.setId(1024L);
        product.setName("Tesla Model 3");
        product.setBrand("Tesla");
        product.setPrice(32.8);
        //Step2:上架商品
        long productId = productRepository.load(product);
        PnxAssert.assertThat(productId)
                .as("检查新商品上架成功")
                .isEqualTo(product.getId());
        //Step3:获取商品信息
        Product car = productRepository.getGoodsInfo(productId);
        //Step4:验证信息
        PnxAssert.assertThat(car.getBrand())
                .as("验证新上架的商品信息")
                .isEqualTo("Tesla");
        //Step5:检查数据是否落地以及商品状态
//        Optional<Product> productInDb = PnxSql.select("SELECT * FROM tb_products WHERE id=:productId")
//                .bind("productId", 1024)
//                .asOne(Product.class);
//        PnxAssert.assertThat(productInDb.getStats()).isEqualTo(1);

    }

}
