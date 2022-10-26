delete
from message;
insert into message(id, tag, text, user_id)
values (1, '1', 'why', 1),
       (2, '2', 'Hello2', 1),
       (3, '3', 'Hello3', 1),
       (4, '4', 'why', 1);

alter sequence hibernate_sequence restart with 10;