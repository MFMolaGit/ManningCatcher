package com.geva.manningcatcher.utils;

public class Constants {
	
	/** CONTEXT'S PROPERTIES FILE PATH **/
	public static final String PROPERTIESPATH = "/config/context.properties";
	
	/** DEFAULT CONECTOR VALUES **/
	public static final String DEFAULTMONGOURI = "mongodb://127.0.0.1:27017/manningdb";
	public static final String DEFAULTMONGODB = "manningdb";
	public static final String DEFAULTCOLLECTION = "offers";
	
	/** PROPERTY NAMES **/
	public static final String MONGOURI = "MONGOLAB_URI";
	public static final String MONGODB = "MONGO_DB";
	
	/** COLLECTIONS PROPERTY NAMES **/
	public static final String MONGO_OFFERS_COLLECTION = "MONGO_OFFERS_COLLECTION";
	public static final String MONGO_PACKS_COLLECTION = "MONGO_PACKS_COLLECTION";
	public static final String MONGO_BOOKS_COLLECTION = "MONGO_BOOKS_COLLECTION";
	
	/** FORMATS **/
	public static final String DATEFORMAT = "dd-MM-yyyy";
	public static final String LARGE_DATEFORMAT = "dd/MM/yyyy - HH:mm:ss";

	/** HOURS **/
	public static final Integer HOUR_INCREMENT = 6;
}
