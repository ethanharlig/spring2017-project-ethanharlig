package edu.calpoly.eharlig.budget_brews.services;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.calpoly.eharlig.budget_brews.models.Beer;
import edu.calpoly.eharlig.budget_brews.util.Credentials;

public class AddBeer implements RequestHandler<Beer, Object> {
	private static String AWS_KEY = new Credentials().getAwsAccessKey();
	private static String SECRET_KEY = new Credentials().getAwsSecretKey();

	private static AmazonDynamoDBClient client = new AmazonDynamoDBClient(new BasicAWSCredentials(AWS_KEY, SECRET_KEY))
			.withRegion(Regions.US_WEST_2);

	private static DynamoDB dynamoDB = new DynamoDB((AmazonDynamoDB) client);

	public Object handleRequest(Beer beer, Context context) {
		Table table = dynamoDB.getTable("beer-" + Integer.toString(beer.getQuantity()));

		Item item = new Item();
		item.withPrimaryKey("name", beer.getName());
		item.withDouble("price", beer.getPrice());
		item.withString("storeName", beer.getStoreName());
		item.withLong("timestamp", System.currentTimeMillis());
		item.withList("history", beer.getHistory());

		return table.putItem(item);
	}

}
