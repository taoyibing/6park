-- Scripts to create test result database schema
-- example: psql -h host -U username -d database -a -f createtables.sql

CREATE TABLE sites (
	id			serial,
	url			varchar(255),
	UNIQUE (url), 
	PRIMARY KEY (id)
);

CREATE TABLE links (
	id 			serial,
	site_id	 	integer REFERENCES sites(id),
	name		varchar(255),
	link   		varchar(255),

	UNIQUE(link),
	PRIMARY KEY (id)
);





