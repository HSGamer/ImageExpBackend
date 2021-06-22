CREATE TABLE `category`
(
    `categoryID`    INTEGER     NOT NULL PRIMARY KEY,
    `category_name` varchar(32) NOT NULL
);

CREATE TABLE `user`
(
    `userid`     INTEGER     NOT NULL PRIMARY KEY,
    `email`      varchar(64) NOT NULL,
    `username`   varchar(64) NOT NULL,
    `password`   text        NOT NULL,
    `created_at` datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `avatar`     text        NOT NULL,
    `status`     varchar(30) NOT NULL
);

CREATE TABLE `picture`
(
    `picID`   INTEGER NOT NULL PRIMARY KEY,
    `userID`  INTEGER NOT NULL,
    `picture` text    NOT NULL,
    FOREIGN KEY (`userID`) REFERENCES `user` (`userid`)
);

CREATE TABLE `post`
(
    `postID`      INTEGER      NOT NULL PRIMARY KEY,
    `userID`      INTEGER      NOT NULL,
    `picID`       INTEGER      NOT NULL,
    `title`       varchar(32)  NOT NULL,
    `description` text         NOT NULL,
    `created_at`  datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at`  datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `keyword`     varchar(128) NOT NULL,
    `status`      varchar(32)  NOT NULL,
    FOREIGN KEY (`picID`) REFERENCES `picture` (`picID`) ON DELETE CASCADE,
    FOREIGN KEY (`userID`) REFERENCES `user` (`userid`) ON DELETE CASCADE
);

CREATE TABLE `postcategory`
(
    `postcatID`  INTEGER NOT NULL PRIMARY KEY,
    `postID`     INTEGER NOT NULL,
    `categoryID` INTEGER NOT NULL,
    FOREIGN KEY (`categoryID`) REFERENCES `category` (`categoryID`) ON DELETE CASCADE,
    FOREIGN KEY (`postID`) REFERENCES `post` (`postID`) ON DELETE CASCADE
);

CREATE TABLE `comment`
(
    `commentID`  INTEGER  NOT NULL PRIMARY KEY,
    `postID`     INTEGER  NOT NULL,
    `userID`     INTEGER  NOT NULL,
    `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `comment`    text     NOT NULL,
    FOREIGN KEY (`postID`) REFERENCES `post` (`postID`) ON DELETE CASCADE,
    FOREIGN KEY (`userID`) REFERENCES `user` (`userid`) ON DELETE CASCADE
);

CREATE TABLE `likes`
(
    `likesID` INTEGER NOT NULL PRIMARY KEY,
    `postID`  INTEGER NOT NULL,
    `userID`  INTEGER NOT NULL,
    FOREIGN KEY (`postID`) REFERENCES `post` (`postID`) ON DELETE CASCADE,
    FOREIGN KEY (`userID`) REFERENCES `user` (`userid`) ON DELETE CASCADE
);

INSERT INTO `category` (`categoryID`, `category_name`)
VALUES (1, 'action'),
       (2, 'adventure'),
       (3, 'comedy'),
       (4, 'demons'),
       (5, 'drama'),
       (6, 'slice of life'),
       (7, 'fantasy'),
       (8, 'game'),
       (9, 'historical'),
       (10, 'isekai'),
       (11, 'magic'),
       (12, 'military'),
       (13, 'mecha'),
       (14, 'music'),
       (15, 'school'),
       (16, 'shoujo'),
       (17, 'shounen'),
       (18, 'super power'),
       (19, 'sports'),
       (20, 'space'),
       (21, 'tragedy'),
       (22, 'supernatural'),
       (23, 'horror'),
       (24, 'mystery'),
       (25, 'psychological'),
       (26, 'romance'),
       (27, 'sci-fi');