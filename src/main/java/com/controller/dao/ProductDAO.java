
/**
 * The MIT License (MIT)
 * <p/>
 * Copyright (c) 2013 Kai-Ting (Danil) Ko
 * <p/>
 * Permission is hereby granted, free of charge,
 * to any person obtaining a copy of this software
 * and associated documentation files (the "Software"),
 * to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify,
 * merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom
 * the Software is furnished to do so, subject to the
 * following conditions:
 * <p/>
 * The above copyright notice and this permission notice
 * shall be included in all copies or substantial portions of the Software.
 * <p/>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY
 * OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED
 * TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 * PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS
 * OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
 * ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE
 * USE OR OTHER DEALINGS IN THE SOFTWARE.
 */


package com.controller.dao;

import java.util.ArrayList;
import java.util.List;

import com.controller.model.Product;
import com.controller.model.ProductSpecification;

public class ProductDAO {

    public static List<Product> getAllProducts() {
        List<Product> productList = new ArrayList<Product>();
        Product product = new Product();
        product.setProductSerialNumber("ua-100");
        product.setProductPrice(200.0);
        product.setProductName("Unique Audio Power Noise Cancellator");
        product.setProductDescription(
                "Unique Audio Power Noise Cancellator Donec id elit non mi porta gravida at eget metus. Fuscedapibus, tellus ac cursus commodo, tortor mauris condimentumnibh, ut fermentum massa justo sit amet risus. Etiam porta semmalesuada magna mollis euismod. Donec sed odio dui.");

        List<String> productImageList = new ArrayList<String>();
        productImageList.add("img/testimage.jpg");
        product.setProudctImageList(productImageList);

        List<ProductSpecification> productSpecifications = new ArrayList<ProductSpecification>();

        ProductSpecification productSpecification = new ProductSpecification();
        productSpecification.setProductSerialNumber(product.getProductSerialNumber());
        productSpecification.setProductSpecificationName("Dimension");
        productSpecification.setProductSpecificationValue("2 in x 2 in x 2 in");

        productSpecifications.add(productSpecification);

        productSpecification = new ProductSpecification();
        productSpecification.setProductSerialNumber(product.getProductSerialNumber());
        productSpecification.setProductSpecificationName("Weight");
        productSpecification.setProductSpecificationValue("1 lb");

        productSpecifications.add(productSpecification);

        product.setProductSpecificationList(productSpecifications);

        productList.add(product);

        product = new Product();
        product.setProductSerialNumber("ua-200");
        product.setProductPrice(100.0);

        product.setProductName("Unique Audio Power Cable");
        product.setProductDescription(
                "Unique Audio Power Cable Eliminater Donec id elit non mi porta gravida at eget metus. Fuscedapibus, tellus ac cursus commodo, tortor mauris condimentumnibh, ut fermentum massa justo sit amet risus. Etiam porta semmalesuada magna mollis euismod. Donec sed odio dui.");

        productImageList = new ArrayList<String>();
        productImageList.add("img/testimage.jpg");
        product.setProudctImageList(productImageList);

        productSpecifications = new ArrayList<ProductSpecification>();

        productSpecification = new ProductSpecification();
        productSpecification.setProductSerialNumber(product.getProductSerialNumber());
        productSpecification.setProductSpecificationName("Leghth");
        productSpecification.setProductSpecificationValue("12 in");

        productSpecifications.add(productSpecification);

        productSpecification = new ProductSpecification();
        productSpecification.setProductSerialNumber(product.getProductSerialNumber());
        productSpecification.setProductSpecificationName("Weight");
        productSpecification.setProductSpecificationValue("2 lb");

        productSpecifications.add(productSpecification);

        productSpecification = new ProductSpecification();
        productSpecification.setProductSerialNumber(product.getProductSerialNumber());
        productSpecification.setProductSpecificationName("Input");
        productSpecification.setProductSpecificationValue("US Standard Power Port");

        productSpecifications.add(productSpecification);

        product.setProductSpecificationList(productSpecifications);

        productList.add(product);

        return productList;
    }

    public static Product getProductByProductSerialNumber(String productSerialNumber) {
        Product product = null;

        List<Product> productList = ProductDAO.getAllProducts();

        for (Product currentProduct : productList) {
            if (productSerialNumber.equalsIgnoreCase(currentProduct.getProductSerialNumber())) {
                product = currentProduct;
            }
        } // for

        return product;
    }
}
