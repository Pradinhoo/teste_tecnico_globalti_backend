package com.desafio.globalti.service;

import com.desafio.globalti.dto.TransferenciaDTO;
import com.desafio.globalti.exception.ContaDeDestinoInvalidaException;
import com.desafio.globalti.exception.DataDeTransferenciaInvalidaException;
import com.desafio.globalti.exception.TaxaNaoAplicavelException;
import com.desafio.globalti.model.Transferencia;
import com.desafio.globalti.repository.TransferenciaRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class TransferenciaServiceImpl implements TransferenciaService {

    private final TransferenciaRepository transferenciaRepository;

    public TransferenciaServiceImpl(TransferenciaRepository transferenciaRepository) {
        this.transferenciaRepository = transferenciaRepository;
    }

    private Double calculaValorTaxa(Double valor, LocalDate dataDaTransferencia, LocalDate dataDeAgendamento) {
        if (dataDaTransferencia.isBefore(dataDeAgendamento)) {
            throw new DataDeTransferenciaInvalidaException();
        }

        long dias = ChronoUnit.DAYS.between(dataDeAgendamento, dataDaTransferencia);

        if (dias == 0) {
            return 3 + (valor * 0.025);
        } else if (dias <= 10) {
            return 12.0;
        } else if (dias <= 20) {
            return valor * 0.082;
        } else if (dias <= 30) {
            return valor * 0.069;
        } else if (dias <= 40) {
            return valor * 0.047;
        } else if (dias <= 50) {
            return valor * 0.017;
        } else {
            return 0.0;
        }
    }

    public void processarTransferencia(TransferenciaDTO transferenciaDTO) {
        if (transferenciaDTO.getContaOrigem().equals(transferenciaDTO.getContaDestino())) {
            throw new ContaDeDestinoInvalidaException();
        }

        Double taxa = calculaValorTaxa(
                transferenciaDTO.getValor(),
                transferenciaDTO.getDataDaTransferencia(),
                transferenciaDTO.getDataDeAgendamento());

        if (taxa.equals(0.0)) {
            throw new TaxaNaoAplicavelException();
        }

        BigDecimal bdTaxa = BigDecimal.valueOf(taxa).setScale(2, RoundingMode.DOWN);
        Double taxaArredondada = bdTaxa.doubleValue();

        Transferencia novaTransferencia = new Transferencia(
                transferenciaDTO.getContaOrigem(),
                transferenciaDTO.getContaDestino(),
                transferenciaDTO.getValor(),
                transferenciaDTO.getDataDaTransferencia(),
                transferenciaDTO.getDataDeAgendamento(),
                taxaArredondada
        );

        transferenciaRepository.save(novaTransferencia);
    }

    public List<Transferencia> listarTodasAsTransferencias() {
        return transferenciaRepository.findAll();
    }
}
