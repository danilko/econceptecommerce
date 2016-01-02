package com.controller.dao.com.controller.dao.impl.spring;


import com.controller.model.Product;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SpringProductRowMapper implements RowMapper {

    @Override
    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        Product product = new Product();

        product.setProductSerialNumber(rs.getString("product_serial_number"));
        product.setProductName(rs.getString("product_name"));
        product.setProductDescription(rs.getString("product_description"));
        product.setProductPrice(Double.parseDouble(rs.getString("product_price")));

        return product;
    }
}
