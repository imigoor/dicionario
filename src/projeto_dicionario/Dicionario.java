package projeto_dicionario;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Dicionario 
{
	private String idiomaAtual;
	private HashMap<String, ArrayList<String>> palavrasIdioma;
	private HashMap<String, ArrayList<String>> palavrasPortugues;
	private ArrayList<String> idiomasDisponiveis;
	
	public Dicionario(String idioma) throws Exception
	{
		idiomasDisponiveis = new ArrayList<>();
		idiomasDisponiveis.add("ingles");
		idiomasDisponiveis.add("espanhol");
		
		if (!idiomasDisponiveis.contains(idioma))
		{
			throw new Exception(String.format("Idioma não encontrado: %s", idioma));
		}

		idiomaAtual = idioma;
		carregarDados();
	}
	
	private void carregarDados() throws FileNotFoundException 
	{
		palavrasIdioma = new HashMap<>();
		palavrasPortugues = new HashMap<>();
		String arquivo = "src/dados/" + idiomaAtual + ".csv";
		
		Scanner scanner = new Scanner(new File(arquivo));
		while (scanner.hasNextLine())
		{
			String[] linha = scanner.nextLine().split(";");
			if (linha.length == 2) {
                String palavraIdioma = normalizar(linha[0]);
                String palavraPortugues = normalizar(linha[1]);

                palavrasIdioma.computeIfAbsent(palavraIdioma, k -> new ArrayList<>()).add(palavraPortugues);
                palavrasPortugues.computeIfAbsent(palavraPortugues, k -> new ArrayList<>()).add(palavraIdioma);
            }
		}
		scanner.close();
	}
	
	private String normalizar(String texto)
	{
		 return Normalizer.normalize(texto.toLowerCase(), Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
	}
	
	public ArrayList<String> traduzirParaPortugues(String palavra)
	{
		palavra = normalizar(palavra);
		return palavrasIdioma.getOrDefault(palavra, new ArrayList<>());
	}
	
	public ArrayList<String> traduzirParaIdioma(String palavra)
	{
		palavra = normalizar(palavra);
		return palavrasPortugues.getOrDefault(palavra, new ArrayList<>());
	}
	
	public ArrayList<String> localizarPalavraIdioma(String palavra)
	{
		palavra = normalizar(palavra);
		ArrayList<String> resultados = new ArrayList<>();
		for (String palavraNoIdioma : palavrasIdioma.keySet()) 
		{
			if (palavraNoIdioma.contains(palavra))
			{
				resultados.add(palavraNoIdioma);
			}
		}
		return resultados;
	}
	
	public ArrayList<String> localizarPalavraPortugues(String palavra)
	{
		palavra = normalizar(palavra);
		ArrayList<String> resultados = new ArrayList<>();
		for (String palavraNoIdioma : palavrasPortugues.keySet())
		{
			if (palavraNoIdioma.contains(palavra))
			{
                resultados.add(palavraNoIdioma);
            }
		}
        return resultados;
	}
	
	public ArrayList<String> getIdiomas() {
        return idiomasDisponiveis;
    }
	
	public void setIdioma(String idioma) throws Exception
	{
		if (!idiomasDisponiveis.contains(idioma))
		{
			throw new Exception(String.format("Idioma não encontrado: %s", idioma));
		}
		idiomaAtual = idioma;
		carregarDados();
	}
}
