package com.geva.manningcatcher.dao.write;

import java.text.SimpleDateFormat;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.geva.manningcatcher.beans.Offer;
import com.geva.manningcatcher.beans.Pack;
import com.geva.manningcatcher.utils.Constants;
import com.geva.manningcatcher.utils.OfferNodes;
import com.mongodb.client.MongoCollection;

@Component(value="offerWriter")
public class OfferManningWriter implements ManningWriter<Offer>, OfferNodes {

	@Autowired
	@Qualifier(value="packWriter")
	private ManningWriter<Pack> packWriter;
	
	@Autowired
	@Qualifier(value="offersCollection")
	private MongoCollection<Document> offersCollection;

//	@Autowired
//	public OfferManningWriter(MongoCollection<Document> collection) {
//		this.offersCollection = collection;
//	}
	
	@Override
	public String write(Offer offer) {
		SimpleDateFormat formatter = new SimpleDateFormat(Constants.DATEFORMAT);
		String offerId = formatter.format(offer.getReadDate());
		String packId = packWriter.write(offer.getPack());
		
		Document offerDoc = new Document(DATE, offerId)
	        .append(CODE, offer.getOffercode())
			.append(PACKID, new ObjectId(packId));
		
		offersCollection.insertOne(offerDoc);
		
		return offerId;
	}
	
}
