package com.amazon.prime.hackathon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.amazon.prime.hackathon.query_handler.MainQueryHandler;
 
@RestController
@RequestMapping(path = "/command")
public class CommandQueryController
{
    @Autowired
    private MainQueryHandler handler;
     
    @GetMapping(path="/query", produces = "application/json")
    public Integer getTimestamp(@RequestParam("timestamp") int timestamp, @RequestParam("command") String command)
    {
    	System.out.println(timestamp);
    	System.out.println(command);
    	

    	return handler.handleQuery(timestamp, command);
    }
}