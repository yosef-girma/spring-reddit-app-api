create table if not exists User
(user_id int primary key auto_increment,created timestamp default  current_timestamp,
email text ,enabled boolean,password text,username varchar(25));
INSERT INTO user (`user_id`, `created`,`email`, `enabled`,`password`, `username`)
VALUES (null ,
        null ,
        'test@email.com',
        true,
        's3cr3t',
        'testuser_sql');
