package br.com.caelum.leilao.infra.dao;

import java.util.ArrayList;
import java.util.List;

import br.com.caelum.leilao.dominio.Leilao;

public class LeilaoDaoFalso implements RepositorioDeLeiloes {

    private static List<Leilao> leiloes = new ArrayList<Leilao>();;

    @Override
    public void salva(Leilao leilao) {
        leiloes.add(leilao);
    }

    @Override
    public List<Leilao> encerrados() {

        List<Leilao> filtrados = new ArrayList<Leilao>();
        for(Leilao leilao : leiloes) {
            if(leilao.isEncerrado()) filtrados.add(leilao);
        }

        return filtrados;
    }

    @Override
    public List<Leilao> correntes() {

        List<Leilao> filtrados = new ArrayList<Leilao>();
        for(Leilao leilao : leiloes) {
            if(!leilao.isEncerrado()) filtrados.add(leilao);
        }

        return filtrados;
    }

    @Override
    public void atualiza(Leilao leilao) { /* faz nada! */ }
}
