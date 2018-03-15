SET SQL_SAFE_UPDATES = 0;

UPDATE top_apis_fix SET bug_rank=(0.8*(bug_r-1)/208)+(0.2*(bug_total/bug_r-1)/19);
UPDATE top_apis_7th SET notbug_rank=(0.8*(notbug_r-1)/294)+(0.2*(notbug_total/notbug_r-1)/64);
UPDATE top_apis_fix SET test_rate=bug_rank/(bug_rank+notbug_rank) where bug_rank+notbug_rank!=0;

SET SQL_SAFE_UPDATES = 1;