package com.revolut.moneytransfer.app;

import spark.Spark;

public class RohanSparkService {

	public static void main(String[] args) {
		Spark.get("/hello", (req,resp) -> {
			
		return "Hello, Rohan";	
		});
		
		Spark.get("/hello/:name", (req,resp) -> {
		return "Hello,"+req.params(":name");	
		});
	}
}
