package com.dmj.Netview;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.dmj.Netview.servicios.StreamingService;

import reactor.core.publisher.Mono;

@Configuration  
@RestController
@SpringBootApplication
public class NetviewApplication {

	@Autowired
    private StreamingService service;
	
	public static void main(String[] args) {
		
		SpringApplication.run(NetviewApplication.class, args);
	}
	
	 @GetMapping(value = "video/{title}", produces = "video/mp4")
	    public Mono<Resource> getVideos(@PathVariable String title, @RequestHeader("Range") String range) {
	        System.out.println("range in bytes() : " + range);
	        return service.getVideo(title);
	    }

}
