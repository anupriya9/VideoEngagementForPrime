package com.amazon.prime.hackathon.query_handler;

import com.algorithmia.AlgorithmException;
import com.amazon.prime.hackathon.ner.IDataProvider;
import com.amazon.prime.hackathon.ner.INamedEntitiesProvider;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class SpeechLookupCommandHandler implements IQueryHandler{

	private final IDataProvider dataProvider;

	private final INamedEntitiesProvider namedEntitiesProvider;

	public SpeechLookupCommandHandler(INamedEntitiesProvider namedEntitiesProvider,
								 IDataProvider dataProvider){
		this.dataProvider = dataProvider;
		this.namedEntitiesProvider = namedEntitiesProvider;
	}

	@Override
	public Integer handle(final String videoId, Integer currentTimeStamp, String command) {

		try {
			Set<String> querySet = namedEntitiesProvider.getNamedEntities(command);
			Map<Integer,Set<String>> audioDescriptionMap = dataProvider.getData(videoId);
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


			return timestamp.get() - currentTimeStamp;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (AlgorithmException e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
}
