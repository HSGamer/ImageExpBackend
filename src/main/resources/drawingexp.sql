CREATE TABLE `user`
(
    `userid`     INTEGER     NOT NULL PRIMARY KEY,
    `email`      varchar(64) NOT NULL,
    `password`   text        NOT NULL,
    `username`   varchar(64) NOT NULL,
    `created_at` datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `avatar`     text        NOT NULL,
    `status`     varchar(30) NOT NULL
);

INSERT
INTO `user` (`userid`,
             `email`,
             `password`,
             `username`,
             `created_at`,
             `avatar`,
             `status`)
VALUES (1,
        'daylaemail@gmail.com',
        'iHUUuHGdJfYUgIKHo2333jB@HJ32k32j3b',
        'daylauser',
        '2021-06-08 17:09:19',
        '',
        '');

CREATE TABLE `picture`
(
    `picID`    INTEGER     NOT NULL PRIMARY KEY,
    `category` varchar(30) NOT NULL,
    `userID`   INTEGER     NOT NULL,
    `picture`  text        NOT NULL,
    FOREIGN KEY (userID) REFERENCES `user` (userid)
);

INSERT
INTO `picture` (`picID`,
                `category`,
                `userID`,
                `picture`)
VALUES (1,
        'sample',
        1,
        '');

CREATE TABLE `post`
(
    `postID`     INTEGER      NOT NULL PRIMARY KEY,
    `userID`     INTEGER      NOT NULL,
    `picID`      INTEGER      NOT NULL,
    `created_at` datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `keyword`    varchar(128) NOT NULL,
    `status`     varchar(30)  NOT NULL,
    `likes`      INTEGER      NOT NULL,
    FOREIGN KEY (userID) REFERENCES `user` (userid),
    FOREIGN KEY (picID) REFERENCES `picture` (picID)
);

INSERT
INTO `post` (`postID`,
             `userID`,
             `picID`,
             `created_at`,
             `updated_at`,
             `keyword`,
             `status`,
             `likes`)
VALUES (1,
        1,
        1,
        '2021-06-08 17:08:16',
        '2021-06-08 17:08:54',
        '#this#is#key#word',
        'banned',
        1234);
