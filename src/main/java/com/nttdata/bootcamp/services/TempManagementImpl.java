package com.nttdata.bootcamp.services;

import org.springframework.stereotype.Service;

@Service
public class TempManagementImpl implements TempManagementI{
	public float celsiusAFahrenheit(float celsius) {
	    return (celsius * 1.8f) + 32;
	}
	public float fahrenheitACelsius(float fahrenheit) {
	    return (fahrenheit - 32) / 1.8f;
	}

}
