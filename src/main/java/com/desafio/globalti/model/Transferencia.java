package com.desafio.globalti.model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
public class Transferencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String contaOrigem;
    private String contaDestino;
    private Double valor;
    private LocalDate dataDaTransferencia;
    private LocalDate dataDeAgendamento;
    private Double valorTaxa;

    public Transferencia() {
    }

    public Transferencia(String contaOrigem,
                         String contaDestino,
                         Double valor,
                         LocalDate dataDaTransferencia,
                         LocalDate dataDeAgendamento,
                         Double valorTaxa) {
        this.contaOrigem = contaOrigem;
        this.contaDestino = contaDestino;
        this.valor = valor;
        this.dataDaTransferencia = dataDaTransferencia;
        this.dataDeAgendamento = dataDeAgendamento;
        this.valorTaxa = valorTaxa;
    }

    public Long getId() {
        return id;
    }

    public String getContaOrigem() {
        return contaOrigem;
    }

    public String getContaDestino() {
        return contaDestino;
    }

    public Double getValor() {
        return valor;
    }

    public LocalDate getDataDaTransferencia() {
        return dataDaTransferencia;
    }

    public LocalDate getDataDeAgendamento() {
        return dataDeAgendamento;
    }

    public Double getValorTaxa() {
        return valorTaxa;
    }
}
