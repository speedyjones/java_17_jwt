package com.hib.hibenatemysql.service_impl.service;

import com.hib.hibenatemysql.domains.dto.ProductsDTO;
import com.hib.hibenatemysql.domains.entity.Products;

import java.util.List;

public interface ProductsService {
    List<ProductsDTO> saveProducts(List<Products> products);
    List<ProductsDTO> updateProducts(List<Products> products);
}
