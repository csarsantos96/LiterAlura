package com.literalura.catalogo;

import com.literalura.catalogo.model.Livro;
import com.literalura.catalogo.model.Autor;
import com.literalura.catalogo.service.GutendexService;
import com.literalura.catalogo.repository.LivroRepository;
import com.literalura.catalogo.repository.AutorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class Principal implements CommandLineRunner {

    private final GutendexService livroService;
    private final LivroRepository livroRepository;
    private final AutorRepository autorRepository;

    public Principal(GutendexService livroService, LivroRepository livroRepository, AutorRepository autorRepository) {
        this.livroService = livroService;
        this.livroRepository = livroRepository;
        this.autorRepository = autorRepository;
    }

    @Override
    public void run(String... args) {
        Scanner scanner = new Scanner(System.in);
        String entrada;

        do {
            System.out.println("\n------------------------------");
            System.out.println("Escolha o número de sua opção:");
            System.out.println("1- Buscar livro pelo título");
            System.out.println("2- Listar livros registrados");
            System.out.println("3- Listar autores registrados");
            System.out.println("4- Listar autores vivos em um determinado ano");
            System.out.println("5- Listar livros em um determinado idioma");
            System.out.println("0- Sair");

            entrada = scanner.nextLine();

            switch (entrada) {
                case "1":
                    System.out.print("Digite o título do livro: ");
                    String titulo = scanner.nextLine();
                    livroService.buscarSalvarLivroPorTitulo(titulo);
                    break;

                case "2":
                    livroRepository.findAll().forEach(System.out::println);
                    break;

                case "3":
                    autorRepository.findAll().forEach(System.out::println);
                    break;

                case "4":
                    System.out.print("Digite o ano: ");
                    int ano = Integer.parseInt(scanner.nextLine());
                    autorRepository.buscarAutoresVivosPorAno(ano).forEach(System.out::println);
                    break;

                case "5":
                    System.out.print("Digite o idioma (ex: en, pt): ");
                    String idioma = scanner.nextLine();
                    livroRepository.findByIdiomasContaining(idioma).forEach(System.out::println);
                    break;

                case "0":
                    System.out.println("Encerrando aplicação...");
                    break;

                default:
                    System.out.println("Opção inválida.");
            }

        } while (!entrada.equals("0"));
    }
}