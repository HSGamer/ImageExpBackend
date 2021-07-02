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
    `status`     varchar(30) NOT NULL,
    `verified`   tinyint(1) NOT NULL DEFAULT FALSE
);

CREATE TABLE `picture`
(
    `picID`   INTEGER NOT NULL PRIMARY KEY,
    `userID`  INTEGER NOT NULL,
    `picture` text    NOT NULL,
    FOREIGN KEY (`userID`) REFERENCES `user` (`userid`) ON DELETE CASCADE
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

CREATE TABLE `code`
(
    `userID` INTEGER    NOT NULL PRIMARY KEY,
    `code`   varchar(6) NOT NULL,
    FOREIGN KEY (`userID`) REFERENCES `user` (`userid`) ON DELETE CASCADE
);

INSERT INTO `category` (`categoryID`, `category_name`)
VALUES (1, '3D'),
       (2, 'Dark'),
       (3, 'Fantasy'),
       (4, 'Fractal'),
       (5, 'Horror'),
       (6, 'Humor'),
       (7, 'Sci-fi'),
       (8, 'Texts'),
       (9, 'Texture'),
       (10, 'Vector'),
       (11, 'Baby Animals'),
       (12, 'Birds'),
       (13, 'Cats'),
       (14, 'Dogs'),
       (15, 'Fishes'),
       (16, 'Insects'),
       (17, 'Reptiles'),
       (18, 'Alcohol'),
       (19, 'Coffee'),
       (20, 'Tea'),
       (21, 'Water'),
       (22, 'Anime'),
       (23, 'Cartoons'),
       (24, 'Classic Games'),
       (25, 'Comics'),
       (26, 'Movies'),
       (27, 'Musics'),
       (28, 'TV Series'),
       (29, 'Video Games'),
       (30, 'Dairies'),
       (31, 'Meats'),
       (32, 'Nuts'),
       (33, 'Pasta'),
       (34, 'Pastries'),
       (35, 'Vegetables'),
       (36, 'Birthday'),
       (37, 'Christmas'),
       (38, 'Easter'),
       (39, 'Halloween'),
       (40, 'New Year'),
       (41, 'Valentine''s Day'),
       (42, 'Beaches'),
       (43, 'Deserts'),
       (44, 'Drops'),
       (45, 'Flowers'),
       (46, 'Fruits'),
       (47, 'Lakes'),
       (48, 'Landscapes'),
       (49, 'Leaves'),
       (50, 'Mountains'),
       (51, 'Plants'),
       (52, 'Rivers'),
       (53, 'Sea / Ocean'),
       (54, 'Sky'),
       (55, 'Space'),
       (56, 'Sunrise / Sunset'),
       (57, 'Trees'),
       (58, 'Waterfalls'),
       (59, 'Children'),
       (60, 'Male'),
       (61, 'Female'),
       (62, 'Male Celebrities'),
       (63, 'Female Celebrities'),
       (64, 'Baseball'),
       (65, 'Basketball'),
       (66, 'Chess'),
       (67, 'Football'),
       (68, 'Soccer'),
       (69, 'Tennis'),
       (70, 'Volleyball'),
       (71, 'Automations'),
       (72, 'Computers'),
       (73, 'Robots'),
       (74, 'Aircrafts'),
       (75, 'Bicycles'),
       (76, 'Cars'),
       (77, 'Concepts'),
       (78, 'Motorcycles'),
       (79, 'Trains'),
       (80, 'Trucks'),
       (81, 'Watercrafts'),
       (82, 'Architecture'),
       (83, 'Cities'),
       (84, 'Flags'),
       (85, 'Roads'),
       (86, 'Other Abstracts'),
       (87, 'Other Animals'),
       (88, 'Other Drinks'),
       (89, 'Other Entertainments'),
       (90, 'Other Foods'),
       (91, 'Other Holidays'),
       (92, 'Other Nature'),
       (93, 'Other People'),
       (94, 'Other Sports'),
       (95, 'Other Technologies'),
       (96, 'Other Vehicles'),
       (97, 'Other Worlds');