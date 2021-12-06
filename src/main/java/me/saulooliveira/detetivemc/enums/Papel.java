package me.saulooliveira.detetivemc.enums;

public enum Papel {

    SUSPEITO(1, "Suspeito"),
    TRAIDOR(2, "Traidor"),
    DETETIVE(3, "Detetive");

    private final Integer value;
    private final String descricao;

    Papel(Integer value, String descricao) {
        this.value = value;
        this.descricao = descricao;
    }

    public Integer getValue() {
        return value;
    }

    public String getDescricao() {
        return descricao;
    }

    public static Papel fromValue(Integer value) {
        for (Papel papel : values()) {
            if (papel.value.equals(value)) {
                return papel;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "Papel{" +
                "value=" + value +
                ", descricao='" + descricao + '\'' +
                '}';
    }
}
