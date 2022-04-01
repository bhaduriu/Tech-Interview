// BlackRock: Question 3

package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;

// Asset Data Type for handling all sort of assets
class Asset implements Comparable<Asset>
{
	private String asset_name, asset_type;
	private int asset_quantity;

	public Asset(String name, String type, int num)
	{
    	this.asset_name = name;
    	this.asset_type = type;
    	this.asset_quantity = num;
	}

	public String get_Asset_Name()
	{
    	return asset_name;
	}

	public String get_Asset_Type()
	{
    	return asset_type;
	}

	public int get_Asset_Quantity()
	{
    	return asset_quantity;
	}

	@Override
	public String toString()
	{
    	return "name:" + asset_name + " type:" + asset_type + " share:" + asset_quantity;
	}

	@Override
	public int compareTo(Asset temp)
	{
    	return this.get_Asset_Name().compareToIgnoreCase(temp.get_Asset_Name());
	}
}

// Trade Data Type for handling the transaction result output
class Trade implements Comparable<Trade>
{
	private String transaction_type, trade_name, trade_type;
	private int trade_quantity;

	public Trade(String buy_or_sell, String name, String type, int num_shares)
	{
    	this.transaction_type = buy_or_sell;
    	this.trade_name = name;
    	this.trade_type = type;
    	this.trade_quantity = num_shares;
	}

	public String get_Transaction_Type()
	{
    	return this.transaction_type;
	}

	public String get_Trade_Name()
	{
    	return this.trade_name;
	}

	public String get_Trade_Type()
	{
    	return this.trade_type;
	}

	public int get_Trade_Quantity()
	{
    	return this.trade_quantity;
	}

	@Override
	public int compareTo(Trade temp)
	{
    	return this.get_Trade_Name().compareToIgnoreCase(temp.get_Trade_Name());
	}
}


interface benchmark_Matching
{
	public ArrayList<Trade> start_Trading();
}

class match_Trading implements benchmark_Matching
{
	ArrayList<Asset> portfolio;
	ArrayList<Asset> benchmark;
	ArrayList<Trade> result_Trade;

	match_Trading(String[] port, String[] bench)
	{
    	portfolio = new ArrayList<Asset>();
    	for (String each_string : port)
    	{
        	String[] port_data = each_string.split(",");
        	portfolio.add(new Asset(port_data[0], port_data[1], Integer.parseInt(port_data[2])));
    	}

    	benchmark = new ArrayList<Asset>();
    	for (String each_string : bench)
    	{
        	String[] bench_data = each_string.split(",");
        	benchmark.add(new Asset(bench_data[0], bench_data[1], Integer.parseInt(bench_data[2])));
    	}

    	Collections.sort(portfolio);
    	Collections.sort(benchmark);
	}

