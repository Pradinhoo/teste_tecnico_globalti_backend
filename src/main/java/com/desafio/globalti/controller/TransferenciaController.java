package com.desafio.globalti.controller;

import com.desafio.globalti.dto.TransferenciaDTO;
import com.desafio.globalti.model.Transferencia;
import com.desafio.globalti.service.TransferenciaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/transferencia")
public class TransferenciaController {

    private final TransferenciaService transferenciaService;

    public TransferenciaController(TransferenciaService transferenciaService) {
        this.transferenciaService = transferenciaService;
    }

    @PostMapping
    ResponseEntity<Map<String, String>> agendarNovaTransferencia(@RequestBody @Valid TransferenciaDTO transferenciaDTO) {
        transferenciaService.processarTransferencia(transferenciaDTO);
        Map<String, String> response = new HashMap<>();
        response.put("mensagem", "Transferência agendada com sucesso!");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    ResponseEntity<List<Transferencia>> listarTodasAsTransferencias() {
        List<Transferencia> listaDeTransferencias = transferenciaService.listarTodasAsTransferencias();
        return ResponseEntity.status(HttpStatus.OK).body(listaDeTransferencias);
    }
}
