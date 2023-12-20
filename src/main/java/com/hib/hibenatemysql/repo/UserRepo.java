package com.hib.hibenatemysql.repo;

import com.hib.hibenatemysql.domains.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<Users, Integer> {

    @Query(value = "select * from users order by id desc limit 1", nativeQuery = true)
    Optional<Users> findLastId();

    Optional<Users> findByUserId(String userid);

    @Query(value = "select * from users where userId = ?1", nativeQuery = true)
    Users findUser(String userId);


}
