package com.alfonso.springbatch.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.OverrideAutoConfiguration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.alfonso.springbatch.model.Persona;


@Component
public class JobListener extends JobExecutionListenerSupport{

	private static final Logger LOG = LoggerFactory.getLogger(JobListener.class);  
	
	private JdbcTemplate jdbcTemplate;

	@Autowired
	public JobListener(JdbcTemplate jdbcTemplate) {
		super();
		this.jdbcTemplate = jdbcTemplate;
	}
	
	@Override
	public void afterJob(JobExecution jobExecution) {

		if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
			LOG.info("FINALIZÓ EL JOB");
			String select = "SELECT primer_nombre, segundo_nombre, telefono FROM persona";
			
			jdbcTemplate
			.query("SELECT primer_nombre, segundo_nombre, telefono FROM persona", 
					(rs, row) -> new Persona(rs.getString(1), rs.getString(2), rs.getString(3)))
			.forEach(persona -> LOG.info("Registro <" + persona +">"));
		}
		if (jobExecution.getStatus() == BatchStatus.STARTING) {
			LOG.info("EMPEZANDO EL JOB");
		}
		if (jobExecution.getStatus() == BatchStatus.STARTED) {
			LOG.info("EMPEZADO EL JOB");
		}
		if (jobExecution.getStatus() == BatchStatus.STOPPING) {
			LOG.info("PARANDO EL JOB");
		}
		if (jobExecution.getStatus() == BatchStatus.STOPPED) {
			LOG.info("PARADO EL JOB");
		}
		if (jobExecution.getStatus() == BatchStatus.ABANDONED) {
			LOG.info("ABANDONADO EL JOB");
		}
		if (jobExecution.getStatus() == BatchStatus.FAILED) {
			LOG.info("FALLADO EL JOB");
		}
		if (jobExecution.getStatus() == BatchStatus.UNKNOWN) {
			LOG.info("DESCONOCIDO EL JOB");
		}
		
	}
	@Override
	public void beforeJob(JobExecution jobExecution) {
		LOG.info("ANTES DE EMPEZAR EL JOB");
	
	}
	
}