	@Override
	public ArrayList<Trade> start_Trading()
	{
    	this.result_Trade = new ArrayList<Trade>();
    	int portfolio_index = 0, benchmark_index = 0;

    	// while loop because portfolio and benchmark can have varying lengths, so need to traverse
    	// the remaining one when either of the indices reach size() (HENCE, OR not AND)
    	while(portfolio_index < portfolio.size() || benchmark_index < benchmark.size())
    	{
        	Asset portfolio_asset = portfolio.get(portfolio_index);
        	Asset benchmark_asset = benchmark.get(benchmark_index);

        	// comparing and matching both portfolios and benchmark when they are equal in size
        	if(portfolio_asset.get_Asset_Name().compareToIgnoreCase(benchmark_asset.get_Asset_Name()) == 0)
        	{
            	if(portfolio_asset.get_Asset_Quantity() < benchmark_asset.get_Asset_Quantity())
            	{
                	result_Trade.add(new Trade("BUY", portfolio_asset.get_Asset_Name(),
                        	portfolio_asset.get_Asset_Type(),
                        	(benchmark_asset.get_Asset_Quantity() - portfolio_asset.get_Asset_Quantity())));
            	}
            	else if(portfolio_asset.get_Asset_Quantity() > benchmark_asset.get_Asset_Quantity())
            	{
                	result_Trade.add(new Trade("SELL", portfolio_asset.get_Asset_Name(),
                        	portfolio_asset.get_Asset_Type(),
                        	(portfolio_asset.get_Asset_Quantity() - benchmark_asset.get_Asset_Quantity())));
            	}
            	portfolio_index++; benchmark_index++;
        	}
        	// Asset 'i' (specific) is in portfolio but not in benchmark
        	else if(portfolio_asset.get_Asset_Name().compareToIgnoreCase(benchmark_asset.get_Asset_Name()) < 0)
        	{
            	result_Trade.add(new Trade("SELL", portfolio_asset.get_Asset_Name(),
                    	portfolio_asset.get_Asset_Type(),
                    	portfolio_asset.get_Asset_Quantity()));
            	portfolio_index++;
        	}
        	// Asset 'i' (specific) is in benchmark but not in portfolio
        	else if(portfolio_asset.get_Asset_Name().compareToIgnoreCase(benchmark_asset.get_Asset_Name()) > 0)
        	{
            	result_Trade.add(new Trade("BUY", benchmark_asset.get_Asset_Name(),
                    	benchmark_asset.get_Asset_Type(),
                    	benchmark_asset.get_Asset_Quantity()));
            	benchmark_index++;
        	}

       // all other remaining assets in benchmark but not in portfolio when portfolio_index reaches the end
        	if(portfolio_index >= portfolio.size())
        	{
            	for(int i = benchmark_index; i < benchmark.size(); i++)
            	{
                	Asset benchmark_temp = benchmark.get(i);
                	result_Trade.add(new Trade("BUY", benchmark_temp.get_Asset_Name(),
                        	benchmark_temp.get_Asset_Type(),
                        	benchmark_temp.get_Asset_Quantity()));
            	}
            	break; // removing break might result in an infinite while loop as portfolio_index reached end
        	}

      // all other remaining assets in portfolio but not in benchmark when benchmark_index reaches the end
        	if(benchmark_index >= benchmark.size())
        	{
            	for(int i = portfolio_index; i < portfolio.size(); i++)
            	{
                	Asset portfolio_temp = portfolio.get(i);
                	result_Trade.add(new Trade("SELL", portfolio_temp.get_Asset_Name(),
                        	portfolio_temp.get_Asset_Type(),
                        	portfolio_temp.get_Asset_Quantity()));
            	}
            	break; // removing break might result in an infinite while loop as benchmark_index reached end
        	}
    	}

    	return result_Trade;
	}
}

public class Main {
	/**
 	* Iterate through each line of input.
 	*/
	public static void main(String[] args) throws IOException {
    	InputStreamReader reader = new InputStreamReader(System.in, StandardCharsets.UTF_8);
    	BufferedReader in = new BufferedReader(reader);
    	String line;
    	while ((line = in.readLine()) != null) {
        	Main.matchBenchmark(line);
    	}
	}
    
	private static void matchBenchmark(String input)
	{
    	// Access your code here. Feel free to create other classes as required
    	String[] parsed_string = input.split(":");
    	String[] portfolio_data, benchmark_data;

/*
    	if(!input.contains(":"))
    	{
        	return;
    	}
    	else
    	{
        	boolean contains_semicolon_and_comma = input.substring(0, input.indexOf(":")).contains(",");
        	if(parsed_string.length == 2 && contains_semicolon_and_comma)
        	{
            	portfolio_data = parsed_string[0].split("\\|");
            	benchmark_data = parsed_string[1].split("\\|");
        	}
        	else if(contains_semicolon_and_comma)
        	{
            	portfolio_data = parsed_string[0].split("\\|");
            	benchmark_data = new String[0];
        	}
        	else
        	{
            	portfolio_data = new String[0];
            	benchmark_data = parsed_string[1].split("\\|");
        	}
    	}
*/

    	portfolio_data = parsed_string[0].split("\\|");
    	benchmark_data = parsed_string[1].split("\\|");

    	match_Trading new_trade = new match_Trading(portfolio_data, benchmark_data);
    	ArrayList<Trade> transactions = new_trade.start_Trading();
    	Collections.sort(transactions);

    	for(Trade t : transactions)
    	{
        	System.out.println(t.get_Transaction_Type() + "," + t.get_Trade_Name() + "," + t.get_Trade_Type()
        	+ "," + t.get_Trade_Quantity());
    	}
	}
}
