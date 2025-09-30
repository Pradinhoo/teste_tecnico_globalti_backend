package com.desafio.globalti.service;

import com.desafio.globalti.dto.TransferenciaDTO;
import com.desafio.globalti.model.Transferencia;

import java.util.List;

public interface TransferenciaService {
    void processarTransferencia(TransferenciaDTO transferenciaDTO);
    List<Transferencia> listarTodasAsTransferencias();
}
