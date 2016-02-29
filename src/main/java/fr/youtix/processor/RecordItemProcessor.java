package fr.youtix.processor;

import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.core.annotation.OnProcessError;
import org.springframework.batch.item.ItemProcessor;

import fr.youtix.model.Record;

public class RecordItemProcessor implements ItemProcessor<Record, Record> {

	private static final Logger log = LogManager.getLogger(RecordItemProcessor.class);
	private static final int ERROR_ALL_X_ITEM = 7 ;

	@Override
	public Record process(Record item) throws Exception {
		
		if(item.getId()%ERROR_ALL_X_ITEM==0) {
			throw new SQLException("SQL ERROR ON ITEM : " + item);
		}
		
		log.info(item);
		
		return item;
	}
	
	@OnProcessError
	public void onProcessError(Record item, Exception e) {
		log.error("ERROR IN PROCESSOR : " + e.getMessage());
	}

}