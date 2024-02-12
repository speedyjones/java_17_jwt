package com.hib.hibenatemysql.domains.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Table(name = "products")
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Products extends PreDefined {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private float perPrice;
    private float qty;
    private float totalPrice;


}
