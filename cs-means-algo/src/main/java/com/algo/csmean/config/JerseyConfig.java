package com.algo.csmean.config;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

import com.algo.csmean.endpoint.CSMean;

@Component
@ApplicationPath("/api")
public class JerseyConfig extends ResourceConfig
{
	public JerseyConfig()
	{
		register(CSMean.class);
		
	}
}
