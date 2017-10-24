CREATE TABLE `user` ( 
	`id` bigint(20) NOT NULL AUTO_INCREMENT, 
	`last_name` varchar(30) DEFAULT NULL, 
	`first_name` varchar(30) DEFAULT NULL, 
	PRIMARY KEY (`id`) 
)

CREATE TABLE image (
   id BIGINT(20) NOT NULL AUTO_INCREMENT,
   data TEXT(1000000) NOT NULL,
   user_id BIGINT(20) NOT NULL,
   prediction VARCHAR(10000) NOT NULL,
   submission_date DATE,
   PRIMARY KEY ( id ), 
   FOREIGN KEY ( user_id ) REFERENCES user( id )
)