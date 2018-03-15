SET SQL_SAFE_UPDATES = 0;
update top_apis_fix t inner join 
(SELECT a.old_complete_class_name,a.old_method_name, count(distinct c.repository_id) as r, sum(c.inner_repeat_num) as sum
FROM example_7th as a, top_apis_fix as b, inner_example_7th as c
WHERE a.old_complete_class_name = b.class_name AND a.old_method_name = b.method_name AND a.example_id = c.example_id
group by a.old_complete_class_name,a.old_method_name) o 
on t.class_name = o.old_complete_class_name and t.method_name = o.old_method_name
set bug_total= sum, bug_r=r;

SET SQL_SAFE_UPDATES = 0;
update top_apis t inner join 
(SELECT a.new_complete_class_name,a.new_method_name, count(distinct c.repository_id) as r, sum(c.inner_repeat_num) as sum
FROM example_not_bug as a, top_apis as b, inner_example_not_bug as c
WHERE a.new_complete_class_name = b.class_name AND a.new_method_name = b.method_name AND a.example_id = c.example_id
group by a.new_complete_class_name,a.new_method_name) o 
on t.class_name = o.new_complete_class_name and t.method_name = o.new_method_name
set notbug_total= sum, notbug_r=r;

SET SQL_SAFE_UPDATES = 1;