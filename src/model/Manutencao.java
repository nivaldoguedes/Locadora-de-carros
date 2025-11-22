package model;
import java.time.LocalDate;

public class Manutencao {
    private LocalDate data;
    private float custo;
    private String detalhesManutencao;
    private Veiculo v1;

    public Manutencao(LocalDate data, float custo, String detalhesManutencao) {
        this.data = data;
        this.custo = custo;
        this.detalhesManutencao = detalhesManutencao;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public float getCusto() {
        return custo;
    }

    public void setCusto(float custo) {
        this.custo = custo;
    }

    public String getDetalhesManutencao() {
        return detalhesManutencao;
    }

    public void setDetalhesManutencao(String detalhesManutencao) {
        this.detalhesManutencao = detalhesManutencao;
    }

    public Veiculo getVeiculo() { return v1; }

    public void setVeiculo(Veiculo v1) { this.v1 = v1; }

    @Override
    public String toString() {
        return "Manutencao{" +
                "data=" + data +
                ", custo=" + custo +
                ", detalhesManutencao='" + detalhesManutencao + '\'' +
                '}';
    }
}