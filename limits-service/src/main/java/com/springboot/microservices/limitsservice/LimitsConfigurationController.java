package com.springboot.microservices.limitsservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.springboot.microservices.limitsservice.bean.LimitsConfiguration;

@RestController
public class LimitsConfigurationController {
	
	@Autowired
	private Configuration configuration;
	
	@GetMapping("/limits")
	public LimitsConfiguration retreiveLimitsFromConfigurations()
	{
		return new LimitsConfiguration(configuration.getMaximum(),configuration.getMinimum());
	}
	
	@GetMapping("/faulttolerancelimits")
	@HystrixCommand(fallbackMethod="defaultLimits")
	public LimitsConfiguration retreiveLimitsFaultToleranceFromConfigurations()
	{
		throw new RuntimeException("Issue");
	}
	
	private LimitsConfiguration defaultLimits()
	{
		return new LimitsConfiguration(999,9);
	}

}
