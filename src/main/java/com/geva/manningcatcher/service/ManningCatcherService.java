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
public class ManningCatcherService implements IManningCatcherService {
	
	@Autowired
	@Qualifier(value="catcher")
	private ManningCatcher catcher;
	
	@GET
	@Produces("application/json")
	@Path(value = "/catchOffer")
	@Override
	public Offer getOffer() {
		return catcher.catchLastOffer();
	}
	
	@GET
	@Produces("application/json")
	@Path(value = "/offer/{date}")
	@Override
	public Offer getOffer(@PathParam(value = "date") String date) {
		return catcher.getOffer(date);
	}
	
	@GET
	@Produces("application/json")
	@Path(value = "/list")
	@Override
	public List<Offer> getAllOffers() {
		return catcher.listAllOffers();
	}
	
	@GET
	@Produces("application/json")
	@Path(value = "/list/{title}")
	@Override
	public List<Offer> getOffers(@PathParam(value = "title") String title) {
		return catcher.listOffers(title);
	}
	
}
