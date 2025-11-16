package model;
import java.time.LocalDateTime;

public class Manutencao {
    private LocalDateTime data;
    private float custo;
    private String detalhesManutencao;

    public Manutencao(LocalDateTime data, float custo, String detalhesManutencao) {
        this.data = data;
        this.custo = custo;
        this.detalhesManutencao = detalhesManutencao;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
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

    @Override
    public String toString() {
        return "Manutencao{" +
                "data=" + data +
                ", custo=" + custo +
                ", detalhesManutencao='" + detalhesManutencao + '\'' +
                '}';
    }
}