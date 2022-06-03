DROP DATABASE IF EXISTS catering;
CREATE DATABASE catering;
USE catering;

GRANT SELECT, INSERT, DELETE, UPDATE
ON catering.*
TO admin@localhost
IDENTIFIED BY 'pass@word';