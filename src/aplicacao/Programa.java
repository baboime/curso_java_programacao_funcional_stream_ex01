package aplicacao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.stream.Collectors;

import entidades.Produto;

public class Programa {

	public static void main(String[] args) {
		
		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);
				
		System.out.print("Inform o diretorio completo para o arquivo: ");
		String diretorio = sc.nextLine();
		
		try (BufferedReader br = new BufferedReader(new FileReader(diretorio))) {
			
			List<Produto> lista = new ArrayList<>();
			
			String linha = br.readLine();
			while (linha != null) {
				String[] campos = linha.split(",");
				String nome = campos[0];
				Double preco = Double.parseDouble(campos[1]);
				lista.add(new Produto(nome, preco));
				linha = br.readLine();
			}
			
			//pipeline para obter o precio medio
			double precoMedio = lista.stream()
					.map(p -> p.getPreco())
					.reduce(0.0, (x,y) -> x + y) / lista.size();
			
			System.out.println("Preco médio: " + String.format("%.2f", precoMedio));
			
			//listar os produtos com o valor inferior ao preco medio em ordem decrescente
			
			Comparator<String> comp = (s1, s2) -> s1.toUpperCase().compareTo(s2.toUpperCase());
			
			List<String> nomesDecrescente = lista.stream()
					.filter(p -> p.getPreco() < precoMedio)
					.map(p -> p.getNome())
					.sorted(comp.reversed())
					.collect(Collectors.toList());
			
			nomesDecrescente.forEach(System.out::println);
	
		}
		catch (IOException e) {
			System.out.println("Erro ao ler o arquivo informado: " + e.getMessage());
		}	
		sc.close();
	}
}
