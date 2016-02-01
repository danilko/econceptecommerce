/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2013 Kai-Ting (Danil) Ko
 * <p>
 * Permission is hereby granted, free of charge,
 * to any person obtaining a copy of this software
 * and associated documentation files (the "Software"),
 * to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify,
 * merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom
 * the Software is furnished to do so, subject to the
 * following conditions:
 * <p>
 * The above copyright notice and this permission notice
 * shall be included in all copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY
 * OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED
 * TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 * PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS
 * OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
 * ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE
 * USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.controller.dao.com.controller.dao.impl.spring;

import com.controller.dao.ProductDAO;
import com.controller.model.Product;
import com.controller.model.ProductSpecification;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class SpringProductDAO implements ProductDAO {

    @Inject
    private JdbcTemplate jdbcTemplate;

    @Inject
    private SpringProductRowMapper productRowMapper;

    @Inject
    private SpringProductSpecificationRowMapper productSpecificationRowMapper;

    @Override
    @Transactional
    public void createProduct(Product product) {
        jdbcTemplate.update("INSERT INTO product (product_serial_number, product_name, product_description, product_price) VALUES (?, ?, ?)", new Object[]{product.getProductSerialNumber(), product.getProductName(), product.getProductDescription(), product.getProductPrice()});

    }

    @Override
    @Transactional
    public void deleteProductByProductSerialNumber(String productSerialNumber) {
        jdbcTemplate.update("DELETE product_specification WHERE product_serial_number = ?", new Object[]{productSerialNumber});
        jdbcTemplate.update("DELETE product WHERE product_serial_number = ?", new Object[]{productSerialNumber});
    }


    @Override
    @Transactional
    public List<Product> getAllProducts() {

        List<Object> productRows = jdbcTemplate.query("SELECT * FROM PRODUCT", new Object[]{}, productRowMapper);

        List<Product> productList = new ArrayList<Product>();

        for (Object productRow : productRows) {
            if (productRow instanceof Product) {

                Product product = (Product) productRow;

                List<Object> productSpecificationRows = jdbcTemplate.query("SELECT * FROM product_specification WHERE product_serial_number = ?", new Object[]{product.getProductSerialNumber()}, productSpecificationRowMapper);

                List<ProductSpecification> productSpecificationList = new ArrayList<ProductSpecification>();
                List<String> productSpecificationImageList = new ArrayList<String>();

                for (Object productSpecificationRow : productSpecificationList) {
                    if (productSpecificationRow instanceof ProductSpecification) {
                        ProductSpecification productSpecification = (ProductSpecification)productSpecificationRow;
                        if(productSpecification.getProductSpecificationName().equalsIgnoreCase("Image")) {
                            productSpecificationImageList.add(productSpecification.getProductSpecificationValue());
                        } else
                        {
                            productSpecificationList.add((ProductSpecification) productSpecificationRow);
                        }
                    }
                }

                product.setProudctImageList(productSpecificationImageList);
                product.setProductSpecificationList(productSpecificationList);
                productList.add(product);
            }
        }

        return productList;
    }

    @Override
    @Transactional
    public Product getProductByProductSerialNumber(String productSerialNumber) {

        Product product = (Product) jdbcTemplate.queryForObject("SELECT * FROM product WHERE product_serial_number = ?", new Object[]{productSerialNumber}, productRowMapper);

        if (product != null) {

            List<Object> productSpecificationRows = jdbcTemplate.query("SELECT * FROM product_specification WHERE product_serial_number = ?", new Object[]{product.getProductSerialNumber()}, productSpecificationRowMapper);

            List<ProductSpecification> productSpecificationList = new ArrayList<ProductSpecification>();

            for (Object productSpecificationRow : productSpecificationList) {
                if (productSpecificationRow instanceof ProductSpecification) {
                    productSpecificationList.add((ProductSpecification) productSpecificationRow);
                }
                product.setProductSpecificationList(productSpecificationList);


            }
        }

        return product;
    }
}
