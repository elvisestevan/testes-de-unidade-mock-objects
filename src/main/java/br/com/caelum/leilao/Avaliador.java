package br.com.caelum.leilao;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import br.com.caelum.leilao.dominio.Lance;
import br.com.caelum.leilao.dominio.Leilao;

public class Avaliador {

    private double maiorLance = Double.NEGATIVE_INFINITY;
    private double menorLance = Double.POSITIVE_INFINITY;
    private List<Lance> maiores;

    public void avalia(Leilao leilao) {

        if (leilao.getLances().size() == 0) {
            throw new RuntimeException("Não é possível avaliar leilão sem lances");
        }

        leilao.getLances().forEach(lance -> {
            if (lance.getValor() < getMenorLance()) setMenorLance(lance.getValor());
            if (lance.getValor() > getMaiorLance()) setMaiorLance(lance.getValor());
        });

        maiores = new LinkedList<Lance>(leilao.getLances());
        Collections.sort(maiores, new Comparator<Lance> () {

            @Override
            public int compare(Lance arg0, Lance arg1) {
                if (arg0.getValor() > arg1.getValor()) return -1;
                if (arg0.getValor() < arg1.getValor()) return 1;
                return 0;
            }

        });

        if (maiores.size() > 3) {
            maiores = maiores.subList(0, 3);
        }

    }

    public Double getValorMedioLances(Leilao leilao) {
        return leilao
                .getLances()
                .parallelStream()
                .mapToDouble(lance -> lance.getValor())
                .average()
                .getAsDouble();
    }

    public double getMaiorLance() {
        return maiorLance;
    }

    public double getMenorLance() {
        return menorLance;
    }

    private void setMaiorLance(Double lance) {
        this.maiorLance = lance;
    }

    private void setMenorLance(Double lance) {
        this.menorLance = lance;
    }
    public List<Lance> getMaiores() {
        return this.maiores;
    }

}