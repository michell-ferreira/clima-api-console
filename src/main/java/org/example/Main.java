package org.example;

// Usado para ler e manipular dados no formato JSON.
import org.json.JSONObject;

// Fluxo (mangueira) para ler o conteúdo de arquivos(usei para ler o api-key.txt).
import java.io.InputStream;

// Transforma o endereço do site (a String da URL) em um link que o Java consiga acessar.
import java.net.URI;

// Converte texto normal em texto seguro para URL. Ex: "São Paulo" tem espaço e acento
// e isso dar problema numa URL, então ele converte para um formato válido
import java.net.URLEncoder;

// Funciona como um "navegador" invisível. É ele quem faz a conexão com a internet.
import java.net.http.HttpClient;
// Este é o "pedido". Define o que você quer acessar(a URL montada).
import java.net.http.HttpRequest;
// É a "resposta" do site. Guarda o que a API do clima enviou de volta.
import java.net.http.HttpResponse;

// Define que o texto usa o padrão UTF-8.
import java.nio.charset.StandardCharsets;


import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Digite o nome da cidade: ");
        String cidade = scanner.nextLine();

        try {
            // O metodo getDadosClimaticos() está na linha 58.
            String dadosClimaticos = getDadosClimaticos(cidade);
            // Código 1006 significa localização não encontrada.
            if (dadosClimaticos.contains("\"code\":1006")) { // \"code\":1006 representa "code": 1006
                System.out.println("Localização não encontrada. Por favor, tente novamente.");
            } else {
                // O metodo imprimirDadosClimaticos está na linha 118.
                imprimirDadosClimaticos(dadosClimaticos);
            }
        } catch (Exception e) {
            System.out.println("!");
            System.out.println(e.getMessage());
        }


    }

    // Este metodo:
    // Pega a chave da API
    // monta a URL
    // envia a requisição
    // devolve o JSON como String.
    public static String getDadosClimaticos(String cidade) throws Exception {

        // Main.class =referência da classe atual.
        // getClassLoader = Carregador de classes.
        // getResourceAsStream("api-key.txt") = tenta encontrar o arquivo e abrir como fluxo.
        // Resumindo !! Essa linha tenta abrir o arquivo api-key.txt que está na resources.
        InputStream input = Main.class.getClassLoader().getResourceAsStream("api-key.txt");
        if (input == null) {
            throw new RuntimeException("Arquivo api-key.txt não encontrado no resources");
        }

        // input.readAllBytes() = lê todos os bytes do texto.
        // new String(...) = transforma bytes em texto (Casting).
        // .trim() = remove espaços e quebras de linhas no começo e fim.
        String apiKey = new String(input.readAllBytes()).trim();

        // Codifica o nome da cidade para ficar seguro na URL.
        // São Paulo vira algo como S%C3%A3o+Paulo.
        String formataNomeCidade = URLEncoder.encode(cidade, StandardCharsets.UTF_8);

        // Monta a URL ex: apiKey = abc123 e cidade = Joinville, http://api.weatherapi.com/v1/current.json?key=abc123&q=Joinville.
        String apiUrl = "http://api.weatherapi.com/v1/current.json?key=" + apiKey + "&q=" + formataNomeCidade;

        HttpRequest request = HttpRequest.newBuilder() // Construção da requisição (Montando o pedido q vai ser enviado para o servidor).
                .uri(URI.create(apiUrl)) // Define a URL da requisição. URI.create(apiUrl) transforma a String da URL em objeto URI.
                .build(); // Finaliza a construção da requisição.

        // Cria o cliente HTTP, é ele q vai enviar a requisição.
        HttpClient client = HttpClient.newHttpClient();

        // Envia a requisição e recebe a resposta.
        // client.send(...) = envia pedido.
        // HttpResponse.BodyHandlers.ofString() = corpo da resposta como texto.
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // retorna os dados meteorologicos obtidos no site da API(Retorna o corpo da resposta).
        // Esse corpo é o JSON que veio da API.
        return response.body();
    }

    // Exemplo de resposta a ser tratada no metodo abaixo.
//    {
//        "location": {
//        "name": "Joinville",
//                "country": "Brazil"
//    },
//        "current": {
//        "temp_c": 24.5,
//                "feelslike_c": 26.0,
//                "humidity": 80,
//                "wind_kph": 15.0,
//                "pressure_mb": 1013.0,
//                "last_updated": "2026-04-14 10:00",
//                "condition": {
//            "text": "Parcialmente nublado"
//        }
//    }
//    }

    // Metodo que recebe o JSON em texto e imprime as informações de forma organizada. O "dados" é uma string JSON.
    public static void imprimirDadosClimaticos(String dados) {

        // Transforma a String JSON em um objeto que o java consegue navegar.
        // Agora pode entrar nas partes dele tipo location, current ...
        JSONObject dadosJson = new JSONObject(dados);

        // Pega só o pedaço "current" do JSON. Agora informacoesMeteorologicas aponta pra este bloco.
        JSONObject informacoesMetereologicas = dadosJson.getJSONObject("current");

        // Extrai os dados da localização
        String cidade = dadosJson.getJSONObject("location").getString("name");
        String pais = dadosJson.getJSONObject("location").getString("country");

        // Extrai os dados adicionais
        String condicaoTempo = informacoesMetereologicas.getJSONObject("condition").getString("text");
        int umidade = informacoesMetereologicas.getInt("humidity");
        float velocidadeVento = informacoesMetereologicas.getFloat("wind_kph");
        float pressAtmosferica = informacoesMetereologicas.getFloat("pressure_mb");
        float sensacaoTermica = informacoesMetereologicas.getFloat("feelslike_c");
        float temperaturaAtual = informacoesMetereologicas.getFloat("temp_c");

        // Extrai data e hora da string retornada pela API
        String dataHoraString = informacoesMetereologicas.getString("last_updated");

        // Imprime as informações atuais
        System.out.println("Informações Metereológicas para " + cidade + ", " + pais);
        System.out.println("Data e Hora: " + dataHoraString);
        System.out.println("Temperatura Atual: " + temperaturaAtual + "°C");
        System.out.println("Sensação Térmica: " + sensacaoTermica + "°C");
        System.out.println("Condição do Tempo: " + condicaoTempo);
        System.out.println("Umidade: " + umidade + "%");
        System.out.println("Velocidade do Vento: " + velocidadeVento + " km/h");
        System.out.println("Pressão Atmosférica: " + pressAtmosferica + " mb");

    }

}