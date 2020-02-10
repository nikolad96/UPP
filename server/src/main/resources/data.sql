insert into role values (1, 'ROLE_USER');
insert into role values (2, 'ROLE_ADMIN');
insert into role values (3, 'ROLE_RECENZENT');
insert into role values (4, 'ROLE_UREDNIK');

insert into user_role values (1,1);
insert into user_role values (2,4);
insert into user_role values (3,3);
insert into user_role values (4,3);

INSERT INTO `db_camunda`.`user` (`id`, `aktiviran`, `drzava`, `email`, `grad`, `ime`, `naucna_oblast`, `password`, `prezime`, `recenzent`, `titula`, `username`) VALUES ('1', true, 'Srbija', 'nikola.djordjevic04@gmail.com', 'Novi Sad', 'Nikola', 'Geografija', '$2a$10$Uy11a9QSNHrB2Z4kC5UdDu3/QmFZu3hB/TWko1mq4.GhafzQ6jWcG', 'djordjevic', false, 'nista', 'nikola321');
INSERT INTO `db_camunda`.`user` (`id`, `aktiviran`, `drzava`, `email`, `grad`, `ime`, `naucna_oblast`, `password`, `prezime`, `recenzent`, `titula`, `username`) VALUES ('2', true, 'Srbija', 'nikola.djordjevic04@gmail.com', 'Novi Sad', 'Urednik', 'Geografija', '$2a$10$Uy11a9QSNHrB2Z4kC5UdDu3/QmFZu3hB/TWko1mq4.GhafzQ6jWcG', 'urednikovic', false, 'nista', 'urednik321');
INSERT INTO `db_camunda`.`user` (`id`, `aktiviran`, `drzava`, `email`, `grad`, `ime`, `naucna_oblast`, `password`, `prezime`, `recenzent`, `titula`, `username`) VALUES ('3', true, 'Srbija', 'nikola.djordjevic04@gmail.com', 'Novi Sad', 'Recenzent', 'Geografija', '$2a$10$Uy11a9QSNHrB2Z4kC5UdDu3/QmFZu3hB/TWko1mq4.GhafzQ6jWcG', 'recenznett', false, 'nista', 'recenzent1');
INSERT INTO `db_camunda`.`user` (`id`, `aktiviran`, `drzava`, `email`, `grad`, `ime`, `naucna_oblast`, `password`, `prezime`, `recenzent`, `titula`, `username`) VALUES ('4', true, 'Srbija', 'nikola.djordjevic04@gmail.com', 'Novi Sad', 'Recenzent2', 'Geografija', '$2a$10$Uy11a9QSNHrB2Z4kC5UdDu3/QmFZu3hB/TWko1mq4.GhafzQ6jWcG', 'recenzentt', false, 'nista', 'recenzent2');

INSERT INTO `db_camunda`.`casopis` (`id`, `naziv`,`oblast`, `open`) VALUES ('1','Nacionalna geografija', 'Geografija',true);
INSERT INTO `db_camunda`.`casopis` (`id`, `naziv`,`oblast`, `open`) VALUES ('2','Casopis 2','Geografija', true);
INSERT INTO `db_camunda`.`casopis` (`id`, `naziv`,`oblast`, `open`) VALUES ('3','Casopis 3','Geografija', false);