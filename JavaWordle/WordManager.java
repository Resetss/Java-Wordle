package JavaWordle;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class WordManager {
	
	public static String getRandomWord(int lengthOfWord) {
		String word = requestRandomWord(lengthOfWord);
		while(!isValidWord(word)) {
			word = requestRandomWord(lengthOfWord); 
		}
		return word; 
	}
	
    public static String requestRandomWord(int lengthOfWord) {
        try {
            String apiUrl = "https://random-word-api.herokuapp.com/word?number=1&length=" + lengthOfWord;
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(apiUrl)).build();
            
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            
            String word = response.body().replace("[", "").replace("]", "").replace("\"", "");
            return word;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static boolean isValidWord(String wordToCheck) {
        try {
            String apiUrl = "https://api.dictionaryapi.dev/api/v2/entries/en/" + wordToCheck;
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(apiUrl)).build();
            
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            
            return response.statusCode() == 200;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }
}