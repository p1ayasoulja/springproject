delete
from user_role;
delete
from usr;
insert into usr(id, username,password,active)
values (1,'qwert','$2a$08$SGU0NY6RycH3NsI0wKw5HeFX7LYYfuVSjKWOYj.74NqCa6xyhJ29u', true);

insert into user_role(user_id, roles)
values (1, 'ADMIN'),
       (1, 'USER');