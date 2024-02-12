package com.hib.hibenatemysql.repo;

import com.hib.hibenatemysql.domains.entity.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductsRepo extends JpaRepository<Products, Integer> {


}
