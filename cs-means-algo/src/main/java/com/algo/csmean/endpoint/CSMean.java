package com.algo.csmean.endpoint;

import java.io.BufferedReader;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

import com.algo.csmean.model.response.ClusterDataResponse;
import com.algo.csmean.service.CSMeanService;
import com.algo.csmean.util.CSVUtil;

@Controller
@Path("cs-mean")
public class CSMean {
	
	@Autowired
	CSMeanService csMeanService;
	
	@Autowired
	
	CSVUtil csvUtil;
	
	@Value("${data.file.csv.location}")
	private String dataFilePath;
	
	
	@GET
	@Path("/create-cluster")
	@Produces("application/json")
	@Consumes("application/json")
	public Response getReferenceValues(@QueryParam("noOfFeature") Integer noOfFeature,
			@QueryParam("clusterStrictness") Double clusterStrictness,@QueryParam("fileName") String fileName) throws Exception
	{
		long startTime = System.nanoTime();
		BufferedReader br=null;
		try {
			br = csvUtil.readCSV(dataFilePath+fileName+".csv");
			ClusterDataResponse clusterData=csMeanService.createClusters(br,noOfFeature,clusterStrictness);
			long endTime   = System.nanoTime();
			System.out.println(startTime);
			System.out.println(endTime);
			long totalTime = endTime - startTime;
			clusterData.setTotalMiliSecondTime(totalTime/1000000);
			return Response.ok().entity(clusterData).build();
		}catch(Exception ex) {
			throw ex;
		}finally {
			br.close();
		}
	}
}
