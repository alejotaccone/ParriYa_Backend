package com.parriya.parriya_api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.parriya.parriya_api.entidades.Categoria;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    
    @Query(value = "select c from Categoria c where c.id = ?1")
    Optional<Categoria> findById(Long id);

}
