package com.amazon.prime.hackathon;

import com.amazon.prime.hackathon.query_handler.MainQueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class APIController {
    @Autowired
    private MainQueryHandler mainQueryHandler;

    @RequestMapping(value = "timestamp/{timestamp}/query/{query}", method = RequestMethod.GET)
    @ResponseBody
    public Integer getTimeStamp(@PathVariable Integer timestamp,
                                @PathVariable String query,
                                @PathVariable String videoId) {
        return mainQueryHandler.handleQuery(videoId, timestamp, query);
    }
}
