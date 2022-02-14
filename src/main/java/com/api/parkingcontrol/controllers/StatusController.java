package com.api.parkingcontrol.controllers;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.parkingcontrol.documentation.Documentation;
import com.api.parkingcontrol.documentation.Request;
import com.api.parkingcontrol.enums.Author;
import com.api.parkingcontrol.enums.RequestMethod;

@RestController
@RequestMapping(path = "/parking-spot")
public class StatusController {

	@Documentation(doc = "verify status {user} and {timestamp}", 
			       author = Author.ivanSantos,
			       date = "08-02-2022",
			       api = @Request(method = RequestMethod.GET, url = "/parking-spot/status"))
	@GetMapping(path = "/status")
	public ResponseEntity<?> status() throws UnknownHostException {
		String status = "TimeStamp: " + new Date(System.currentTimeMillis()) + 
						"\n\tAddress: "+InetAddress.getLocalHost();
		return ResponseEntity.ok().body(status);
	}
}
