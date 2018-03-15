SET SQL_SAFE_UPDATES = 0;
update example_7th 
set outer_repeat_num = 0;
update example_7th inner join
(SELECT example_id,count(distinct repository_id) as num
FROM apichange_7th 
where binary new_file_name not like '%Test%'
group by example_id) o
on example_7th.example_id = o.example_id
set outer_repeat_num = num;


update inner_example_7th 
set inner_repeat_num = 0;
update inner_example_7th inner join
(SELECT repository_id, example_id, count(*) as num
FROM temp.apichange_7th
where binary new_file_name not like '%Test%'
group by repository_id, example_id) o
on inner_example_7th.repository_id = o.repository_id and inner_example_7th.example_id = o.example_id
set inner_repeat_num = num;
SET SQL_SAFE_UPDATES = 1;