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
    `title`       varchar(128) NOT NULL,
    `description` varchar(128) NOT NULL,
    `created_at`  datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at`  datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `keyword`     varchar(128) NOT NULL,
    `status`      varchar(32)  NOT NULL,
    FOREIGN KEY (`picID`) REFERENCES `picture` (`picID`),
    FOREIGN KEY (`userID`) REFERENCES `user` (`userid`)
);

CREATE TABLE `postcategory`
(
    `postcatID`  INTEGER NOT NULL PRIMARY KEY,
    `postID`     INTEGER NOT NULL,
    `categoryID` INTEGER NOT NULL,
    FOREIGN KEY (`categoryID`) REFERENCES `category` (`categoryID`),
    FOREIGN KEY (`postID`) REFERENCES `post` (`postID`)
);

CREATE TABLE `comment`
(
    `commentID`  INTEGER  NOT NULL PRIMARY KEY,
    `postID`     INTEGER  NOT NULL,
    `userID`     INTEGER  NOT NULL,
    `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `comment`    text     NOT NULL,
    FOREIGN KEY (`postID`) REFERENCES `post` (`postID`),
    FOREIGN KEY (`userID`) REFERENCES `user` (`userid`)
);

CREATE TABLE `likes`
(
    `likesID` INTEGER NOT NULL PRIMARY KEY,
    `postID`  INTEGER NOT NULL,
    `userID`  INTEGER NOT NULL,
    FOREIGN KEY (`postID`) REFERENCES `post` (`postID`),
    FOREIGN KEY (`userID`) REFERENCES `user` (`userid`)
);
