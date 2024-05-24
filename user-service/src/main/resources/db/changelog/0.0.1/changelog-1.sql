--liquibase formatted sql

--changeset Ostap Evdokimov:1
CREATE TABLE Users
(
    user_id           SERIAL PRIMARY KEY,
    username          VARCHAR(255) UNIQUE NOT NULL,
    email             VARCHAR(255) UNIQUE NOT NULL,
    password_hash     VARCHAR(255)        NOT NULL,
    registration_date TIMESTAMP                    DEFAULT CURRENT_TIMESTAMP,
    online_status     BOOLEAN             NOT NULL DEFAULT FALSE,
    is_enabled        BOOLEAN             NOT NULL DEFAULT TRUE
);

--changeset Ostap Evdokimov:2
CREATE TABLE Roles
(
    role_id   SERIAL PRIMARY key,
    role_name VARCHAR(50) NOT null
);

--changeset Ostap Evdokimov:3
CREATE TABLE Authorities
(
    authority_id SERIAL PRIMARY KEY,
    authority    VARCHAR(50) NOT NULL
);

--changeset Ostap Evdokimov:4
CREATE TABLE Role_Authorities
(
    role_id      BIGINT NOT NULL,
    authority_id BIGINT NOT NULL,
    PRIMARY KEY (role_id, authority_id),
    FOREIGN KEY (role_id) REFERENCES Roles (role_id),
    FOREIGN KEY (authority_id) REFERENCES Authorities (authority_id)
);

--changeset Ostap Evdokimov:5
CREATE TABLE User_Roles
(
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES Users (user_id),
    FOREIGN KEY (role_id) REFERENCES Roles (role_id)
);

--changeset Ostap Evdokimov:6
CREATE TABLE Contacts
(
    contact_id      SERIAL PRIMARY KEY,
    user_id         BIGINT NOT NULL,
    contact_user_id BIGINT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES Users (user_id),
    FOREIGN KEY (contact_user_id) REFERENCES Users (user_id)
);

--changeset Ostap Evdokimov:7
CREATE TABLE Chats
(
    chat_id      SERIAL PRIMARY KEY,
    user_id      BIGINT NOT NULL,
    recipient_id BIGINT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES Users (user_id),
    FOREIGN KEY (recipient_id) REFERENCES Users (user_id)
);

--changeset Ostap Evdokimov:8
CREATE TABLE Messages
(
    message_id   SERIAL PRIMARY KEY,
    chat_id      BIGINT  NOT NULL,
    sender_id    BIGINT  NOT NULL,
    recipient_id BIGINT  NOT NULL,
    content      TEXT    NOT NULL,
    timestamp    TIMESTAMP        DEFAULT CURRENT_TIMESTAMP,
    read_status  BOOLEAN NOT NULL DEFAULT FALSE,
    FOREIGN KEY (chat_id) REFERENCES Chats (chat_id),
    FOREIGN KEY (sender_id) REFERENCES Users (user_id),
    FOREIGN KEY (recipient_id) REFERENCES Users (user_id)
);

--changeset Ostap Evdokimov:9
CREATE TABLE Notifications
(
    notification_id SERIAL PRIMARY KEY,
    user_id         BIGINT NOT NULL,
    message_id      BIGINT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES Users (user_id),
    FOREIGN KEY (message_id) REFERENCES Messages (message_id)
);

--changeset Ostap Evdokimov:10
CREATE TABLE Dialogs
(
    dialog_id SERIAL PRIMARY KEY,
    user1_id  BIGINT NOT NULL,
    user2_id  BIGINT NOT NULL,
    FOREIGN KEY (user1_id) REFERENCES Users (user_id),
    FOREIGN KEY (user2_id) REFERENCES Users (user_id)
);

--changeset Ostap Evdokimov:11
CREATE TABLE Posts
(
    post_id   SERIAL PRIMARY KEY,
    user_id   BIGINT NOT NULL,
    content   TEXT   NOT NULL,
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES Users (user_id)
);

--changeset Ostap Evdokimov:12
CREATE TABLE Group_Chats
(
    group_chat_id SERIAL PRIMARY KEY,
    chat_name     VARCHAR(255) NOT NULL,
    timestamp     TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

--changeset Ostap Evdokimov:13
CREATE TABLE Comments
(
    comment_id        SERIAL PRIMARY KEY,
    post_id           BIGINT NOT NULL,
    user_id           BIGINT NOT NULL,
    content           TEXT   NOT NULL,
    timestamp         TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    parent_comment_id BIGINT,
    FOREIGN KEY (post_id) REFERENCES Posts (post_id),
    FOREIGN KEY (user_id) REFERENCES Users (user_id),
    FOREIGN KEY (parent_comment_id) REFERENCES Comments (comment_id)
);
