INSERT INTO `user` (`id`, `email`, `password`, `active`, `first_name`, `last_name`) VALUES
  (1, 'sasha@gmail.com', '$2a$10$Ogw1kABgMFEdtq6GPwE9H.oXbCUGo3NyCjqb2f7.4H7Y9gpC5KyCq', 1, 'Саша','Картошкін'),
  (2, 'masha@gmail.com', '$2a$10$Ogw1kABgMFEdtq6GPwE9H.oXbCUGo3NyCjqb2f7.4H7Y9gpC5KyCq', 1, 'Маша','Машкевіч'),
  (3, 'sasha2@gmail.com', '$2a$10$Ogw1kABgMFEdtq6GPwE9H.oXbCUGo3NyCjqb2f7.4H7Y9gpC5KyCq', 1, 'Саша','Сашкевіч'),
  (4, 'masha2@gmail.com', '$2a$10$Ogw1kABgMFEdtq6GPwE9H.oXbCUGo3NyCjqb2f7.4H7Y9gpC5KyCq', 1, 'Masha','Mashevich');

INSERT  INTO `user_role` (`id`, `role`, `user_id`) VALUES
  (1, 'admin', 1),
  (2, 'user', 1),
  (3, 'user', 2),
  (4, 'user', 3),
  (5, 'user', 4);

INSERT INTO `bill` (`id`, `active`, `deleted`, `name`, `score`, `user_id`) VALUES
  (1, 1, 0, 'bill1', 200, 1),
  (2, 1, 0, 'bill2', 500, 2),
  (3, 1, 0, 'bill3', 700, 3),
  (4, 1, 0, 'bill4', 400, 4),
  (5, 1, 0, 'bill5', 800, 1);

INSERT INTO `card` (`id`, `active`, `deleted`, `name`, `password`, `id_bill`) VALUES
  (1, 1, 0, 'card1', '$2a$10$Ogw1kABgMFEdtq6GPwE9H.oXbCUGo3NyCjqb2f7.4H7Y9gpC5KyCq', 1),
  (2, 1, 0, 'card2', '$2a$10$Ogw1kABgMFEdtq6GPwE9H.oXbCUGo3NyCjqb2f7.4H7Y9gpC5KyCq', 1),
  (3, 1, 0, 'card3', '$2a$10$Ogw1kABgMFEdtq6GPwE9H.oXbCUGo3NyCjqb2f7.4H7Y9gpC5KyCq', 2),
  (4, 1, 0, 'card4', '$2a$10$Ogw1kABgMFEdtq6GPwE9H.oXbCUGo3NyCjqb2f7.4H7Y9gpC5KyCq', 3),
  (5, 1, 0, 'card5', '$2a$10$Ogw1kABgMFEdtq6GPwE9H.oXbCUGo3NyCjqb2f7.4H7Y9gpC5KyCq', 4),
  (6, 1, 0, 'card6', '$2a$10$Ogw1kABgMFEdtq6GPwE9H.oXbCUGo3NyCjqb2f7.4H7Y9gpC5KyCq', 5);