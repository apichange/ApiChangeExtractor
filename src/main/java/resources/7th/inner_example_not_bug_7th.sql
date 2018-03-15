drop table if exists inner_example_not_bug_7th;
CREATE TABLE `inner_example_not_bug_7th` (
   `inner_example_id` int(11) NOT NULL AUTO_INCREMENT,
   `repository_id` int(11) DEFAULT NULL,
   `example_id` int(11) DEFAULT NULL,
   `inner_repeat_num` int(11) DEFAULT NULL,
   PRIMARY KEY (`inner_example_id`)
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8;