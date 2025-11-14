package Model;
import java.time.LocalDateTime;

public class Aluga {
    private LocalDateTime dataAluguel;
    private LocalDateTime dataDevolucao;
    private float valor;
    private String formaPagamento;

    public Aluga(LocalDateTime dataAluguel, LocalDateTime dataDevolucao, float valor, String formaPagamento) {
        this.dataAluguel = dataAluguel;
        this.dataDevolucao = dataDevolucao;
        this.valor = valor;
        this.formaPagamento = formaPagamento;
    }

    public LocalDateTime getDataAluguel() {
        return dataAluguel;
    }

    public void setDataAluguel(LocalDateTime dataAluguel) {
        this.dataAluguel = dataAluguel;
    }

    public LocalDateTime getDataDevolucao() {
        return dataDevolucao;
    }

    public void setDataDevolucao(LocalDateTime dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public String getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(String formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    @Override
    public String toString() {
        return "Aluga{" +
                "dataAluguel=" + dataAluguel +
                ", dataDevolucao=" + dataDevolucao +
                ", valor=" + valor +
                ", formaPagamento='" + formaPagamento + '\'' +
                '}';
    }
}