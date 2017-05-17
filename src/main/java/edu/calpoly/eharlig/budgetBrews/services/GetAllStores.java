package edu.calpoly.eharlig.budgetBrews.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

//import edu.calpoly.eharlig.budgetBrews.util.Credentials;

public class GetAllStores implements RequestHandler<Object, List<String>> {
//  private static String AWS_KEY = Credentials.getAwsKey();
//  private static String SECRET_KEY = Credentials.getSecretKey();
  static String AWS_KEY = "";
  static String SECRET_KEY = "";

  private static AmazonDynamoDBClient client = new AmazonDynamoDBClient(
      new BasicAWSCredentials(AWS_KEY, SECRET_KEY)).withRegion(Regions.US_WEST_2);

  public List<String> handleRequest(Object request, Context context) {
    List<String> stores = new ArrayList<String>();

    for (String store : getAllQuantity(12)) {
      if (!stores.contains(store))
        stores.add(store);
    }

    for (String store : getAllQuantity(30)) {
      if (!stores.contains(store))
        stores.add(store);
    }

    return stores;
  }

  public ArrayList<String> getAllQuantity(int quantity) {
    ScanRequest scanRequest = new ScanRequest().withTableName("beer-" + quantity);

    ScanResult result = client.scan(scanRequest);

    ArrayList<String> allStores = new ArrayList<String>();

    for (Map<String, AttributeValue> item : result.getItems()) {
      allStores.add(item.get("storeName").getS());
    }

    return allStores;
  }


}