package br.com.caelum.leilao.servico;

import br.com.caelum.leilao.builder.CriadorDeLeilao;
import br.com.caelum.leilao.dominio.Leilao;
import br.com.caelum.leilao.infra.dao.EnviadorDeEmail;
import br.com.caelum.leilao.infra.dao.RepositorioDeLeiloes;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

public class EncerradorDeLeilaoTest {

    @Test
    public void naoFazNadaCasoNaoHajaLeilao() {
        List<Leilao> leiloes = new LinkedList<>();

        RepositorioDeLeiloes dao = Mockito.mock(RepositorioDeLeiloes.class);
        EnviadorDeEmail carteiro = Mockito.mock(EnviadorDeEmail.class);

        Mockito.when(dao.correntes()).thenReturn(leiloes);

        EncerradorDeLeilao encerradorDeLeilao = new EncerradorDeLeilao(dao, carteiro);
        encerradorDeLeilao.encerra();

        assertEquals(0, encerradorDeLeilao.getTotalEncerrados());

    }

    @Test
    public void naoDeveEncerrarLeiloesQueComecaramOntem() {
        Calendar ontem = Calendar.getInstance();
        ontem.add(Calendar.DAY_OF_YEAR, -1);

        Leilao leilao1 = new CriadorDeLeilao().para("TV de plasma").naData(ontem).constroi();
        Leilao leilao2 = new CriadorDeLeilao().para("Geladeira").naData(ontem).constroi();

        List<Leilao> leiloesDeOntem = Arrays.asList(leilao1, leilao2);

        RepositorioDeLeiloes dao = Mockito.mock(RepositorioDeLeiloes.class);
        EnviadorDeEmail carteiro = Mockito.mock(EnviadorDeEmail.class);

        Mockito.when(dao.correntes()).thenReturn(leiloesDeOntem);

        EncerradorDeLeilao encerradorDeLeilao = new EncerradorDeLeilao(dao, carteiro);
        encerradorDeLeilao.encerra();

        assertEquals(0, encerradorDeLeilao.getTotalEncerrados());
        assertFalse(leilao1.isEncerrado());
        assertFalse(leilao2.isEncerrado());

        InOrder inOrder = Mockito.inOrder(dao, carteiro);

        inOrder.verify(dao, Mockito.never()).atualiza(leilao1);
        inOrder.verify(dao, Mockito.never()).atualiza(leilao2);
        inOrder.verify(carteiro, Mockito.never()).envia(leilao1);
        inOrder.verify(carteiro, Mockito.never()).envia(leilao2);
    }

    @Test
    public void deveEncerrarLeiloesQueComecaramUmaSemanaAntes() {
        Calendar antiga = Calendar.getInstance();
        antiga.set(1999,1,20);

        Leilao leilao1 = new CriadorDeLeilao().para("TV de plasma").naData(antiga).constroi();
        Leilao leilao2 = new CriadorDeLeilao().para("Geladeira").naData(antiga).constroi();
        List<Leilao> leiloesAntigos = Arrays.asList(leilao1, leilao2);

        RepositorioDeLeiloes dao = Mockito.mock(RepositorioDeLeiloes.class);
        EnviadorDeEmail carteiro = Mockito.mock(EnviadorDeEmail.class);

        Mockito.when(dao.correntes()).thenReturn(leiloesAntigos);

        EncerradorDeLeilao encerradorDeLeilao = new EncerradorDeLeilao(dao, carteiro);
        encerradorDeLeilao.encerra();

        assertEquals(2, encerradorDeLeilao.getTotalEncerrados());
        assertTrue(leilao1.isEncerrado());
        assertTrue(leilao2.isEncerrado());

        InOrder inOrder = Mockito.inOrder(dao, carteiro);

        inOrder.verify(dao, Mockito.times(1)).atualiza(leilao1);
        inOrder.verify(carteiro, Mockito.times(1)).envia(leilao1);
        inOrder.verify(dao, Mockito.times(1)).atualiza(leilao2);
        inOrder.verify(carteiro, Mockito.times(1)).envia(leilao2);

    }

    @Test
    public void deveAtualizarLeiloesEncerrados() {
        Calendar antiga = Calendar.getInstance();
        antiga.set(1999,1,20);

        Leilao leilao1 = new CriadorDeLeilao().para("TV de Plasma").naData(antiga).constroi();

        RepositorioDeLeiloes dao = Mockito.mock(RepositorioDeLeiloes.class);
        EnviadorDeEmail carteiro = Mockito.mock(EnviadorDeEmail.class);

        Mockito.when(dao.correntes()).thenReturn(Arrays.asList(leilao1));

        EncerradorDeLeilao encerradorDeLeilao = new EncerradorDeLeilao(dao, carteiro);
        encerradorDeLeilao.encerra();

        InOrder inOrder = Mockito.inOrder(dao, carteiro);

        inOrder.verify(dao, Mockito.times(1)).atualiza(leilao1);
        inOrder.verify(carteiro, Mockito.times(1)).envia(leilao1);

    }

}
