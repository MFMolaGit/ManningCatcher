package com.geva.manningcatcher.service;

import java.util.List;

import com.geva.manningcatcher.beans.Offer;

public interface IManningCatcherService {

	public Offer getOffer();
	
	public Offer getOffer(final String date);
	
	public List<Offer> getAllOffers();
	
	public List<Offer> getOffers(final String title);
	
}
