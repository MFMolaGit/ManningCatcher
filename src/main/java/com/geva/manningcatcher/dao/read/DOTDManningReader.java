package com.geva.manningcatcher.dao.read;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.geva.manningcatcher.beans.New;
import com.geva.manningcatcher.beans.Offer;
import com.geva.manningcatcher.beans.Pack;
import com.geva.manningcatcher.dao.ManningConverter;
import com.geva.manningcatcher.dao.MongoManningConnector;
import com.geva.manningcatcher.utils.Constants;
import com.geva.manningcatcher.utils.OfferNodes;
import com.mongodb.Block;
import com.mongodb.client.FindIterable;

public class DOTDManningReader extends ManningConverter implements ManningReader<Offer>, OfferNodes {

	private static Pattern mainBookAndCodePattern = Pattern.compile(">Get half off (.*?) - use code (dotd\\d+)<");
	private static Pattern extraBooksPattern = Pattern.compile("Use this same code to save 50% on.*?<i>(.*?)<\\/i>.*?<i>(.*?)<\\/i>");
	
    private URL url;
    private InputStream is = null;
    private BufferedReader br;
    
    private ManningReader<Pack> packManningReader;
	
    public DOTDManningReader(final String urlSource) {
	    try {
	        url = new URL(urlSource);
	    } catch (MalformedURLException mue) {
	         mue.printStackTrace();
	    }
	    
	    packManningReader = new PackManningReader();
    }
    
    @Override
	public New<Offer> read() {
    	New<Offer> newOffer = null;
        boolean codeAdded = false;
        String line;
        Date today = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat(Constants.DATEFORMAT);
        
        Offer offer = read(formatter.format(today));
        
        if(offer == null) {
        
	        try {
		        is = url.openStream();
		        br = new BufferedReader(new InputStreamReader(is));
		        newOffer = new New<Offer>(Offer.class);
	        	newOffer.getObject().setReadDate(today);
		        
		        while((line = br.readLine()) != null) {
		        	Matcher mMainBookAndCode = mainBookAndCodePattern.matcher(line);
			        	if(mMainBookAndCode.find() && !codeAdded){
			        		newOffer.getObject().addBook(mMainBookAndCode.group(1));
			        		newOffer.getObject().setOffercode(mMainBookAndCode.group(2));
			        		codeAdded = true;
			        	}
		        	
		        	Matcher mExtraBooks = extraBooksPattern.matcher(line);
			        	if(mExtraBooks.find()){
			        		newOffer.getObject().addBook(mExtraBooks.group(1));
			        		newOffer.getObject().addBook(mExtraBooks.group(2));
			        	}	        	
		        }
	        }catch (IOException ioe) {
		         ioe.printStackTrace();
		    } finally {
		        try {
		            if (is != null) is.close();
		        } catch (IOException ioe) {
		        }
		    }
	        
	        newOffer.setNew(true);
        } else {
        	newOffer = new New<Offer>(Offer.class);
        	newOffer.setObject(offer);
        	newOffer.setNew(false);
        }
        
	   return newOffer;
	}
	
    @Override
	public List<Offer> readAll() {
		final List<Offer> offers = new ArrayList<Offer>();
		final SimpleDateFormat formatter = new SimpleDateFormat(Constants.DATEFORMAT);
		FindIterable<Document> results = MongoManningConnector.connect(Constants.MONGO_OFFERS_COLLECTION).find();
		
		results.forEach(new Block<Document>() {
		    @Override
		    public void apply(final Document result) {
		    	Offer offer = new Offer();
		    	
		    	try {
					offer.setReadDate(formatter.parse((String) result.get(DATE)));
				} catch (ParseException e) {
					e.printStackTrace();
				}
		    	
		    	offer.setOffercode((String) result.getString(CODE));
		    	
		    	ObjectId packId = result.getObjectId(PACKID);
		    	
		    	offer.setPack(packManningReader.read(packId.toHexString()));
		    	
		    	offers.add(offer);
		    }
		});
		
		return offers;
	}

    @Override
	public Offer read(String date) {
		FindIterable<Document> results = MongoManningConnector.connect(Constants.MONGO_OFFERS_COLLECTION).find(new Document(DATE, date));
		final SimpleDateFormat formatter = new SimpleDateFormat(Constants.DATEFORMAT);		
		Document result = results.first();
		Offer offer = null;
		
		if(result != null) {
			try {
				offer = new Offer();
				offer.setReadDate(formatter.parse((String) result.get(DATE)));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			offer.setOffercode((String) result.get(CODE));

			ObjectId packId = result.getObjectId(PACKID);
	    	
	    	offer.setPack(packManningReader.read(packId.toHexString()));
		}
		
		return offer;
	}
}
