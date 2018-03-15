drop table if exists high_project_info;
CREATE TABLE `high_project_info` (
  `repository_id` INT NOT NULL COMMENT '',
  `website` VARCHAR(255) NULL COMMENT '',
  `stars` INT NULL COMMENT '',
  `blank_loc` INT NULL COMMENT '',
  `comment_loc` INT NULL COMMENT '',
  `code_loc` INT NULL COMMENT '',
  `commits` INT NULL COMMENT '',
  `bug_commits` INT NULL COMMENT '',
  `aord_bug_commits` INT NULL COMMENT '',
  `filter_time` INT NULL COMMENT '',
  `diff_time` INT NULL COMMENT '',
  `match_time` INT NULL COMMENT '',
  `count_time` INT NULL COMMENT '',
  `total_time` INT NULL COMMENT '',
  PRIMARY KEY (`repository_id`)  COMMENT '');
