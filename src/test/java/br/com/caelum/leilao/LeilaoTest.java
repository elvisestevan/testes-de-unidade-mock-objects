package br.com.caelum.leilao;

import br.com.caelum.leilao.dominio.Lance;
import br.com.caelum.leilao.dominio.Leilao;
import br.com.caelum.leilao.dominio.Usuario;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LeilaoTest {

    @Test
    public void deveReceberUmLance() {
        Leilao leilao = new Leilao("Macbook Pro");

        assertEquals(0, leilao.getLances().size());

        leilao.propoe(new Lance(new Usuario("Steve Jobs"), 2000.0));

        assertEquals(1, leilao.getLances().size());
        assertEquals(2000.0, leilao.getLances().get(0).getValor(), 0.00001);

    }

    @Test
    public void deveReceberVariosLances() {
        Leilao leilao = new Leilao("Macbook Pro");

        leilao.propoe(new Lance(new Usuario("Steve Jobs"), 2000.0));
        leilao.propoe(new Lance(new Usuario("Steve Wozniak"), 3000.0));

        assertEquals(2, leilao.getLances().size());
        assertEquals(2000.0, leilao.getLances().get(0).getValor(), 0.0001);
        assertEquals(3000.0, leilao.getLances().get(1).getValor(), 0.0001);
    }

    @Test
    public void naoDeveAceitarDoisLancesSeguidosDoMesmoUsuario() {
        Leilao leilao = new Leilao("Macbook pro");
        Usuario jobs = new Usuario("Steve Jobs");

        leilao.propoe(new Lance(jobs, 2000.0));
        leilao.propoe(new Lance(jobs, 3000.0));

        assertEquals(1, leilao.getLances().size());
        assertEquals(2000.0, leilao.getLances().get(0).getValor(), 0.00001);
    }

    @Test
    public void naoDeveAceitarMaisDeCincoLancesDoMesmoUsuario() {
        Leilao leilao = new Leilao("Macbook Pro");

        Usuario jobs = new Usuario("Steve Jobs");
        Usuario gates = new Usuario("Bill Gates");

        leilao.propoe(new Lance(jobs, 2000.0));
        leilao.propoe(new Lance(gates, 3000.0));

        leilao.propoe(new Lance(jobs, 3000.0));
        leilao.propoe(new Lance(gates, 4000.0));

        leilao.propoe(new Lance(jobs, 5000.0));
        leilao.propoe(new Lance(gates, 6000.0));

        leilao.propoe(new Lance(jobs, 7000.0));
        leilao.propoe(new Lance(gates, 8000.0));

        leilao.propoe(new Lance(jobs, 9000.0));
        leilao.propoe(new Lance(gates, 10000.0));

        leilao.propoe(new Lance(jobs, 11000.0));

        assertEquals(10, leilao.getLances().size());
        assertEquals(10000.0, leilao.getUltimoLanceDado().getValor(), 0.0001);

    }

}