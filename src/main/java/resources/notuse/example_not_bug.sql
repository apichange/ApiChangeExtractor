drop table if exists example_not_bug;
CREATE TABLE `example_not_bug` (
   `example_id` int(11) NOT NULL AUTO_INCREMENT,
   `change_type` varchar(255) DEFAULT NULL,
   `old_complete_class_name` varchar(255) DEFAULT NULL,
   `new_complete_class_name` varchar(255) DEFAULT NULL,
   `old_method_name` varchar(255) DEFAULT NULL,
   `new_method_name` varchar(255) DEFAULT NULL,
   `outer_repeat_num` int(11) DEFAULT NULL,
   PRIMARY KEY (`example_id`)
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8;