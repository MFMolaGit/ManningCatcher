package com.geva.manningcatcher.dao.write;

import java.text.SimpleDateFormat;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.geva.manningcatcher.beans.Offer;
import com.geva.manningcatcher.beans.Pack;
import com.geva.manningcatcher.dao.MongoManningConnector;
import com.geva.manningcatcher.utils.Constants;
import com.geva.manningcatcher.utils.OfferNodes;
import com.mongodb.client.MongoCollection;

public class OfferManningWriter implements ManningWriter<Offer>, OfferNodes {

	private ManningWriter<Pack> packManningWriter;
	
	private MongoCollection<Document> collection;
	
	public OfferManningWriter() {
		packManningWriter = new PackManningWriter();
		collection = MongoManningConnector.connect(Constants.MONGO_OFFERS_COLLECTION);
	}
	
	@Override
	public String write(Offer offer) {
		SimpleDateFormat formatter = new SimpleDateFormat(Constants.DATEFORMAT);
		String offerId = formatter.format(offer.getReadDate());
		String packId = packManningWriter.write(offer.getPack());
		
		Document offerDoc = new Document(DATE, offerId)
	        .append(CODE, offer.getOffercode())
			.append(PACKID, new ObjectId(packId));
		
		collection.insertOne(offerDoc);
		
		return offerId;
	}
	
}
