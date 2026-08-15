package com.tradefinance.trade_finance_backend.ai.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tradefinance.trade_finance_backend.ai.AIAnalysisResult;
import com.tradefinance.trade_finance_backend.ai.AIAnalysisService;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;
import java.util.List;
import java.util.Map;

@Service
public class AIAnalysisServiceImpl implements AIAnalysisService {

    private final WebClient aiWebClient;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public AIAnalysisServiceImpl(WebClient aiWebClient) {
        this.aiWebClient = aiWebClient;
    }

    @Override
    public AIAnalysisResult analyser(String texteOcr, String typeDocumentNom) {
        String prompt = construirePrompt(texteOcr, typeDocumentNom);

        Map<String, Object> body = Map.of(
                "model", "claude-sonnet-4-6",
                "max_tokens", 1000,
                "messages", List.of(Map.of("role", "user", "content", prompt))
        );

        try {
            Map<?, ?> response = aiWebClient.post()
                    .bodyValue(body)
                    .retrieve()
                    .bodyToMono(Map.class)
                    .timeout(Duration.ofSeconds(30))
                    .block();

            String texteJson = extraireTexteReponse(response);
            return parserJsonEnResultat(texteJson, typeDocumentNom);

        } catch (Exception e) {
            // Fallback : si l'IA ne répond pas, on ne bloque pas tout le flux
            AIAnalysisResult fallback = new AIAnalysisResult();
            fallback.setConforme(false);
            fallback.setScoreConformite(0.0);
            fallback.setResume("Erreur lors de l'analyse IA : " + e.getMessage());
            return fallback;
        }
    }

    private String construirePrompt(String texteOcr, String typeDocumentNom) {
        return """
                Voici le texte extrait par OCR d'un document de type %s :
                ---
                %s
                ---
                Analyse ce document et réponds UNIQUEMENT en JSON, sans aucun texte avant ou après,
                avec ce format exact :
                {
                  "conforme": true ou false,
                  "scoreConformite": nombre entre 0 et 100,
                  "resume": "texte court résumant l'analyse",
                  "anomalies": [
                    {
                      "type": "ex: MONTANT, DATE, SIGNATURE...",
                      "description": "description de l'anomalie",
                      "gravite": "FAIBLE ou MOYENNE ou HAUTE",
                      "champConcerne": "nom du champ concerné"
                    }
                  ]
                }
                """.formatted(typeDocumentNom, texteOcr);
    }

    @SuppressWarnings("unchecked")
    private String extraireTexteReponse(Map<?, ?> response) {
        // La réponse de l'API Anthropic a la forme :
        // { "content": [ { "type": "text", "text": "..." } ] }
        List<Map<String, Object>> content = (List<Map<String, Object>>) response.get("content");

        if (content == null || content.isEmpty()) {
            throw new RuntimeException("Réponse IA vide ou mal formée");
        }

        return (String) content.get(0).get("text");
    }

    private AIAnalysisResult parserJsonEnResultat(String texteJson, String typeDocumentNom) {
        try {
            // Nettoyer au cas où le LLM ajoute des ```json ... ``` autour
            String clean = texteJson.replaceAll("```json", "")
                                     .replaceAll("```", "")
                                     .trim();

            JsonNode node = objectMapper.readTree(clean);

            AIAnalysisResult resultat = new AIAnalysisResult();
            resultat.setConforme(node.path("conforme").asBoolean(false));
            resultat.setScoreConformite(node.path("scoreConformite").asDouble(0.0));
            resultat.setResume(node.path("resume").asText("Analyse indisponible pour " + typeDocumentNom));

            JsonNode anomalies = node.path("anomalies");
            if (anomalies.isArray()) {
                for (JsonNode a : anomalies) {
                    resultat.getAnomalies().add(new AIAnalysisResult.AnomalieDetectee(
                            a.path("type").asText(""),
                            a.path("description").asText(""),
                            a.path("gravite").asText(""),
                            a.path("champConcerne").asText("")
                    ));
                }
            }

            return resultat;

        } catch (Exception e) {
            throw new RuntimeException("Erreur de parsing de la réponse IA : " + e.getMessage(), e);
        }
    }
}