package com.geva.manningcatcher.business;

import java.util.List;

import org.bson.types.ObjectId;

import com.geva.manningcatcher.beans.New;
import com.geva.manningcatcher.beans.Offer;
import com.geva.manningcatcher.dao.read.DOTDManningReader;
import com.geva.manningcatcher.dao.read.ManningReader;
import com.geva.manningcatcher.dao.write.ManningWriter;
import com.geva.manningcatcher.dao.write.OfferManningWriter;
import com.geva.manningcatcher.utils.OfferNodes;


public class ManningCatcher {

	private static final String URLMANNINGOFFERS = "https://manning.com/dotd";
	
	private ManningReader<Offer> offerReader;
	
	private ManningWriter<Offer> offerWriter;
	
	public ManningCatcher() {
		offerReader = new DOTDManningReader(URLMANNINGOFFERS);
		offerWriter = new OfferManningWriter();
	}
	
	public Offer catchNewOffer() {
		
		New<Offer> newOffer = offerReader.read();
		Offer offer = newOffer.getObject();
		
		if(newOffer.isNewOffer()) {
//			statisticOffer(offer);
			offerWriter.write(offer);
		}
		
		return offer;
	}
	
	public List<Offer> listOffers() {
		return offerReader.readAll();
	}

	public Offer getOffer(String date) {
		return offerReader.read(OfferNodes.DATE, date);
	}

//	private void statisticOffer(Offer offer) {
//		List<Offer> oldOffers = listOffers();
//		boolean isNewPack = true;
//		List<Long> packIds = new ArrayList<Long>();
//		
//		for(Offer oldOffer:oldOffers) {
//			if(offer.getBooks().containsAll(oldOffer.getBooks())) {
//				Pack existingPack = oldOffer.getPack();
//				existingPack.addTime();
//				offer.setPack(existingPack);
//				isNewPack = false;
//				packIds.add(oldOffer.getPack().getId());
//			}
//		}
//		
//		if(isNewPack) {
//			offer.getPack().setId(generateNewPackId(packIds));
//			offer.getPack().addTime();
//		}
//	}
//
//	private Long generateNewPackId(List<Long> packIds) {
//		return LongRandomGenerator.generateId(packIds);
//	}
	
}
