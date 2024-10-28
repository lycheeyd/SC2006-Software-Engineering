package com.WellnessZone;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

public class NParkDataDownloader {
    private String datasetId = ""; // d_77d7ec97be83d44f61b85454f844382f for this specific data
    private String initiateUrl = "";

    public NParkDataDownloader(String datasetId) {
        this.datasetId = datasetId;
        this.initiateUrl = "https://api-open.data.gov.sg/v1/public/api/datasets/" + datasetId
                + "/initiate-download";
    }

    private String responseData = "";
    private String errorMessage = "";

    public void initiateDownload() {

        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(initiateUrl).openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");

            int responseCode = connection.getResponseCode();

            if (responseCode == 201) {
                String response = readResponse(connection);
                JSONObject initiateData = new JSONObject(response);

                // Check if the initiation was successful
                if (initiateData.getInt("code") == 0) {
                    pollDownload(datasetId);
                } else {
                    errorMessage = initiateData.getString("errMsg");
                    responseData = "";
                }
            } else {
                errorMessage = "Error initiating download: " + responseCode;
                responseData = "";
            }

        } catch (Exception e) {
            errorMessage = "Error: " + e.getMessage();
            responseData = "";
        }
    }

    private void pollDownload(String datasetId) {
        String pollUrl = "https://api-open.data.gov.sg/v1/public/api/datasets/" + datasetId + "/poll-download";

        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(pollUrl).openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");

            int responseCode = connection.getResponseCode();

            if (responseCode == 201) {
                String response = readResponse(connection);
                JSONObject pollData = new JSONObject(response);

                // Check if the data is ready
                if (pollData.getInt("code") == 0) {
                    // Download the data from the URL provided in the response
                    String downloadUrl = pollData.getJSONObject("data").getString("url");
                    downloadData(downloadUrl);
                } else {
                    errorMessage = pollData.getString("errMsg");
                    responseData = "";
                }
            } else {
                errorMessage = "Error polling download: " + responseCode;
                responseData = "";
            }

        } catch (Exception e) {
            errorMessage = "Error: " + e.getMessage();
            responseData = "";
        }
    }

    private void downloadData(String url) {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();

            if (responseCode == 200) {
                responseData = readResponse(connection);
                errorMessage = "";
            } else {
                errorMessage = "Error downloading data: " + responseCode;
                responseData = "";
            }

        } catch (Exception e) {
            errorMessage = "Error: " + e.getMessage();
            responseData = "";
        }
    }

    private String readResponse(HttpURLConnection connection) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();
        return response.toString();
    }

    public String getResponseData() {
        return responseData;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

}
