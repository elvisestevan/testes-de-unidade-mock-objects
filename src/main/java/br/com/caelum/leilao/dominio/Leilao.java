package br.com.caelum.leilao.dominio;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Leilao {

    private String descricao;
    private List<Lance> lances;

    public Leilao(String descricao) {
        this.descricao = descricao;
        this.lances = new ArrayList<Lance>();
    }

    public void propoe(Lance lance) {

        if (this.lances.isEmpty() || podeDarLance(lance.getUsuario())) {
            lances.add(lance);
        }
    }

    private boolean podeDarLance(Usuario usuario) {
        long total = this.lances
                .stream()
                .filter((l) -> l.getUsuario().equals(usuario))
                .count();

        return !getUltimoLanceDado().getUsuario().equals(usuario) && total < 5L;
    }

    public Lance getUltimoLanceDado() {
        return lances.get(lances.size() - 1);
    }

    public String getDescricao() {
        return descricao;
    }

    public List<Lance> getLances() {
        return Collections.unmodifiableList(lances);
    }



}