package entities;

public enum MesesAno {

    JANEIRO(1),
    FEVEREIRO(2),
    MARCO(3),
    ABRIL(4),
    MAIO(5),
    JUNHO(6),
    JULHO(7),
    AGOSTO(8),
    SETEMBRO(9),
    OUTUBRO(10),
    NOVEMBRO(11),
    DEZEMBRO(12);

    private final int numero;  // Campo para armazenar o valor numérico do mês

    // Construtor para associar o valor numérico a cada mês
    MesesAno(int numero) {
        this.numero = numero;
    }

    // Método getter para acessar o número do mês
    public int getNumero() {
        return numero;
    }
}
