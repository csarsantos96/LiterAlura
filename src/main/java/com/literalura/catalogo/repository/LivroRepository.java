package com.literalura.catalogo.repository;

import com.literalura.catalogo.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LivroRepository extends JpaRepository<Livro, Long> {
    boolean existsByTitulo(String titulo);
    List<Livro> findByIdiomasContaining(String idioma);
}
