package com.literalura.catalogo.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ResultadoDTO(
        List<BookDTO> results
) {}
