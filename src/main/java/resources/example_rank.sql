SET SQL_SAFE_UPDATES = 0;

UPDATE example_7th SET bug_rank=(0.8*(bug_r-1)/170)+(0.2*(bug_total/bug_r-1)/22);

SET SQL_SAFE_UPDATES = 1;