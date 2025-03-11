create table job(
    job_idx bigint primary key auto_increment,
    job_id bigint,
    domain varchar(10),
    image_path varchar(500),
    title varchar(100),
    company_name varchar(50),
    location varchar(50)
);