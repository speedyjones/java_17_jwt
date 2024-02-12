package com.hib.hibenatemysql.domains.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductsDTO {

    private int id;
    private String name;
    private float perPrice;
    private float qty;
    private float totalPrice;
}
