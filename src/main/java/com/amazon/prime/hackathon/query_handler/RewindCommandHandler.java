package com.amazon.prime.hackathon.query_handler;

public class RewindCommandHandler implements IQueryHandler{


	public RewindCommandHandler() {
		
	}

	@Override
	public Integer handle(Integer currentTimeStamp, String data) {
		String[] dataArr = data.split(" ");
		if(dataArr.length>1) {
			final String unit = dataArr[dataArr.length-1].toLowerCase();
			final String wordNumber = data.toLowerCase().replace(unit, "").trim();
			if(unit.startsWith("sec")) {
				return - WordNumberHelper.getNum(wordNumber);
			}
			if(unit.startsWith("min")) {
				return  - (WordNumberHelper.getNum(wordNumber)* 60);
			}
			if(unit.startsWith("hour")) {
				return - (WordNumberHelper.getNum(wordNumber)* 60 * 60);
			}
		}
		return currentTimeStamp;
	}
	
}
