-- Scripts to create test result database schema
-- example: psql -h host -U username -d database -a -f createtables.sql

CREATE TABLE sites (
	id			serial,
	url			varchar(255),
	url_pattern	varchar(255), //regex pattern to reconstruct URL based on tid
	UNIQUE (url), 
	PRIMARY KEY (id)
);

CREATE TABLE links (
	site_id	 	integer REFERENCES sites(id),
	tid			integer,
	parent_tid  integer,
	title		varchar(255),
	size		integer
	children_size integer,
	content		text,

	PRIMARY KEY (tid)
);





