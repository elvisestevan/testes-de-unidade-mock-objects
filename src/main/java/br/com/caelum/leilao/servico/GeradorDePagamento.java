package br.com.caelum.leilao.servico;

import br.com.caelum.leilao.dominio.Leilao;
import br.com.caelum.leilao.dominio.Pagamento;
import br.com.caelum.leilao.infra.dao.RepositorioDeLeiloes;
import br.com.caelum.leilao.infra.dao.RepositorioDePagamentos;

import java.util.Calendar;
import java.util.List;

public class GeradorDePagamento {

    private final RepositorioDeLeiloes repositorioDeLeiloes;
    private final RepositorioDePagamentos repositorioDePagamentos;
    private final Avaliador avaliador;

    public GeradorDePagamento(RepositorioDeLeiloes repositorioDeLeiloes, RepositorioDePagamentos repositorioDePagamentos, Avaliador avaliador) {
        this.repositorioDeLeiloes = repositorioDeLeiloes;
        this.repositorioDePagamentos = repositorioDePagamentos;
        this.avaliador = avaliador;
    }

    public void gera() {
        List<Leilao> encerrados = repositorioDeLeiloes.encerrados();

        for (Leilao leilao : encerrados) {
            avaliador.avalia(leilao);

            Pagamento pagamento = new Pagamento(avaliador.getMaiorLance(), Calendar.getInstance());
            repositorioDePagamentos.salvar(pagamento);
        }
    }

}
