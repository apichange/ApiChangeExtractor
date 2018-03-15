SET SQL_SAFE_UPDATES = 0;

update top_apis_fix t ,(SELECT a.old_complete_class_name,a.old_method_name, count(distinct a.repository_id) as r, count(*) as sum
FROM apichange_7th as a, top_apis_fix as b
WHERE binary a.new_file_name not like '%Test%'
group by a.old_complete_class_name,a.old_method_name) o
set t.bug_total= sum, t.bug_r=r
where t.class_name = o.old_complete_class_name and t.method_name = o.old_method_name;

SET SQL_SAFE_UPDATES = 1;