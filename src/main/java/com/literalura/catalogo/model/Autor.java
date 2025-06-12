package com.literalura.catalogo.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private Integer nascimento;
    private Integer falecimento;

    @ManyToMany(mappedBy = "autores")
    private List<Livro> livros;

    public Autor() {}

    public Autor(String nome, Integer nascimento, Integer falecimento) {
        this.nome = nome;
        this.nascimento = nascimento;
        this.falecimento = falecimento;
    }

    // Getters e Setters
    public Long getId() { return id; }

    public String getNome() { return nome; }

    public void setNome(String nome) { this.nome = nome; }

    public Integer getNascimento() { return nascimento; }

    public void setNascimento(Integer nascimento) { this.nascimento = nascimento; }

    public Integer getFalecimento() { return falecimento; }

    public void setFalecimento(Integer falecimento) { this.falecimento = falecimento; }

    public List<Livro> getLivros() { return livros; }

    public void setLivros(List<Livro> livros) { this.livros = livros; }

    @Override
    public String toString() {
        return """
        ----- AUTOR -----
        Nome: %s
        Ano de nascimento: %s
        Ano de falecimento: %s
        ------------------
        """.formatted(
                nome != null ? nome : "Desconhecido",
                nascimento != null ? nascimento : "N/A",
                falecimento != null ? falecimento : "Vivo"
        );
    }

}
