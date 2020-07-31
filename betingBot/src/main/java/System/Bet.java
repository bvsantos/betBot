package System;

public class Bet {
	private int size;
	private Double odd1, odd2;
	private String url, name1, name2, date;
	
	public Bet(int size, Double odd1, Double odd2, String url, String name1, String name2, String date) {
		this.size = size;
		this.odd1 = odd1;
		this.odd2 = odd2;
		this.url = "https://www.betclic.pt/"+url;
		this.name1 = name1;
		this.name2 = name2;
		this.date = date;
	}
	
	public String getInfo() {
		return this.name1+"("+this.odd1+") vs "+this.name2+"("+this.odd2+")     - "+this.date;
	}
	public int getSize() {
		return this.size;
	}
	public Double getOddToBet1() {
		return this.odd1;
	}
	public Double getOddToBet2() {
		return this.odd2;
	}
	public int getIndexToBet() {
		if(Double.compare(odd1, odd2)<0) {
			return 0;
		}else {
			if(this.size == 2)
				return 1;
			else
				return 2;
		}
	}
	public String getUrl() {
		return this.url;
	}
}
