drop table if exists apichange;
CREATE TABLE `apichange` (
   `apichange_id` int(11) NOT NULL AUTO_INCREMENT,
   `repository_id` int(11) DEFAULT NULL,
   `commit_id` varchar(100) DEFAULT NULL,
   `parent_commit_id` varchar(100) DEFAULT NULL,
   `new_file_name` varchar(255) DEFAULT NULL,
   `old_file_name` varchar(255) DEFAULT NULL,
   `line_number` int(11) DEFAULT NULL,
   `change_type` varchar(10) DEFAULT NULL,
   `content` longtext,
   `complete_class_name` varchar(255) DEFAULT NULL,
   `method_name` varchar(255) DEFAULT NULL,
   `parameter` varchar(255) DEFAULT NULL,
   PRIMARY KEY (`apichange_id`)
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8;