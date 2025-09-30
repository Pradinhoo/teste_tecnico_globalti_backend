package com.desafio.globalti.controller;

import com.desafio.globalti.dto.TransferenciaDTO;
import com.desafio.globalti.model.Transferencia;
import com.desafio.globalti.service.TransferenciaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transferencia")
public class TransferenciaController {

    private final TransferenciaService transferenciaService;

    public TransferenciaController(TransferenciaService transferenciaService) {
        this.transferenciaService = transferenciaService;
    }

    @PostMapping
    ResponseEntity<String> agendarNovaTransferencia(@RequestBody @Valid TransferenciaDTO transferenciaDTO) {
        transferenciaService.processarTransferencia(transferenciaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("Transferencia agendada com sucesso!");
    }

    @GetMapping
    ResponseEntity<List<Transferencia>> listarTodasAsTransferencias() {
        List<Transferencia> listaDeTransferencias = transferenciaService.listarTodasAsTransferencias();
        return ResponseEntity.status(HttpStatus.OK).body(listaDeTransferencias);
    }
}
