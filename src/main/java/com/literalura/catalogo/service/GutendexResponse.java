package com.literalura.catalogo.service;

import com.literalura.catalogo.dto.BookDTO;
import java.util.List;

public record GutendexResponse(List<BookDTO> results) {}
