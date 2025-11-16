package model;

public class Devolucao {
    private float quilometragemFinal;
    private String danos;

    public Devolucao(float quilometragemFinal, String danos) {
        this.quilometragemFinal = quilometragemFinal;
        this.danos = danos;
    }

    public float getQuilometragemFinal() {
        return quilometragemFinal;
    }

    public void setQuilometragemFinal(float quilometragemFinal) {
        this.quilometragemFinal = quilometragemFinal;
    }

    public String getDanos() {
        return danos;
    }

    public void setDanos(String danos) {
        this.danos = danos;
    }

    @Override
    public String toString() {
        return "Devolucao{" +
                "quilometragemFinal=" + quilometragemFinal +
                ", danos='" + danos + '\'' +
                '}';
    }
}