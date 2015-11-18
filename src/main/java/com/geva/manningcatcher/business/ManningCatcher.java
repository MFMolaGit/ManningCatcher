package com.geva.manningcatcher.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.geva.manningcatcher.beans.New;
import com.geva.manningcatcher.beans.Offer;
import com.geva.manningcatcher.dao.read.ManningReader;
import com.geva.manningcatcher.dao.write.ManningWriter;
import com.geva.manningcatcher.utils.OfferNodes;

@Component(value="catcher")
public class ManningCatcher {

	@Autowired
	@Qualifier(value="offerReader")
	private ManningReader<Offer> offerReader;
	
	@Autowired
	@Qualifier(value="offerWriter")
	private ManningWriter<Offer> offerWriter;
	
	private Offer lastOffer;
	
	public Offer catchLastOffer() {
		return lastOffer;
	}
	
	public List<Offer> listOffers() {
		return offerReader.readAll();
	}

	public Offer getOffer(String date) {
		return offerReader.read(OfferNodes.DATE, date);
	}

	@Scheduled(cron = "${TIMETOCATCH}")
	public void catchNewOffer() {
		New<Offer> newOffer = offerReader.read();
	    lastOffer = newOffer.getObject();
		
		if(newOffer.isNewOffer()) {
//			statisticOffer(offer);
			offerWriter.write(lastOffer);
		}
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
