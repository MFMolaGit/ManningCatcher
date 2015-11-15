package com.geva.manningcatcher.business.adapters;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class DateAdapter extends XmlAdapter<String, Date> {
	
	private static final String DATEFORMAT = "dd/MM/yyyy";

    private static SimpleDateFormat dateFormat = new SimpleDateFormat(DATEFORMAT);

    @Override
    public String marshal(Date v) throws Exception {
        return dateFormat.format(v);
    }

    @Override
    public Date unmarshal(String v) throws Exception {
        return dateFormat.parse(v);
    }
    
    public static SimpleDateFormat getDateFormat() {
    	return dateFormat;
    }

}
