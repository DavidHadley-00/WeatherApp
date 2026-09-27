# WeatherApp

Simple Java app that uses APIs to retrieve weather info and display them

## About

I followed along with a project to learn how to make API requests in Java,
Work with JSON data and Parsing API responses. As well as getting familiar with github since I'm used to gitlab.

## What this Project does

- Accepts a city name from the user via the console
- Queries the Open-Meteo geocoding API to get the city's latitude and longitude
- Queries the Open-Meteo forecast API for current weather at those coordinates
- Parses the JSON response using `json-simple` and prints the results to the console
- Repeats until the user enters "no"

## Plans

I may revisit this project at a later date and create a UI to display the info instead of just 
displaying it in the console