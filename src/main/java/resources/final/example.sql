drop table if exists example_7th;
CREATE TABLE `example_7th` (
   `example_id` int(11) NOT NULL AUTO_INCREMENT,
   `change_type` varchar(255) DEFAULT NULL,
   `old_complete_class_name` varchar(255) DEFAULT NULL,
   `new_complete_class_name` varchar(255) DEFAULT NULL,
   `old_method_name` varchar(255) DEFAULT NULL,
   `new_method_name` varchar(255) DEFAULT NULL,
   `parameter_position` int(11) DEFAULT -1,
   `outer_repeat_num` int(11) DEFAULT NULL,
   `bug_total` int(11) DEFAULT NULL,
   `bug_r` int(11) DEFAULT NULL,
   `bug_rank` DOUBLE DEFAULT NULL,
   `bug_total_rank` DOUBLE DEFAULT NULL,
   PRIMARY KEY (`example_id`)
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8;