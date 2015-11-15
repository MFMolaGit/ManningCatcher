package com.geva.manningcatcher.dao;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;

import com.geva.manningcatcher.service.ManningCatcherService;
import com.geva.manningcatcher.utils.Constants;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class MongoManningConnector {

	private static MongoDatabase mongoDB;
	private static MongoClient mongoClient;
	private String collectionParam;
	
	@Autowired
	public MongoManningConnector(final String collectionParam) {
		this.collectionParam = collectionParam;
	}
	
	public MongoCollection<Document> connect() {
		
		MongoCollection<Document> mongoCollection = null;
		String uriMongo, dbMongo, collection;
		InputStream propFile;
		try {
			propFile = ManningCatcherService.class.getResourceAsStream(Constants.PROPERTIESPATH);
	        Properties p = new Properties(System.getProperties());
    			p.load(propFile);
	        System.setProperties(p);
	        
			uriMongo = System.getProperty(Constants.MONGOURI);
			dbMongo = System.getProperty(Constants.MONGODB);
			collection = System.getProperty(collectionParam);
	        
		} catch (IOException e) {
			e.printStackTrace();
			
			uriMongo = Constants.DEFAULTMONGOURI;
			dbMongo = Constants.DEFAULTMONGODB;
			collection = Constants.DEFAULTCOLLECTION;
		}
		
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
