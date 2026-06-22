package ai;

import org.json.JSONArray;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import pw.base.PWBaseTest;

public class GeminiClient implements AIClient {
	private static final String API_KEY = PWBaseTest.mapAllVariables.get("geminikey");
	// private static final String URL =
	// "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent?key="
	private static final String URL = PWBaseTest.mapAllVariables.get("geminiURL1") + API_KEY;
	OkHttpClient client = new OkHttpClient.Builder().protocols(java.util.Collections.singletonList(Protocol.HTTP_1_1))
			.connectTimeout(30, java.util.concurrent.TimeUnit.SECONDS)
			.readTimeout(120, java.util.concurrent.TimeUnit.SECONDS).build();

	@Override
	public String ask(String prompt) throws Exception {
		JSONObject text = new JSONObject();
		text.put("text", prompt);
		JSONArray parts = new JSONArray();
		parts.put(text);
		JSONObject content = new JSONObject();
		content.put("parts", parts);
		JSONArray contentsArray = new JSONArray();
		contentsArray.put(content);
		JSONObject requestJson = new JSONObject();
		requestJson.put("contents", contentsArray);
		RequestBody body = RequestBody.create(requestJson.toString(), MediaType.parse("application/json"));
		Request request = new Request.Builder().url(URL).addHeader("Content-Type", "application/json").post(body)
				.build();
		try (Response response = client.newCall(request).execute()) {
			if (!response.isSuccessful()) {
				return "AI request failed: HTTP " + response.code();
			}
			String responseBody = response.body().string();
			JSONObject json = new JSONObject(responseBody);
			if (!json.has("candidates")) {
				return "AI could not analyze the failure.";
			}
			return json.getJSONArray("candidates").getJSONObject(0).getJSONObject("content").getJSONArray("parts")
					.getJSONObject(0).getString("text");
		}
	}
}// EOF