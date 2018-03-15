drop table if exists top_apis;
CREATE TABLE `top_apis` (
   `api_id` int(11) NOT NULL AUTO_INCREMENT,
   `class_name` varchar(255) DEFAULT NULL,
   `method_name` varchar(255) DEFAULT NULL,
   `bug_total` int(11) DEFAULT NULL,
   `notbug_total` int(11) DEFAULT NULL,
   `bug_r` int(11) DEFAULT NULL,
   `notbug_r` int(11) DEFAULT NULL,
   `test_rate` Double DEFAULT NULL,
   `bug_rank` Double DEFAULT NULL,
   `notbug_rank` Double DEFAULT NULL,
   PRIMARY KEY (`api_id`)
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8;