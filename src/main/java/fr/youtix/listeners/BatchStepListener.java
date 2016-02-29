package fr.youtix.listeners;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;

public class BatchStepListener implements StepExecutionListener {
	
	private static final Logger log = LogManager.getLogger(BatchStepListener.class);
	
	private DataSource datasource;
	private Connection connection;

	@Override
	public void beforeStep(StepExecution arg0) {
		try {
			connection = datasource.getConnection();
		} catch (SQLException e) {
			log.error(e);
		}
	}

	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		
		String sqlQuery = "SELECT * FROM RECORDS";
		StringBuilder res = new StringBuilder("Records ids inserted in database : ");
		
		try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);) {
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				res.append(rs.getInt(1)).append(",");
			}
			log.info(res.deleteCharAt(res.length()-1));
		} catch(Exception e) {
			log.error(e);
		}
		
		log.info(stepExecution.getSummary());
		
		return null;
	}

	public DataSource getDatasource() {
		return datasource;
	}

	public void setDatasource(DataSource datasource) {
		this.datasource = datasource;
	}

}
