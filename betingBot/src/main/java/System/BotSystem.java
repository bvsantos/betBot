package System;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.TreeMap;

public class BotSystem {
	private final CloseableHttpClient httpClient = HttpClients.createDefault();
	private ArrayList<Bet> bet1, bet2, bet3;

	
	public BotSystem() throws ClientProtocolException, IOException {
		bet1 = new ArrayList<Bet>();
		bet2 = new ArrayList<Bet>();
		bet3 = new ArrayList<Bet>();
		this.formBets(this.handleArray(this.getGames()));
//		System.out.println(bet1.size());
//		System.out.println(bet2.size());
//		System.out.println(bet3.size());
//		Iterator<Bet> i = bet1.iterator();
//		Iterator<JSONObject> ii = bet2.iterator();
//		Iterator<JSONObject> iii = bet3.iterator();
//		System.out.println("---------bet1---------");
//		while(i.hasNext()) {
//			Bet bet = i.next();
//			System.out.println(bet.getInfo());
//		}
//		System.out.println(bet1.isEmpty());
//		System.out.println("---------bet2---------");
//		while(ii.hasNext()) {
//			JSONObject bet = ii.next();
//			System.out.println(bet.get("name")+"   "+ bet.get("date"));
//		}
//		System.out.println("---------bet3---------");
//		while(iii.hasNext()) {
//			JSONObject bet = iii.next();
//			System.out.println(bet.get("name")+"   "+ bet.get("date"));
//		}
		
	}
	
	public ArrayList<Bet> getBet(int i){
		if(i == 0)
			return bet1;
		else if(i == 1)
			return bet2;
		else if(i == 2)
			return bet3;
		return null;
	}

	private void formBets(TreeMap<Double, ArrayList<JSONObject>> games) {
		Iterator<Double> i = games.descendingKeySet().descendingIterator(); 
		Iterator<JSONObject> listI;
		double totalOdd = 1.0;
		int counter = 0;
		while (i.hasNext()) {
			double next = i.next();
			listI = games.get(next).iterator();
			while (listI.hasNext()) {
				if(counter > 2)
					break;
				JSONObject nextBet = listI.next();
				JSONArray selections = nextBet.getJSONArray("markets").getJSONObject(0).getJSONArray("selections");
				Double odd1;
				Double odd2;
				String name1;
				String name2;
				if (selections.length() == 2) {
					name1 = selections.getJSONObject(0).getString("name");
					name2 = selections.getJSONObject(1).getString("name");
					odd1 = selections.getJSONObject(0).getDouble("odds");
					odd2 = selections.getJSONObject(1).getDouble("odds");
				} else {

					name1 = selections.getJSONObject(0).getString("name");
					name2 = selections.getJSONObject(2).getString("name");
					odd1 = selections.getJSONObject(0).getDouble("odds");
					odd2 = selections.getJSONObject(2).getDouble("odds");
				}
				//System.out.println(odd1+"  "+ odd2);
				if (Double.compare(odd1, odd2) < 0 && odd1>1.0)
					totalOdd *= odd1;
				else if(Double.compare(odd1, odd2) > 0 && odd2>1.0)
					totalOdd *= odd2;
				if (counter == 0 && odd1>1.0 && odd2>1.0)
					this.bet1.add(new Bet(selections.length(), odd1, odd2, nextBet.getString("relativeDesktopUrl")+"/", name1, name2, nextBet.getString("date")));
				else if (counter == 1 && odd1>1.0 && odd2>1.0)
					this.bet2.add(new Bet(selections.length(), odd1, odd2, nextBet.getString("relativeDesktopUrl")+"/", name1, name2, nextBet.getString("date")));
				else if(counter == 2 && odd1>1.0 && odd2>1.0)
					this.bet3.add(new Bet(selections.length(), odd1, odd2, nextBet.getString("relativeDesktopUrl")+"/",name1, name2, nextBet.getString("date")));
				if(totalOdd > 1.20){
					totalOdd = 1.0;
					counter++;
				}
			}
			if(counter > 2)
				break;
		}

	}

	private TreeMap<Double, ArrayList<JSONObject>> handleArray(JSONArray response) throws IOException {
		httpClient.close();
		TreeMap<Double, ArrayList<JSONObject>> games = new TreeMap<Double, ArrayList<JSONObject>>(Collections.reverseOrder());
		double odd1 = 0.0;
		double odd2 = 0.0;
		JSONArray selections = new JSONArray();
		ArrayList<JSONObject> toAdd;
		// System.out.println(response.getJSONObject(0).get("markets"));
		for (int i = 0; i < response.length(); i++) {
			if (response.getJSONObject(i).getJSONArray("markets").length() > 0
					&& !response.getJSONObject(i).getBoolean("isLive")) {
				selections = response.getJSONObject(i).getJSONArray("markets").getJSONObject(0)
						.getJSONArray("selections");
				if (selections.length() == 2) {
					odd1 = selections.getJSONObject(0).getDouble("odds");
					odd2 = selections.getJSONObject(1).getDouble("odds");
				} else if (selections.length() == 3) {
					odd1 = selections.getJSONObject(0).getDouble("odds");
					odd2 = selections.getJSONObject(2).getDouble("odds");
				} else {
					break;
				}
				if (Double.compare(odd1, odd2) < 0) {
					if (games.containsKey(odd2))
						games.get(odd2).add(response.getJSONObject(i));
					else {
						toAdd = new ArrayList<JSONObject>();
						toAdd.add(response.getJSONObject(i));
						games.put(odd2, toAdd);
					}
				} else if (Double.compare(odd1, odd2) > 0) {
					if (games.containsKey(odd1))
						games.get(odd1).add(response.getJSONObject(i));
					else {
						toAdd = new ArrayList<JSONObject>();
						toAdd.add(response.getJSONObject(i));
						games.put(odd1, toAdd);
					}
				}
			}
		}
		return games;
	}

	@SuppressWarnings("unused")
	private JSONArray getGames() throws ClientProtocolException, IOException {
		HttpGet request = new HttpGet(
				"https://offer.cdn.begmedia.com/api/pub/v4/events?application=1024&countrycode=pt&isLive=false&language=pt&limit=400&offset=0&sitecode=ptpt&sortBy=ByLiveRankingPreliveDate");
		try (CloseableHttpResponse response = httpClient.execute(request)) {

			// Get HttpResponse Status
			System.out.println(response.getStatusLine().toString());

			HttpEntity entity = response.getEntity();
			Header headers = entity.getContentType();
			// System.out.println(headers);

			if (entity != null) {
				// return it as a String
				String result = EntityUtils.toString(entity);
				// System.out.println(result);
				return new JSONArray(result);
			}
		}
		return null;
	}
}
