CREATE DATABASE IF NOT EXISTS game_project;

USE game_project;

CREATE TABLE IF NOT EXISTS players (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    wins INT DEFAULT 0,
    losses INT DEFAULT 0,
    draws INT DEFAULT 0,
    score INT DEFAULT 0
);

INSERT INTO
    players (
        username,
        password,
        wins,
        losses,
        draws,
        score
    )
VALUES ('des1', '12345', 0, 0, 0, 0);

INSERT INTO
    players (
        username,
        password,
        wins,
        losses,
        draws,
        score
    )
VALUES ('des2', '12345', 0, 0, 0, 0);

INSERT INTO
    players (
        username,
        password,
        wins,
        losses,
        draws,
        score
    )
VALUES ('des3', '12345', 0, 0, 0, 0);

INSERT INTO
    players (
        username,
        password,
        wins,
        losses,
        draws,
        score
    )
VALUES ('des4', '12345', 0, 0, 0, 0);

INSERT INTO
    players (
        username,
        password,
        wins,
        losses,
        draws,
        score
    )
VALUES ('des5', '12345', 0, 0, 0, 0);