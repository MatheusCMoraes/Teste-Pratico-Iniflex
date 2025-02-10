package entities;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class Funcionario extends Pessoa {

	private BigDecimal salario;
	private String funcao;
	
	
	
	public Funcionario(String nome, String dataNascimento, Double salario, String funcao) {
		super(nome, dataNascimento);
		this.salario = new BigDecimal(salario);
		this.funcao = funcao;
	}

	public BigDecimal getSalario() {
		return salario;
	}

	public void setSalario(BigDecimal salario) {
		this.salario = salario;
	}

	public String getFuncao() {
		return funcao;
	}

	public void setFuncao(String funcao) {
		this.funcao = funcao;
	}
	
    @Override
    public String toString() {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(new Locale("pt", "BR"));
        symbols.setDecimalSeparator(',');
        symbols.setGroupingSeparator('.');

        DecimalFormat df = new DecimalFormat("#,##0.00", symbols);
        return "Nome: " + getNome() + ", Data Nascimento: " + getDataNascimento() + ", Salário: R$ " + df.format(salario) + ", Função: " + getFuncao();



    }
}
