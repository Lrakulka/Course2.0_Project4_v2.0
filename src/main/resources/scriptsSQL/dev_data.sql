INSERT INTO `user` (`id`, `email`, `password`, `active`, `first_name`, `last_name`) VALUES
  (1, 'sasha@gmail.com', '$2a$10$9.QmNEcFb/bf2lTthsN.TOHEfJrRRHQQdpHJ9SM9r6RTh7AMO3SRK', 1, 'Саша','Картошкін'),
  (2, 'masha@gmail.com', '$2a$10$9.QmNEcFb/bf2lTthsN.TOHEfJrRRHQQdpHJ9SM9r6RTh7AMO3SRK', 1, 'Маша','Машкевіч'),
  (3, 'sasha2@gmail.com', '$2a$10$9.QmNEcFb/bf2lTthsN.TOHEfJrRRHQQdpHJ9SM9r6RTh7AMO3SRK', 1, 'Саша','Сашкевіч'),
  (4, 'masha2@gmail.com', '$2a$10$9.QmNEcFb/bf2lTthsN.TOHEfJrRRHQQdpHJ9SM9r6RTh7AMO3SRK', 1, 'Masha','Mashevich');

INSERT  INTO `user_role` (`id`, `role`, `user_id`) VALUES
  (1, 'ROLE_ADMIN', 1),
  (2, 'ROLE_USER', 1),
  (3, 'ROLE_USER', 2),
  (4, 'ROLE_USER', 3),
  (5, 'ROLE_USER', 4);

INSERT INTO `bill` (`id`, `active`, `deleted`, `name`, `score`, `user_id`) VALUES
  (1, 1, 0, 'bill1', 200, 1),
  (2, 1, 0, 'bill2', 500, 2),
  (3, 1, 0, 'bill3', 700, 3),
  (4, 1, 0, 'bill4', 400, 4),
  (5, 1, 0, 'bill5', 800, 1);

INSERT INTO `card` (`id`, `active`, `deleted`, `name`, `password`, `id_bill`) VALUES
  (1, 1, 0, 'card1', '$2a$10$9.QmNEcFb/bf2lTthsN.TOHEfJrRRHQQdpHJ9SM9r6RTh7AMO3SRK', 1),
  (2, 1, 0, 'card2', '$2a$10$9.QmNEcFb/bf2lTthsN.TOHEfJrRRHQQdpHJ9SM9r6RTh7AMO3SRK', 1),
  (3, 1, 0, 'card3', '$2a$10$9.QmNEcFb/bf2lTthsN.TOHEfJrRRHQQdpHJ9SM9r6RTh7AMO3SRK', 2),
  (4, 1, 0, 'card4', '$2a$10$9.QmNEcFb/bf2lTthsN.TOHEfJrRRHQQdpHJ9SM9r6RTh7AMO3SRK', 3),
  (5, 1, 0, 'card5', '$2a$10$9.QmNEcFb/bf2lTthsN.TOHEfJrRRHQQdpHJ9SM9r6RTh7AMO3SRK', 4),
  (6, 1, 0, 'card6', '$2a$10$9.QmNEcFb/bf2lTthsN.TOHEfJrRRHQQdpHJ9SM9r6RTh7AMO3SRK', 5);