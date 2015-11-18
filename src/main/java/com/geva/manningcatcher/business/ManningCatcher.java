package com.geva.manningcatcher.business;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.geva.manningcatcher.beans.New;
import com.geva.manningcatcher.beans.Offer;
import com.geva.manningcatcher.dao.read.ManningReader;
import com.geva.manningcatcher.dao.write.ManningWriter;
import com.geva.manningcatcher.utils.Constants;
import com.geva.manningcatcher.utils.OfferNodes;

@Component(value="catcher")
public class ManningCatcher {
	
	private static Log log = LogFactory.getLog(ManningCatcher.class.getName());

	@Autowired
	@Qualifier(value="offerReader")
	private ManningReader<Offer> offerReader;
	
	@Autowired
	@Qualifier(value="offerWriter")
	private ManningWriter<Offer> offerWriter;
	
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.LARGE_DATEFORMAT);
	
	public Offer catchLastOffer() {

		Offer lastOffer = readOffer();
		
			log.info("ManningCatcher - catchLastOffer - " +
					"Se captura una oferta (" + lastOffer.getOffercode() + ") desde ejecucion manual a las " + 
					dateFormat.format(new Date()));
		
		return lastOffer;
	}
	
	@Scheduled(cron = "${TIMETOCATCH}")
	public void catchNewOffer() {

		Offer lastOffer = readOffer();
		
			log.info("ManningCatcher - catchNewOffer - " +
					"Se captura una oferta (" + lastOffer.getOffercode() + ") desde ejecucion automatica a las " + 
					dateFormat.format(new Date()));
	}
	
	public List<Offer> listOffers() {
		return offerReader.readAll();
	}

	public Offer getOffer(String date) {
		return offerReader.read(OfferNodes.DATE, date);
	}

	private Offer readOffer() {
		New<Offer> newOffer = offerReader.read();
	    Offer lastOffer = newOffer.getObject();
		
		if(newOffer.isNewOffer()) {
//			statisticOffer(offer);
			offerWriter.write(lastOffer);
		}
		
		return lastOffer;
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
