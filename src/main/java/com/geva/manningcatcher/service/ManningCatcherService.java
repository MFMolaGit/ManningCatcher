package com.geva.manningcatcher.service;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import com.geva.manningcatcher.beans.Offer;
import com.geva.manningcatcher.business.ManningCatcher;

@Component
@Path("/manningCatcher")
@EnableScheduling
public class ManningCatcherService {
	
	@Autowired
	@Qualifier(value="catcher")
	private ManningCatcher catcher;
	
	@GET
	@Produces("application/json")
	@Path(value = "/catchOffer")
	public Offer getOffer() {
		return catcher.catchLastOffer();
	}
	
	@GET
	@Produces("application/json")
	@Path(value = "/offer/{date}")
	public Offer getOffer(@PathParam(value = "date") String date) {
		return catcher.getOffer(date);
	}
	
	@GET
	@Produces("application/json")
	@Path(value = "/list")
	public List<Offer> getAllOffers() {
		return catcher.listOffers();
	}
	
}
