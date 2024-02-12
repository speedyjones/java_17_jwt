package com.hib.hibenatemysql.controllers;

import com.hib.hibenatemysql.domains.dto.ProductsDTO;
import com.hib.hibenatemysql.domains.entity.Products;
import com.hib.hibenatemysql.service_impl.service.ProductsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("products")
@RequiredArgsConstructor
@CrossOrigin(maxAge = 3000)
public class ProductsController {

    private final ProductsService productsService;


    @PostMapping("saveProducts")
    private List<ProductsDTO> saveProducts(@RequestBody List<Products> products) {
        return productsService.saveProducts(products);
    }

    @PutMapping("updateProducts")
    private List<ProductsDTO> updateProducts(@RequestBody List<Products> products) {
        return productsService.updateProducts(products);
    }


}
