CREATE TABLE rules (
  id INTEGER NOT NULL PRIMARY KEY,
  name varchar(255),
  description varchar(255),
  version varchar(8),
  comparison varchar(255),
  action varchar(255)
);
CREATE INDEX name_index ON rules(name);