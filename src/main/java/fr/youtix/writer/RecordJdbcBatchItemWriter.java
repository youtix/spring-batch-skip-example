package fr.youtix.writer;

import java.sql.SQLException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.core.annotation.OnWriteError;
import org.springframework.batch.item.database.JdbcBatchItemWriter;

import fr.youtix.model.Record;

public class RecordJdbcBatchItemWriter extends JdbcBatchItemWriter<Record>{

	private static final Logger log = LogManager.getLogger(RecordJdbcBatchItemWriter.class);
	private static final int ERROR_ALL_X_ITEM = 19 ;

	@Override
	public void write(List<? extends Record> listOfRecords) throws Exception {
		super.write(listOfRecords);
		
		for(Record r : listOfRecords) {
			if(r.getId()%ERROR_ALL_X_ITEM==0) {
				throw new SQLException("SQL ERROR ON ITEM : " + r);
			}
		}
		
		log.info("Write " + listOfRecords.size() + " record(s) in database.");
	}
	
	@OnWriteError
	public void onWriteError(Exception exception, List<? extends Record> items) {
		log.error("ERROR DETECTED IN WRITER " + exception.getMessage());
	}

}
