package org.cz.project.controller;

import java.util.HashMap;
import java.util.Map;

import org.cz.project.entity.bean.Result;
import org.cz.project.service.ProbeAwsPerHourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
@Controller
public class ProbeEnviPerHourController {
	@Autowired ProbeAwsPerHourService probeAwsPerHourService;
	
	@RequestMapping(value="/get_lastest_probe_aws_per_hour", method=RequestMethod.GET)
	@ResponseBody
	public Result getLastestProbeAwsPerHour(){
		System.out.println("123456");
		Result result=new Result();
		result.setStatus("success");
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("longitude", 120.35);
		params.put("latitude", 29.91);
		result.setResult(probeAwsPerHourService.getLatestProbeAwsPerHour(params));
		return result;
		
	}
	
}
