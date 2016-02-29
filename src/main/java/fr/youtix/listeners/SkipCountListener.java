package fr.youtix.listeners;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.core.SkipListener;

import fr.youtix.model.Record;

public class SkipCountListener implements SkipListener<Record, Record> {
	
	private static final Logger log = LogManager.getLogger(SkipCountListener.class);

	@Override
	public void onSkipInRead(Throwable t) {
		log.warn("SKIP IN READER " + t.getMessage());
	}

	@Override
	public void onSkipInProcess(Record item, Throwable t) {
		log.warn("SKIP IN PROCESSOR : " + item);
	}

	@Override
	public void onSkipInWrite(Record item, Throwable t) {
		log.warn("SKIP IN WRITER : " + item);
	}
}
