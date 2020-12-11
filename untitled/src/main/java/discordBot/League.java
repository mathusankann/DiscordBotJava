package discordBot;


import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;


public class League {
    private final HttpClient client = HttpClient.newHttpClient();
    private Map<String,String> summonerNames =new HashMap<>();



    public String getSummerInfo(String name) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://euw1.api.riotgames.com/lol/summoner/v4/summoners/by-name/"+name+"?api_key=RGAPI-9be2160e-288f-4470-9768-4f624b1b0b18"))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return  response.body();
    }


    public void getMatchHistory(String name) throws IOException, InterruptedException{

        String puuID = getSummerInfo(name).split(",")[2].split(":")[1];
        summonerNames.put(name,puuID);
        System.out.println(puuID);

    }

}
