package org.example;

import org.json.JSONObject;

import java.io.InputStream;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;


import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Digite o nome da cidade: ");
        String cidade = scanner.nextLine();

        try {
            String dadosClimaticos = getDadosClimaticos(cidade);
            // Código 1006 significa localização não encontrada.
            if (dadosClimaticos.contains("\"code\":1006")) { // \"code\":1006 representa "code": 1006
                System.out.println("Localização não encontrada. Por favor, tente novamente.");
            } else {
                imprimirDadosClimaticos(dadosClimaticos);
            }
        } catch (Exception e) {
            System.out.println("!");
            System.out.println(e.getMessage());
        }


    }

    public static String getDadosClimaticos(String cidade) throws Exception {

        InputStream input = Main.class.getClassLoader().getResourceAsStream("api-key.txt");
        if (input == null) {
            throw new RuntimeException("Arquivo api-key.txt não encontrado no resources");
        }

        String apiKey = new String(input.readAllBytes()).trim();
        String formataNomeCidade = URLEncoder.encode(cidade, StandardCharsets.UTF_8);
        String apiUrl = "http://api.weatherapi.com/v1/current.json?key=" + apiKey + "&q=" + formataNomeCidade;
        HttpRequest request = HttpRequest.newBuilder() // Começa a construção de uma nova solicitação HTTP.
                .uri(URI.create(apiUrl)) // Este metodo define o URI da solicitação HTTP.
                .build(); // Finaliza a construção da solicitação HTTP.

        // Criar objeto e enviar solicitações HTTP e receber respostas HTTP, para acessar o site da WeatherAPI.
        HttpClient client = HttpClient.newHttpClient();

        // Agora vamos enviar requisições HTTP e receber respostas HTTP, comunicar com o site da API meteorologica.
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return response.body(); // retorna os dados meteorologicos obtivos no site da API
    }

    // Metodo para imprimir os dados meteorológicos de forma organizada.
    public static void imprimirDadosClimaticos(String dados) {
        //System.out.println("Dados originais (JSON) obtidos no site meteorológico" + dados);

        JSONObject dadosJson = new JSONObject(dados);
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