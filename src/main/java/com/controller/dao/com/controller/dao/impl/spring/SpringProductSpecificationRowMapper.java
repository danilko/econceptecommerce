package com.controller.dao.com.controller.dao.impl.spring;

import com.controller.model.ProductSpecification;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SpringProductSpecificationRowMapper implements RowMapper {

    @Override
    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        ProductSpecification productSpecification = new ProductSpecification();

        productSpecification.setProductSerialNumber(rs.getString("product_serialnumber"));
        productSpecification.setProductSpecificationValue(rs.getString("product_specification_value"));
        productSpecification.setProductSpecificationName(rs.getString("product_specification_name"));

        return productSpecification;
    }
}