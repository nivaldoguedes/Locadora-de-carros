package Model;
import java.time.LocalDateTime;

public class Manutencao {
    LocalDateTime data;
    float custo;
    String detalhesManutencao;

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
}