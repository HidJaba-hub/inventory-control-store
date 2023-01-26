package com.company.electro_store.server.functions.storage;

import com.company.electro_store.entity.store.Product;
import com.company.electro_store.entity.store.Shell;
import com.company.electro_store.server.functions.Function;

import java.util.List;

import static com.company.electro_store.server.functions.Function.productService;

public class ProductFunction extends Function {
    public static boolean saveOrUpdateProduct(Product product) {
        return productService.saveOrUpdate(product);
    }
    public static boolean deleteProduct(Product product){
        return productService.delete(product);
    }
    public static List<Product> getProducts(){
        return productService.readAll();
    }
    public static Product getProductByName(Integer name){
        return productService.read(name);
    }
}
