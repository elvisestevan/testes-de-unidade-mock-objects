package br.com.caelum.leilao.infra.dao;

import br.com.caelum.leilao.dominio.Leilao;

import java.util.List;

public interface RepositorioDeLeiloes {

    public void salva(Leilao leilao);

    public List<Leilao> encerrados();

    public List<Leilao> correntes();

    public void atualiza(Leilao leilao);

}
