package edu.calpoly.eharlig.budgetBrews.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.calpoly.eharlig.budgetBrews.models.Beer;
import edu.calpoly.eharlig.budgetBrews.util.Sorting;

public class FilterBeers implements RequestHandler<Map<String, String>, List<List<Beer>>>{

  public List<List<Beer>> handleRequest(Map<String, String> request, Context context) {
    List<Beer> beer12 = new GetAll().getAllQuantity(12);
    List<Beer> beer30 = new GetAll().getAllQuantity(30);
    
    List<List<Beer>> byBeer12 = new ArrayList<>();
    List<List<Beer>> byBeer30 = new ArrayList<>();
    List<Beer> eachBeer12 = new ArrayList<>();
    List<Beer> eachBeer30 = new ArrayList<>();

    for (Map.Entry<String, String> entry : request.entrySet()) {
      byBeer12.add(filterBeer(beer12, entry.getValue()));
    }

    for (List<Beer> beers : byBeer12) {
      eachBeer12.addAll(beers);
    }

    for (Map.Entry<String, String> entry : request.entrySet()) {
      byBeer30.add(filterBeer(beer30, entry.getValue()));
    }

    for (List<Beer> beers : byBeer30) {
      eachBeer30.addAll(beers);
    }

    eachBeer12 = Sorting.sort(eachBeer12);
    eachBeer30 = Sorting.sort(eachBeer30);
    
    List<List<Beer>> byBeer = new ArrayList<List<Beer>>();

    byBeer.add(eachBeer12);
    byBeer.add(eachBeer30);
    
    return byBeer;
  }
  
  public static List<Beer> filterBeer(List<Beer> beers, String beerName) {
    List<Beer> allBeers = new ArrayList<Beer>();

    for (Beer beer : beers) {
      if (beer.getName().equals(beerName)) {
        allBeers.add(beer);
      }
    }

    return allBeers;
  }

}
