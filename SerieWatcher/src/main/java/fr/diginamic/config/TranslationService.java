package fr.diginamic.config;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class TranslationService {
	@Autowired
	private RestTemplate restTemplate;

	private static final String YANDEX_API_BASE_URL = "https://translate.yandex.net/api/v1.5/tr.json/translate";
	private static final String API_KEY = "pdct.1.1.20240419T180320Z.214b14a5da524e12.c6a80b37cde863cb7964b750cea6d6dbc6b9ce0b";

	public String translateText(String text, String sourceLang,
			String targetLang) {
		UriComponentsBuilder uriBuilder = UriComponentsBuilder
				.fromHttpUrl(YANDEX_API_BASE_URL)
				.queryParam("key", API_KEY).queryParam("text", text)
				.queryParam("lang", sourceLang + "-" + targetLang)
				.queryParam("format", "plain");

		ResponseEntity<YandexResponse> response = restTemplate
				.getForEntity(uriBuilder.toUriString(),
						YandexResponse.class);
		if (response.getStatusCode().is2xxSuccessful()
				&& response.getBody() != null
				&& !response.getBody().getText().isEmpty()) {
			return response.getBody().getText().get(0);
		} else {
			System.out.println(
					"Failed to translate text: " + response.getStatusCode()
							+ " - " + response.getBody());
			return text;
		}
	}

	private static class YandexResponse {
		private List<String> text = Collections.emptyList();

		public List<String> getText() {
			return text;
		}

		public void setText(List<String> text) {
			this.text = text;
		}
	}
}
