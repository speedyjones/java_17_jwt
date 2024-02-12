package com.hib.hibenatemysql.service_impl.impl;

import com.hib.hibenatemysql.controllers.CustomException;
import com.hib.hibenatemysql.domains.dto.ProductsDTO;
import com.hib.hibenatemysql.domains.entity.Products;
import com.hib.hibenatemysql.repo.ProductsRepo;
import com.hib.hibenatemysql.service_impl.service.ProductsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductsServiceImpl implements ProductsService {

    private final ProductsRepo productsRepo;
    private final ModelMapper modelMapper;

    @Override
    public List<ProductsDTO> saveProducts(List<Products> products) {
        List<ProductsDTO> productsDTOSList = new ArrayList<>();
        ListIterator<Products> iterator = products.listIterator();

        while (iterator.hasNext()) {
            float totalPrice = (iterator.next().getPerPrice() * iterator.previous().getQty());
            iterator.next().setTotalPrice(totalPrice);
        }
        productsRepo.saveAll(products);

        for (Products p : products) {
            ProductsDTO.builder()
                    .id(p.getId())
                    .name(p.getName())
                    .perPrice(p.getPerPrice())
                    .qty(p.getQty())
                    .totalPrice(p.getTotalPrice())
                    .build();
            productsDTOSList.add(modelMapper.map(p, ProductsDTO.class));
        }


        log.info("Saved Data :" + products);

        return productsDTOSList;
    }


    @Override
    public List<ProductsDTO> updateProducts(List<Products> products) {
        ListIterator<Products> iterator = products.listIterator();
        List<ProductsDTO> productsDTOSList = new ArrayList<>();


        for (Products p : products) {
            if (productsRepo.findById(p.getId()).isEmpty()) {
                throw new CustomException("Id " + p.getId() + " Not Found");
            }
        }

        while (iterator.hasNext()) {
            float totalPrice = (iterator.next().getPerPrice() * iterator.previous().getQty());
            iterator.next().setTotalPrice(totalPrice);
        }

        productsRepo.saveAll(products);

        for (Products p : products) {
            ProductsDTO.builder()
                    .id(p.getId())
                    .name(p.getName())
                    .perPrice(p.getPerPrice())
                    .qty(p.getQty())
                    .totalPrice(p.getTotalPrice())
                    .build();
            productsDTOSList.add(modelMapper.map(p, ProductsDTO.class));
        }
        return productsDTOSList;
    }
}
