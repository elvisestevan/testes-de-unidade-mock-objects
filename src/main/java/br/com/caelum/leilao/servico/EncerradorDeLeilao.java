package br.com.caelum.leilao.servico;

import br.com.caelum.leilao.dominio.Leilao;
import br.com.caelum.leilao.infra.dao.RepositorioDeLeiloes;
import br.com.caelum.leilao.infra.email.Carteiro;

import java.util.Calendar;
import java.util.List;

public class EncerradorDeLeilao {

    private RepositorioDeLeiloes dao;
    private Carteiro carteiro;

    public EncerradorDeLeilao(RepositorioDeLeiloes dao, Carteiro carteiro) {
        this.dao = dao;
        this.carteiro = carteiro;
    }

    private int total = 0;

    public void encerra() {
        List<Leilao> todosLeiloesCorrentes = dao.correntes();

        for (Leilao leilao : todosLeiloesCorrentes) {
            try {
                if (comecouSemanaPassada(leilao)) {
                    leilao.encerra();
                    total++;
                    dao.atualiza(leilao);
                    carteiro.envia(leilao);
                }
            } catch(Exception e) {
                //loga exception e continua...
            }

        }
    }

    private boolean comecouSemanaPassada(Leilao leilao) {
        return diasEntre(leilao.getData(), Calendar.getInstance()) >= 7;
    }

    private int diasEntre(Calendar inicio, Calendar fim) {
        Calendar data = (Calendar) inicio.clone();
        int diasNoIntervalo = 0;
        while (data.before(fim)) {
            data.add(Calendar.DAY_OF_MONTH, 1);
            diasNoIntervalo++;
        }

        return diasNoIntervalo;
    }

    public int getTotalEncerrados() {
        return total;
    }
}
