package br.com.caelum.leilao.servico;

import br.com.caelum.leilao.builder.CriadorDeLeilao;
import br.com.caelum.leilao.dominio.Leilao;
import br.com.caelum.leilao.dominio.Pagamento;
import br.com.caelum.leilao.dominio.Usuario;
import br.com.caelum.leilao.infra.dao.RepositorioDeLeiloes;
import br.com.caelum.leilao.infra.dao.RepositorioDePagamentos;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class GeradorDePagamentoTest {

    @Test
    public void geraPagamento() {
        Leilao leilao = new CriadorDeLeilao().para("Playstation")
                .lance(new Usuario("Maria"), 2000.0)
                .lance(new Usuario("José"), 2100.0)
                .constroi();

        RepositorioDeLeiloes repositorioDeLeiloes = mock(RepositorioDeLeiloes.class);
        RepositorioDePagamentos repositorioDePagamentos = mock(RepositorioDePagamentos.class);
        Avaliador avaliador = mock(Avaliador.class);

        Mockito.when(repositorioDeLeiloes.encerrados()).thenReturn(Arrays.asList(leilao));
        Mockito.when(avaliador.getMaiorLance()).thenReturn(2100.0);

        GeradorDePagamento geradorDePagamento = new GeradorDePagamento(repositorioDeLeiloes, repositorioDePagamentos, avaliador);

        geradorDePagamento.gera();

        ArgumentCaptor<Pagamento> argumentCaptor = ArgumentCaptor.forClass(Pagamento.class);

        verify(repositorioDePagamentos).salvar(argumentCaptor.capture());
        assertEquals(2100.0, argumentCaptor.getValue().getValor(), 0.0001);
    }
}
