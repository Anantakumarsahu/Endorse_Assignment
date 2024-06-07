use skill_endorsement;

-- All Select Query of the table that I have created --
CREATE TABLE user (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    role VARCHAR(255) NOT NULL,
    years_of_experience INT NOT NULL
);

CREATE TABLE skill (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE endorsement (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    reviewee_id BIGINT NOT NULL,
    reviewer_id BIGINT NOT NULL,
    skill_id BIGINT NOT NULL,
    score INT NOT NULL,
    adjusted_score DOUBLE,
    reason VARCHAR(255),
    FOREIGN KEY (reviewee_id) REFERENCES user(id),
    FOREIGN KEY (reviewer_id) REFERENCES user(id),
    FOREIGN KEY (skill_id) REFERENCES skill(id)
);

CREATE TABLE coworker_relationship (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user1_id BIGINT NOT NULL,
    user2_id BIGINT NOT NULL,
    is_current_coworker BOOLEAN NOT NULL,
    FOREIGN KEY (user1_id) REFERENCES user(id),
    FOREIGN KEY (user2_id) REFERENCES user(id)
);

CREATE TABLE department (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

ALTER TABLE skill ADD COLUMN department_id BIGINT, ADD CONSTRAINT fk_department FOREIGN KEY (department_id) REFERENCES department(id);


-- All insert query example I have created in my Mysql DB--

-- Insert users
INSERT INTO user (name, role, years_of_experience) VALUES
('John Doe', 'Developer', 10),
('Jane Smith', 'PR Manager', 5),
('Alice Johnson', 'CTO', 15);

-- Insert skills
INSERT INTO skill (id, name, department_id) VALUES (1, 'Java', 1);
INSERT INTO skill (id, name, department_id) VALUES (2, 'Spring Boot', 1);
INSERT INTO skill (id, name, department_id) VALUES (3, 'Sales Strategy', 2);
INSERT INTO skill (`id`, `name`, `department_id`) VALUES ('4', 'REST Api', '1');
INSERT INTO skill (`id`, `name`, `department_id`) VALUES ('5', 'Marketing', '2');

-- Insert coworker relationships
INSERT INTO coworker_relationship (user1_id, user2_id, is_current_coworker) VALUES
(1, 3, TRUE),
(1, 2, FALSE),
(2, 3, TRUE);

-- Insert into department table --
INSERT INTO department (name) VALUES ('Software Development');
INSERT INTO department (name) VALUES ('Sales and Marketing');

-- Insert into user_skill table --
INSERT INTO user_skill (user_id, skill_id) VALUES
(1, 1),
(1, 2),
(2, 3);


