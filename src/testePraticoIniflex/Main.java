package testePraticoIniflex;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import entities.Funcionario;
import entities.MesesAno;

public class Main {

	private static DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	public static void main(String[] args) {
		Locale locale = Locale.forLanguageTag("pt-BR");
        
        // Configurando símbolos
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(locale);
        symbols.setDecimalSeparator(',');
        symbols.setGroupingSeparator('.');

        // Definindo o formato
        DecimalFormat df = new DecimalFormat("#,##0.00", symbols);

		List<Funcionario> quadroFuncionarios = new ArrayList<>();

		// 3.1 – Inserir todos os funcionários, na mesma ordem e informações da tabela
		// acima.
		
		String arquivo = "src/resources/listaFuncionarios.txt";
		
		try(BufferedReader br = new BufferedReader(new FileReader(arquivo))){
			
			String linha = br.readLine();
			
			while(linha != null) {
				
				// Separar os dados da linha usando o delimitador ";"
	            String[] dados = linha.split(";");

	            // Verifica se a linha possui dados suficientes (nome, data, salário e funcao)
	            if (dados.length == 4) {
	                String nome = dados[0];
	                String dataNascimento = dados[1];
	                double salario = Double.parseDouble(dados[2]);
	                String funcao = dados[3];
	                
	            	quadroFuncionarios.add(new Funcionario(nome, dataNascimento, salario, funcao));
	            }
				
	            linha = br.readLine();
			}
			
			
		}catch(IOException e) {
			System.out.println("Error:" + e.getMessage());
		}

		

		// 3.2 – Remover o funcionário “João” da lista.

		removerFuncionarioPeloNome("joão", quadroFuncionarios);
		System.out.println();
	

		// 3.3 – Imprimir todos os funcionários com todas suas informações, sendo que:
		// • informação de data deve ser exibido no formato dd/mm/aaaa;
		// • informação de valor numérico deve ser exibida no formatado com separador de
		// milhar como ponto e decimal como vírgula.
		System.out.println("Lista de funcionários");
		quadroFuncionarios.stream().forEach(System.out::println);

		// 3.4 – Os funcionários receberam 10% de aumento de salário, atualizar a lista
		// de funcionários com novo valor.
		
		System.out.println();
		System.out.println("Lista com os novos salarios");
		double aumentoPercentual = 0.1;
		ajustarSalario(aumentoPercentual, quadroFuncionarios);
		quadroFuncionarios.stream().forEach(System.out::println);

		// 3.5 – Agrupar os funcionários por função em um MAP, sendo a chave a “função”
		// e o valor a “lista de funcionários”.
		funcionariosPorFuncao(quadroFuncionarios);
		

		System.out.println();

		// 3.8 – Imprimir os funcionários que fazem aniversário no mês 10 e 12.

		System.out.println("Funcionarios que fazem aniversario em Outubro e Dezembro");
		aniversariantesMes(quadroFuncionarios, MesesAno.valueOf("OUTUBRO"), MesesAno.valueOf("DEZEMBRO"));
		

		// 3.9 – Imprimir o funcionário com a maior idade, exibir os atributos: nome e
		// idade.
		System.out.println();

		System.out.println("Funcionario mais velho");
		funcionarioMaisVelho(quadroFuncionarios);

		

		// 3.10 – Imprimir a lista de funcionários por ordem alfabética.
		System.out.println();
		System.out.println("Lista de funcionários por ordem alfabética");

		quadroFuncionarios.stream().sorted(Comparator.comparing(Funcionario::getNome))
				.forEach(funcionario -> System.out.println(funcionario.getNome()));

		// 3.11 – Imprimir o total dos salários dos funcionários.

		System.out.println();
		System.out.print("Total  dos salarios dos funcionarios: ");
		BigDecimal totalSalarios = quadroFuncionarios.stream().map(Funcionario::getSalario).reduce(BigDecimal.ZERO,
				BigDecimal::add);

		System.out.print("R$ " + df.format(totalSalarios));

		System.out.println();
		System.out.println();
		// 3.12 – Imprimir quantos salários mínimos ganha cada funcionário, considerando
		// que o salário mínimo é R$1212.00.

		quadroFuncionarios.stream().forEach(funcionario -> {
			double salarioMinimo = 1212.00;
			BigDecimal nSalariosMin = funcionario.getSalario().divide(BigDecimal.valueOf(salarioMinimo), 2,
					RoundingMode.HALF_UP);

			System.out.println("Funcionário " + funcionario.getNome() + " recebe " + df.format(nSalariosMin)
					+ " salários mínimos.");
		});

	}
	
	public static void removerFuncionarioPeloNome(String nome, List<Funcionario> quadroFuncionarios) {
		if(quadroFuncionarios.isEmpty()) {
			System.out.println("A lista está vazia");
		} else {
			quadroFuncionarios.removeIf(funcionario -> funcionario.getNome().equalsIgnoreCase(nome));
			System.out.println("Funcionario " + nome + " removido com sucesso.");
		}
	}
	
	
	public static void ajustarSalario(double aumentoPercentual, List<Funcionario> quadroFuncionarios) {
		quadroFuncionarios.stream().forEach(funcionario -> {

			BigDecimal salarioAtual = funcionario.getSalario();


			funcionario.setSalario(salarioAtual.multiply(new BigDecimal(1 + aumentoPercentual)));

		});
	
	}
	
	public static void funcionariosPorFuncao(List<Funcionario> quadroFuncionarios) {
		
		Map<String, List<Funcionario>> funcionariosPorFuncao = new HashMap<>();

		for (Funcionario funcionario : quadroFuncionarios) {
			funcionariosPorFuncao.computeIfAbsent(funcionario.getFuncao(), k -> new ArrayList<>()).add(funcionario);
		}

		System.out.println();
		System.out.println("Funcionários por função");

		for (Entry<String, List<Funcionario>> funcao : funcionariosPorFuncao.entrySet()) {

			System.out.print("Função: " + funcao.getKey() + " - ");

			if (funcao.getValue().isEmpty()) {
				System.out.println("Nenhum funcionário encontrado para essa função!");
			} else {
				
				funcao.getValue().forEach(funcionario -> {
					System.out.print("	" + funcionario.getNome());
				});
			}

			System.out.println();
		}
		
	}
	
	public static void aniversariantesMes(List<Funcionario> quadroFuncionarios, MesesAno mes1, MesesAno mes2) {
		
		quadroFuncionarios.stream().filter(funcionario -> {
			int mesNascimento = LocalDate.parse(funcionario.getDataNascimento(), fmt).getMonthValue();
			return mesNascimento == mes1.getNumero() || mesNascimento == mes2.getNumero();
		}).forEach(funcionario -> System.out.println(funcionario.getNome() + " - " + funcionario.getDataNascimento()));
		
	}
	
	public static void funcionarioMaisVelho(List<Funcionario> quadroFuncionarios) {
        Optional<Funcionario> funcionarioMaisVelho = quadroFuncionarios.stream()
                .min(Comparator.comparing(funcionario -> LocalDate.parse(funcionario.getDataNascimento(), fmt)));

        funcionarioMaisVelho.ifPresent(funcionario -> {
            int idade = Period.between(LocalDate.parse(funcionario.getDataNascimento(), fmt), LocalDate.now())
                    .getYears();
            System.out.println("Nome: " + funcionario.getNome() + " - Idade: " + idade + " anos");
        });
    }
}
