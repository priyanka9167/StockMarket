package org.example.repository;

import org.example.model.Companies;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompaniesRepository extends JpaRepository<Companies, Long> {

    Companies findBySymbol(String symbol);


}
