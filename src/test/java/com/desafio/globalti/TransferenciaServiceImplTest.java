package com.desafio.globalti;

import com.desafio.globalti.dto.TransferenciaDTO;
import com.desafio.globalti.exception.ContaDeDestinoInvalidaException;
import com.desafio.globalti.exception.DataDeTransferenciaInvalidaException;
import com.desafio.globalti.exception.TaxaNaoAplicavelException;
import com.desafio.globalti.model.Transferencia;
import com.desafio.globalti.repository.TransferenciaRepository;
import com.desafio.globalti.service.TransferenciaServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TransferenciaServiceImplTest {

    private TransferenciaRepository repository;
    private TransferenciaServiceImpl service;

    @BeforeEach
    void setup() {
        repository = mock(TransferenciaRepository.class);
        service = new TransferenciaServiceImpl(repository);
    }

    private TransferenciaDTO buildDTO(Double valor, LocalDate dataAgendamento, LocalDate dataTransferencia) {
        return new TransferenciaDTO(
                "1234567890",
                "0987654231",
                valor,
                dataTransferencia,
                dataAgendamento
        );
    }

    @Test
    void deveSalvarTransferenciaComSucesso() {
        TransferenciaDTO dto = buildDTO(
                100.0,
                LocalDate.now(),
                LocalDate.now());

        service.processarTransferencia(dto);

        ArgumentCaptor<Transferencia> captor = ArgumentCaptor.forClass(Transferencia.class);
        verify(repository, times(1)).save(captor.capture());

        Transferencia transferenciaSalva = captor.getValue();

        assertEquals("1234567890", transferenciaSalva.getContaOrigem());
        assertEquals("0987654231", transferenciaSalva.getContaDestino());
        assertEquals(100.0, transferenciaSalva.getValor());
        assertEquals(5.5, transferenciaSalva.getValorTaxa());
    }

    @Test
    void deveLancarExcecaoSeContaOrigemIgualDestino() {
        TransferenciaDTO dto = new TransferenciaDTO(
                "1234567890",
                "1234567890",
                100.0,
                LocalDate.now(),
                LocalDate.now());

        assertThrows(ContaDeDestinoInvalidaException.class,
                () -> service.processarTransferencia(dto));
    }

    @Test
    void deveLancarExcecaoSeDataTransferenciaAntesDoAgendamento() {
        TransferenciaDTO dto = buildDTO(
                100.0,
                LocalDate.now(),
                LocalDate.now().minusDays(1));

        assertThrows(DataDeTransferenciaInvalidaException.class,
                () -> service.processarTransferencia(dto));
    }

    @Test
    void deveLancarExcecaoSeTaxaNaoAplicavel() {
        TransferenciaDTO dto = buildDTO(
                100.0,
                LocalDate.now(),
                LocalDate.now().plusDays(60));

        assertThrows(TaxaNaoAplicavelException.class,
                () -> service.processarTransferencia(dto));
    }

    @Test
    void deveCalcularTaxaMesmoDia() {
        TransferenciaDTO dto = buildDTO(
                100.0,
                LocalDate.now(),
                LocalDate.now());

        service.processarTransferencia(dto);

        ArgumentCaptor<Transferencia> captor = ArgumentCaptor.forClass(Transferencia.class);
        verify(repository).save(captor.capture());

        Transferencia transferencia = captor.getValue();
        assertEquals(5.5, transferencia.getValorTaxa());
    }

    @Test
    void deveCalcularTaxaAte10Dias() {
        TransferenciaDTO dto = buildDTO(
                100.0,
                LocalDate.now(),
                LocalDate.now().plusDays(5));

        service.processarTransferencia(dto);

        ArgumentCaptor<Transferencia> captor = ArgumentCaptor.forClass(Transferencia.class);
        verify(repository).save(captor.capture());

        assertEquals(12.0, captor.getValue().getValorTaxa());
    }

    @Test
    void deveCalcularTaxaEntre11e20Dias() {
        TransferenciaDTO dto = buildDTO(
                200.0,
                LocalDate.now(),
                LocalDate.now().plusDays(15));

        service.processarTransferencia(dto);

        ArgumentCaptor<Transferencia> captor = ArgumentCaptor.forClass(Transferencia.class);
        verify(repository).save(captor.capture());

        assertEquals(16.4, captor.getValue().getValorTaxa()); // 200 * 0.082
    }

    @Test
    void deveCalcularTaxaEntre21e30Dias() {
        TransferenciaDTO dto = buildDTO(
                200.0,
                LocalDate.now(),
                LocalDate.now().plusDays(25));

        service.processarTransferencia(dto);

        ArgumentCaptor<Transferencia> captor = ArgumentCaptor.forClass(Transferencia.class);
        verify(repository).save(captor.capture());

        assertEquals(13.8, captor.getValue().getValorTaxa());
    }

    @Test
    void deveCalcularTaxaEntre31e40Dias() {
        TransferenciaDTO dto = buildDTO(
                200.0,
                LocalDate.now(),
                LocalDate.now().plusDays(35));

        service.processarTransferencia(dto);

        ArgumentCaptor<Transferencia> captor = ArgumentCaptor.forClass(Transferencia.class);
        verify(repository).save(captor.capture());

        assertEquals(9.4, captor.getValue().getValorTaxa());
    }

    @Test
    void deveCalcularTaxaEntre41e50Dias() {
        TransferenciaDTO dto = buildDTO(
                200.0,
                LocalDate.now(),
                LocalDate.now().plusDays(45));

        service.processarTransferencia(dto);

        ArgumentCaptor<Transferencia> captor = ArgumentCaptor.forClass(Transferencia.class);
        verify(repository).save(captor.capture());

        assertEquals(3.4, captor.getValue().getValorTaxa());
    }
}
