CREATE TABLE ${SCHEMA}.cfg_data_migration (
	id varchar NOT NULL,
	start_exec timestamp NOT NULL,
	restart_exec timestamp NULL,
	end_exec timestamp NULL,
	status varchar NOT NULL,
	last_executed_step varchar NOT NULL,
	detail jsonb NOT NULL,
	CONSTRAINT cfg_data_migration_pk PRIMARY KEY (id)
);
