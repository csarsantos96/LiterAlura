package com.literalura.catalogo.service;

import com.literalura.catalogo.dto.ResultadoDTO;
import org.springframework.web.client.RestTemplate;

public class ConsumoAPI {
    private static final String URL_BASE = "https://gutendex.com/books/?search=";

    public ResultadoDTO buscarLivroPorTitulo(String titulo) {
        RestTemplate restTemplate = new RestTemplate();
        var url = URL_BASE + titulo.replace(" ", "%20");
        return restTemplate.getForObject(url, ResultadoDTO.class);
    }
}
