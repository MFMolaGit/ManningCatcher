package com.geva.manningcatcher.dao;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class MongoManningConnector {

	private static MongoDatabase mongoDB;
	private static MongoClient mongoClient;
	private	String uriMongo, dbMongo, collection;
	
	@Autowired
	public MongoManningConnector(final String collection, final String uriMongo, final String dbMongo) {
		this.collection = collection;
		this.uriMongo = uriMongo;
		this.dbMongo = dbMongo;
	}
	
	public MongoCollection<Document> connect() {
		
		MongoCollection<Document> mongoCollection = null;
		MongoClientURI  mongolabUri = new MongoClientURI(uriMongo);

		try {
			mongoClient = new MongoClient(mongolabUri);
			mongoDB = mongoClient.getDatabase(dbMongo);
			
			mongoCollection = mongoDB.getCollection(collection);
	        
		} catch(MongoException e) {
			e.printStackTrace();
		}
		
		return mongoCollection;
	}
	
}
