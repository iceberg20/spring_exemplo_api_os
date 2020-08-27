package com.iceberg.osapi.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iceberg.osapi.domain.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long>{
	Produto findById(long id);

}