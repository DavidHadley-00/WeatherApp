import org.json.simple.JSONObject;

import java.util.Scanner;
import java.util.concurrent.ExecutionException;

public class WeatherAPI {
    public static void main(String[] args) {
        try{
            Scanner scanner = new Scanner(System.in);
            String city;
            do{
                // Take user input
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

    }

    private static void displayWeatherData(double latitude, double longitude){

    }
}
