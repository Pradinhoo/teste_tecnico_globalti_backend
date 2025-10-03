package com.desafio.globalti.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;

public class TransferenciaDTO {

    @NotBlank(message = "O campo CONTA DE ORIGEM é obrigatório")
    @Size(min = 10, max = 10, message = "O campo CONTA DE ORIGEM deve seguir o padrão XXXXXXXXXX")
    private String contaOrigem;

    @NotBlank (message = "O campo CONTA DE DESTINO é obrigatório")
    @Size(min = 10, max = 10, message = "O campo CONTA DE DESTINO deve seguir o padrão XXXXXXXXXX")
    private String contaDestino;

    @NotNull(message = "O campo VALOR é obrigatório")
    @Positive(message = "O valor deve ser maior que zero!")
    private Double valor;

    @NotNull (message = "O campo DATA DA TRANSFERÊNCIA é obrigatório")
    private LocalDate dataDaTransferencia;

    public TransferenciaDTO(String contaOrigem, String contaDestino, Double valor, LocalDate dataDaTransferencia, LocalDate dataDeAgendamento) {
        this.contaOrigem = contaOrigem;
        this.contaDestino = contaDestino;
        this.valor = valor;
        this.dataDaTransferencia = dataDaTransferencia;
        this.dataDeAgendamento = dataDeAgendamento;
    }

    private LocalDate dataDeAgendamento = LocalDate.now();

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
}
