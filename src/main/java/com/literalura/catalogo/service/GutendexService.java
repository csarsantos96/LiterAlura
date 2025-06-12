package com.literalura.catalogo.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.literalura.catalogo.dto.AuthorDTO;
import com.literalura.catalogo.dto.BookDTO;
import com.literalura.catalogo.dto.ResultadoDTO;
import com.literalura.catalogo.model.Autor;
import com.literalura.catalogo.model.Livro;
import com.literalura.catalogo.repository.AutorRepository;
import com.literalura.catalogo.repository.LivroRepository;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@Service
public class GutendexService {

    private final LivroRepository livroRepository;
    private final AutorRepository autorRepository;
    private final ObjectMapper mapper = new ObjectMapper();

    public GutendexService(LivroRepository livroRepository, AutorRepository autorRepository) {
        this.livroRepository = livroRepository;
        this.autorRepository = autorRepository;
    }

    public void buscarSalvarLivroPorTitulo(String titulo) {
        try {
            String url = "https://gutendex.com/books/?search=" + titulo.replace(" ", "+");
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            ResultadoDTO resultado = mapper.readValue(response.body(), ResultadoDTO.class);

            if (resultado.results().isEmpty()) {
                System.out.println("Nenhum livro encontrado com esse título.");
                return;
            }

            BookDTO livroApi = resultado.results().get(0);

            // Verifica se o livro já está cadastrado
            if (livroRepository.existsByTitulo(livroApi.getTitle())) {
                System.out.println("Livro já cadastrado.");
                return;
            }

            // Cria e salva autores (pode haver mais de um)
            List<Autor> autores = livroApi.getAuthors().stream()
                    .map(autorApi -> autorRepository.findByNome(autorApi.getName())
                            .orElseGet(() -> autorRepository.save(new Autor(
                                    autorApi.getName(),
                                    autorApi.getBirth_year(),
                                    autorApi.getDeath_year()
                            )))
                    ).toList();

            Livro livro = new Livro(
                    livroApi.getTitle(),
                    List.of(livroApi.getLanguages().get(0)), // só pega o primeiro idioma
                    livroApi.getDownload_count(),
                    autores,
                    livroApi.getSubjects()
            );

            livroRepository.save(livro);
            System.out.println("Livro salvo com sucesso: " + livro.getTitulo());

        } catch (Exception e) {
            System.out.println("Erro ao buscar ou salvar o livro: " + e.getMessage());
        }
    }
}
