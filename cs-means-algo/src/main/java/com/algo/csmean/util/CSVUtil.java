package com.algo.csmean.util;

import java.io.BufferedReader;
import java.io.FileReader;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.algo.csmean.serviceImpl.CSMeanServiceImpl;

@Component
public class CSVUtil {
	
	final static Logger logger=Logger.getLogger(CSMeanServiceImpl.class); 

	public BufferedReader readCSV(String dataFilePath) throws Exception {
		BufferedReader br = null;
		 try { 
			  br= new BufferedReader(new FileReader(dataFilePath));
			  return br;
		  } catch(Exception e) {
			  logger.error("error while reading input data file");
			  e.printStackTrace(); 
			  throw e;
		}
		
	}
}
