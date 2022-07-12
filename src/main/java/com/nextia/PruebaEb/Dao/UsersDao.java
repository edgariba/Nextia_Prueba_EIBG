package com.nextia.PruebaEb.Dao;

import com.nextia.PruebaEb.Entity.UsersEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersDao extends JpaRepository<UsersEntity, Long> {
    Optional<UsersEntity> findByHashUser(String hashUser);
    Optional<UsersEntity> findByEmail(String email);
    boolean existsByEmail(String email);

    Page<UsersEntity> findAllByIsDeleted(boolean isDeleted, Pageable pageable);
}
