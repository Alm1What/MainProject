-- Створення таблиці ролей
CREATE TABLE roles
(
    id   BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL UNIQUE
);

-- Створення таблиці користувачів
CREATE TABLE users
(
    id             BIGINT PRIMARY KEY AUTO_INCREMENT,
    email          VARCHAR(255) NOT NULL UNIQUE,
    password       VARCHAR(255) NOT NULL,
    name           VARCHAR(255) NOT NULL,
    deleted        BOOLEAN      NOT NULL DEFAULT FALSE,
    deleted_at     DATETIME,
    restoration_at DATETIME
);

-- Створення проміжної таблиці для зв'язку користувачів та ролей
CREATE TABLE user_roles
(
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    CONSTRAINT fk_user_roles_user FOREIGN KEY (user_id) REFERENCES users (id),
    CONSTRAINT fk_user_roles_role FOREIGN KEY (role_id) REFERENCES roles (id)
);

-- Створення таблиці завдань
CREATE TABLE tasks
(
    id          BIGINT PRIMARY KEY AUTO_INCREMENT,
    title       VARCHAR(255) NOT NULL,
    description VARCHAR(1000),
    completed   BOOLEAN      NOT NULL DEFAULT FALSE,
    user_id     BIGINT       NOT NULL,
    created_at  DATETIME     NOT NULL,
    updated_at  DATETIME,
    CONSTRAINT fk_tasks_user FOREIGN KEY (user_id) REFERENCES users (id)
);