DROP TABLE IF EXISTS AUTHOR;
CREATE TABLE AUTHOR
(
    ID   BIGINT AUTO_INCREMENT PRIMARY KEY,
    NAME VARCHAR(255) NOT NULL
);
DROP TABLE IF EXISTS GENRE;
CREATE TABLE GENRE
(
    ID   BIGINT AUTO_INCREMENT PRIMARY KEY,
    NAME VARCHAR(255) NOT NULL
);
DROP TABLE IF EXISTS BOOK;
CREATE TABLE BOOK
(
    ID        BIGINT AUTO_INCREMENT PRIMARY KEY,
    TITLE     VARCHAR(255) NOT NULL,
    AUTHOR_ID BIGINT       NOT NULL REFERENCES AUTHOR(ID),
    GENRE_ID  BIGINT       NOT NULL REFERENCES GENRE(ID)
);
DROP TABLE IF EXISTS COMMENT;
CREATE TABLE COMMENT
(
    ID        BIGINT AUTO_INCREMENT PRIMARY KEY,
    BOOK_ID   BIGINT       NOT NULL REFERENCES BOOK(ID),
    NICK_NAME VARCHAR(255) NOT NULL,
    TEXT      VARCHAR(255) NOT NULL
);
