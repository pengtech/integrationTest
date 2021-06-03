package com.pnxtest.integrationTest.repository;


import com.pnxtest.core.api.Repository;
import com.pnxtest.core.steps.Step;
import com.pnxtest.integrationTest.entity.Product;

@Repository
public class ProductRepository {

    @Step("获取商品ID为{0}的详细信息")
    public Product getGoodsInfo(long productId){
        // 伪代码 请求接口
        // PnxHttp.get("/products/getGoodsPrice").queryString("productId", productId).asString();
        Product product = new Product();
        product.setName("Tesla Model 3");
        product.setBrand("Tesla");
        product.setPrice(32.8);
        return product;
    }

    @Step("上架一个新商品")
    public long load(Product newProductInfo){
        // 伪代码 请求接口
        // PnxHttp.post("/products/load").body(newProductInfo).asString();
        return newProductInfo.getId();
    }



    @Step("下架ID为{0}的商品")
    public boolean unload(long productId){
        // 伪代码 请求接口
        //PnxHttp.post("/products/unload").field("productId", productId).asString();
        return true;
    }


}
