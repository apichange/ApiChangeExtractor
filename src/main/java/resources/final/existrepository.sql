drop table if exists existrepository;
CREATE TABLE `temp`.`existrepository` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT '',
  `repository_id` INT NULL COMMENT '',
  `github_id` INT NULL COMMENT '',
  `name` VARCHAR(255) NULL COMMENT '',
  `user_id` INT NULL COMMENT '',
  `user_name` VARCHAR(255) NULL COMMENT '',
  `website` VARCHAR(255) NULL COMMENT '',
  `stars` INT NULL COMMENT '',
  `address` LONGTEXT NULL COMMENT '',
  PRIMARY KEY (`id`)  COMMENT '');
