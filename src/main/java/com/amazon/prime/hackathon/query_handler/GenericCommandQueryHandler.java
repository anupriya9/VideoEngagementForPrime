package com.amazon.prime.hackathon.query_handler;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import org.springframework.stereotype.Component;

import com.algorithmia.AlgorithmException;
import com.amazon.prime.hackathon.ner.IAudioDescriptionDataProvider;
import com.amazon.prime.hackathon.ner.INamedEntitiesProvider;

@Component
public class GenericCommandQueryHandler implements IQueryHandler {
    private IAudioDescriptionDataProvider audioDescriptionDataProvider;

    private INamedEntitiesProvider namedEntitiesProvider;

    public GenericCommandQueryHandler(INamedEntitiesProvider namedEntitiesProvider,
                                      IAudioDescriptionDataProvider audioDescriptionDataProvider){
        this.audioDescriptionDataProvider = audioDescriptionDataProvider;
        this.namedEntitiesProvider = namedEntitiesProvider;
    }

    @Override
    public Integer handle(final String videoId, Integer currentTimeStamp, String command) {

        try {
            Set<String> querySet = namedEntitiesProvider.getNamedEntities(command);
            Map<Integer,Set<String>> audioDescriptionMap = audioDescriptionDataProvider.getData(videoId);
            AtomicInteger maxScore = new AtomicInteger();
            AtomicInteger scoreVal = new AtomicInteger();
            AtomicReference<Integer> timestamp = new AtomicReference<>(0);
            audioDescriptionMap.forEach((time,score) -> {
                scoreVal.set(0);
                querySet.forEach(queryWord-> {
                    if(score.contains(queryWord)){
                        scoreVal.addAndGet(1);
                    }
                });
                if(maxScore.get() < scoreVal.get()){
                    maxScore.set(scoreVal.get());
                    timestamp.set(time);
                }
            });


            return timestamp.get();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (AlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}
