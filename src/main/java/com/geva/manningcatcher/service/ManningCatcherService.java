package com.geva.manningcatcher.service;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import com.geva.manningcatcher.beans.Offer;
import com.geva.manningcatcher.business.ManningCatcher;

@Path("/manningCatcher")
public class ManningCatcherService {
	
	private ManningCatcher catcher;
	
	public ManningCatcherService() {
		catcher = new ManningCatcher();
	}
	
	@GET
	@Produces("application/xml")
	@Path(value = "/catchOffer")
	public Offer getOffer() {
		return catcher.catchNewOffer();
	}
	
	@GET
	@Produces("application/xml")
	@Path(value = "/offer/{date}")
	public Offer getOffer(@PathParam(value = "date") String date) {
		return catcher.getOffer(date);
	}
	
	@GET
	@Produces("application/xml")
	@Path(value = "/list")
	public List<Offer> getAllOffers() {
		return catcher.listOffers();
	} 
}
