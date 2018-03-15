SET SQL_SAFE_UPDATES = 0;

update example_7th a,(SELECT example_id,count(*) as total,count(distinct repository_id) as r FROM apichange_7th where binary new_file_name not like '%Test%' group by example_id) b
set bug_total=total,bug_r=r
where a.example_id = b.example_id;

UPDATE example_7th SET bug_rank=(0.8*(bug_r-min(bug_r))/(max(bug_r)-min(bug_r)))+(0.2*(bug_total/bug_r-min(bug_total/bug_r))/(max(bug_total/bug_r)-min(bug_total/bug_r)));

SET SQL_SAFE_UPDATES = 1;