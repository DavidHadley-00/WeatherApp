import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class WeatherAPI {
    public static void main(String[] args) {
        try{
            Scanner scanner = new Scanner(System.in);
            String city;
            do{
                // take user input
                System.out.println("-----------------------------------------");
                System.out.print("City: ");
                city = scanner.nextLine();

                if(city.equalsIgnoreCase("no")) break;

                JSONObject cityLocationData = (JSONObject) getLocationData(city);
                double latitude = (Double) cityLocationData.get("latitude");
                double longitude = (Double) cityLocationData.get("longitude");

                displayWeatherData(latitude, longitude);
            } while (!city.equalsIgnoreCase("no"));

        } catch(Exception e){
            e.printStackTrace();
        }
    }

    private static JSONObject getLocationData(String city){
        city = city.replaceAll(" ", "+");

        String URLString = "https://geocoding-api.open-meteo.com/v1/search?name=" + city + "&count=10&language=en&format=json";

        try {
            // fetch response from API
            HttpURLConnection apiConnection = fetchApiResponse(URLString);

            // check response status (200 = success)
            if(apiConnection.getResponseCode() != 200){
                System.out.println("Error: Could not connect to API");
                return null;
            }

            // read the response as a string
            String jsonResponse = readApiResponse(apiConnection);

            // convert the string into a JSON object
            JSONParser parser = new JSONParser();
            JSONObject resultsJsonObj = (JSONObject) parser.parse(jsonResponse);

            // get the list of results and return the first one
            JSONArray locationData = (JSONArray) resultsJsonObj.get("results");
            return (JSONObject) locationData.get(0);

        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    private static void displayWeatherData(double latitude, double longitude){
        try{
            // fetch the API response based on API link
            String url = "https://api.open-meteo.com/v1/forecast?latitude=" + latitude +
                    "&longitude=" + longitude + "&current=temperature_2m,relative_humidity_2m,precipitation,wind_speed_10m,apparent_temperature,is_day";
            HttpURLConnection apiConnection = fetchApiResponse(url);

            // check response status (200 = success)
            if(apiConnection.getResponseCode() != 200){
                System.out.println("Error: Could not connect to API");
                return;
            }

            // read the response as a string
            String jsonResponse = readApiResponse(apiConnection);

            // convert the string into a JSON object
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(jsonResponse);
            JSONObject currentWeatherJson = (JSONObject) jsonObject.get("current");


            // store the data into their corresponding data type
            String time = (String) currentWeatherJson.get("time");
            System.out.println("Current Time: " + time);

            double temperature = (double) currentWeatherJson.get("temperature_2m");
            System.out.println("Current Temperature (C): " + temperature);

            double temperatureFeels = (double) currentWeatherJson.get("apparent_temperature");
            System.out.println("Currently Feels Like: (C): " + temperatureFeels);

            long relativeHumidity = (long) currentWeatherJson.get("relative_humidity_2m");
            System.out.println("Relative Humidity: " + relativeHumidity + "%");

            double windSpeed = (double) currentWeatherJson.get("wind_speed_10m");
            System.out.println("Weather Speed: " + windSpeed + " km/h");

            long isDayValue = (long) currentWeatherJson.get("is_day");
            boolean isDay = (isDayValue == 1);
            String dayText = isDay ? "yes" : "no";
            System.out.println("Is it Daytime?: " + dayText);

        } catch(Exception e){
            e.printStackTrace();
        }
    }

    // opens a GET connection to the given URL
    private static HttpURLConnection fetchApiResponse(String URLString){
        try{
            // try to connect
            URL url = new URL(URLString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            // set request method to GET
            conn.setRequestMethod("GET");

            return conn;
        } catch(IOException e){
            e.printStackTrace();
        }

        // connection failed
        return null;
    }

    // reads the API response and returns it as a single string
    private static String readApiResponse(HttpURLConnection apiConnection) {
        try {
            // build the response string
            StringBuilder resultJson = new StringBuilder();

            // read from the connection's input stream
            Scanner scanner = new Scanner(apiConnection.getInputStream());

            // add each line to the result
            while (scanner.hasNext()) {
                resultJson.append(scanner.nextLine());
            }

            // close the scanner
            scanner.close();

            // return the full response
            return resultJson.toString();

        } catch(IOException e) {
            // print the error if reading fails
            e.printStackTrace();
        }

        // return null if reading failed
        return null;
    }
}
