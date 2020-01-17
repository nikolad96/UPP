insert into role values (1, 'ROLE_USER');
insert into role values (2, 'ROLE_ADMIN');
insert into role values (3, 'ROLE_RECENZENT');

insert into user_role values (1,1);

INSERT INTO `db_camunda`.`user` (`id`, `aktiviran`, `drzava`, `email`, `grad`, `ime`, `naucna_oblast`, `password`, `prezime`, `recenzent`, `titula`, `username`) VALUES ('1', true, 'Srbija', 'nikola.djordjevic04@gmail.com', 'Novi Sad', 'Nikola', 'Geografija', '$2a$10$Uy11a9QSNHrB2Z4kC5UdDu3/QmFZu3hB/TWko1mq4.GhafzQ6jWcG', 'djordjevic', false, 'nista', 'nikola321');
