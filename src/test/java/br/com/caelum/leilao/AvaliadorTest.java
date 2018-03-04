package br.com.caelum.leilao;

import br.com.caelum.leilao.builder.CriadorDeLeilao;
import br.com.caelum.leilao.dominio.Lance;
import br.com.caelum.leilao.dominio.Leilao;
import br.com.caelum.leilao.dominio.Usuario;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

;

public class AvaliadorTest {

    private Avaliador leiloeiro;
    private Usuario joao;
    private Usuario maria;
    private Usuario jose;

    @Before
    public void criaAvaliador() {
        this.leiloeiro = new Avaliador();
        this.joao = new Usuario("Joao");
        this.maria = new Usuario("Maria");
        this.jose = new Usuario("José");
    }

    @Test
    public void deveEntenderLancesEmOrdemAleatoria() {

        Leilao leilao = new CriadorDeLeilao().para("Playstation")
                .lance(joao, 500.0)
                .lance(maria, 250.0)
                .lance(jose, 400)
                .constroi();

        leiloeiro.avalia(leilao);

        MatcherAssert.assertThat(leiloeiro.getMaiorLance(), Matchers.equalTo(500.0));
        MatcherAssert.assertThat(leiloeiro.getMenorLance(), Matchers.equalTo(250.0));

    }

    @Test
    public void deveEntenderLancesEmOrdemCrescente() {

        Leilao leilao = new CriadorDeLeilao().para("Playstation")
                .lance(joao, 250.0)
                .lance(maria, 400.0)
                .lance(jose, 500)
                .constroi();

        leiloeiro.avalia(leilao);

        MatcherAssert.assertThat(leiloeiro.getMaiorLance(), Matchers.equalTo(500.0));
        MatcherAssert.assertThat(leiloeiro.getMenorLance(), Matchers.equalTo(250.0));

    }

    @Test
    public void deveEntenderLeilaoComApenasUmLance() {

        Leilao leilao = new CriadorDeLeilao().para("Playstation")
                .lance(joao, 1000.0)
                .constroi();

        leiloeiro.avalia(leilao);

        MatcherAssert.assertThat(leiloeiro.getMaiorLance(), Matchers.equalTo(1000.0));
        MatcherAssert.assertThat(leiloeiro.getMenorLance(), Matchers.equalTo(1000.0));
    }

    @Test
    public void deveCalcularAMediaDosLances() {

        Leilao leilao = new CriadorDeLeilao().para("Playstation")
                .lance(joao, 500.0)
                .lance(maria, 250.0)
                .lance(jose, 400)
                .constroi();

        double media = leiloeiro.getValorMedioLances(leilao);

        MatcherAssert.assertThat(media, Matchers.equalTo(383.33333333333333));

    }

    @Test
    public void deveEncontrarOsMaioresLances() {

        Leilao leilao = new CriadorDeLeilao().para("Playstation")
                .lance(joao, 500.0)
                .lance(maria, 250.0)
                .lance(jose, 400.0)
                .lance(maria, 600.0)
                .constroi();

        leiloeiro.avalia(leilao);

        List<Lance> maiores = leiloeiro.getMaiores();

        MatcherAssert.assertThat(leiloeiro.getMaiores().size(), Matchers.equalTo(3));
        MatcherAssert.assertThat(leiloeiro.getMaiores(), Matchers.hasItems(
                new Lance(maria, 600.0),
                new Lance(jose, 400.0),
                new Lance(joao, 500.0)
        ));

    }

    @Test(expected = RuntimeException.class)
    public void naoDeveAvaliarLeiloesSemLance() {

        Leilao leilao = new CriadorDeLeilao().para("Playstation").constroi();

        this.leiloeiro.avalia(leilao);
    }

}