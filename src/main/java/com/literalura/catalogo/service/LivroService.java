package com.literalura.catalogo.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.literalura.catalogo.model.Autor;
import com.literalura.catalogo.model.Livro;
import com.literalura.catalogo.repository.AutorRepository;
import com.literalura.catalogo.repository.LivroRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

@Service
public class LivroService {

    private final LivroRepository livroRepository;
    private final AutorRepository autorRepository;
    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper mapper = new ObjectMapper();

    public LivroService(LivroRepository livroRepository, AutorRepository autorRepository) {
        this.livroRepository = livroRepository;
        this.autorRepository = autorRepository;
    }

    public void buscarSalvarLivroPorTitulo(String titulo) {
        String url = "https://gutendex.com/books/?search=" + titulo.replace(" ", "%20");

        try {
            String json = restTemplate.getForObject(url, String.class);
            JsonNode root = mapper.readTree(json);
            JsonNode resultados = root.path("results");

            if (!resultados.isArray() || resultados.isEmpty()) {
                System.out.println("Livro não encontrado.");
                return;
            }

            JsonNode livroJson = resultados.get(0);

            String tituloLivro = livroJson.path("title").asText();
            int numeroDownloads = livroJson.path("download_count").asInt();

            // Pega apenas o primeiro idioma
            String idioma = livroJson.path("languages").isArray() && livroJson.path("languages").size() > 0
                    ? livroJson.path("languages").get(0).asText()
                    : "desconhecido";

            // Pega apenas o primeiro assunto (pode adaptar se quiser mais)
            List<String> assuntos = mapper.convertValue(livroJson.path("subjects"), List.class);

            // Pega apenas o primeiro autor
            JsonNode autorJson = livroJson.path("authors").isArray() && livroJson.path("authors").size() > 0
                    ? livroJson.path("authors").get(0)
                    : null;

            if (autorJson == null) {
                System.out.println("Autor não encontrado.");
                return;
            }

            String nomeAutor = autorJson.path("name").asText();
            Integer nascimento = autorJson.path("birth_year").isNull() ? null : autorJson.path("birth_year").asInt();
            Integer falecimento = autorJson.path("death_year").isNull() ? null : autorJson.path("death_year").asInt();

            Autor autor = autorRepository.findByNome(nomeAutor)
                    .orElseGet(() -> autorRepository.save(new Autor(nomeAutor, nascimento, falecimento)));

            // Verifica duplicata
            if (livroRepository.existsByTitulo(tituloLivro)) {
                System.out.println("Livro já cadastrado.");
                return;
            }

            Livro livro = new Livro(
                    tituloLivro,
                    Collections.singletonList(idioma),
                    numeroDownloads,
                    Collections.singletonList(autor),
                    assuntos
            );

            livroRepository.save(livro);
            System.out.println("Livro salvo com sucesso: " + tituloLivro);

        } catch (Exception e) {
            System.out.println("Erro ao buscar ou salvar o livro: " + e.getMessage());
        }
    }
}
