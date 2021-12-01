package com.nttdata.bootcamp.controllers;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nttdata.bootcamp.controllers.TestController;
import com.nttdata.bootcamp.services.TempManagementI;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import tempservice.Temp;

@RestController
public class TestController {
	@Autowired
	TempManagementI tempManagementI;

	private final static Logger Logger = LoggerFactory.getLogger(TestController.class);
	
	private Counter llamadasTotales;
	private Counter llamadasMyValue;
	
	public TestController (MeterRegistry registry) {
		this.llamadasTotales = Counter.builder("invocaciones.Totales").description("Invocaciones totales").register(registry);
		this.llamadasMyValue = Counter.builder("invocaciones.MyValue").description("Invocaciones myValue").register(registry);
	}
	@Autowired
	private Temp temp;
	
	@GetMapping("/")
	public ResponseEntity<String> index(){
		Logger.info("Llamada al endpoint devolverTemp.");
		llamadasTotales.increment();
		return new ResponseEntity<String>(HttpStatus.OK).ok(temp.devolverTemp());
	}
	
	@Value("${some.value}")
	private String myValue;
	
	@GetMapping(path="/myValue")
	public String myValue(@RequestParam("grados") int grados) {
		llamadasMyValue.increment();
		Logger.info("Llamada temperatura con perfiles");
		if(myValue.equals("F")) {
			return "Temperatura:"+tempManagementI.fahrenheitACelsius(grados);
		}else {
			return "Temperatura:"+tempManagementI.celsiusAFahrenheit(grados);
		}
		
	}
}
