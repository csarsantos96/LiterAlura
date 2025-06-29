package com.literalura.catalogo.repository;

import com.literalura.catalogo.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AutorRepository extends JpaRepository<Autor, Long> {
    Optional<Autor> findByNome(String nome);



    @Query("SELECT a FROM Autor a WHERE a.nascimento <= :ano AND (a.falecimento IS NULL OR a.falecimento >= :ano)")
    List<Autor> buscarAutoresVivosPorAno(@Param("ano") int ano);

}
