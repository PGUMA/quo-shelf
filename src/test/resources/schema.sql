CREATE TABLE IF NOT EXISTS authors (
    author_id INT NOT NULL AUTO_INCREMENT,
    author_name VARCHAR(100) NOT NULL,
    PRIMARY KEY (author_id)
);

CREATE TABLE IF NOT EXISTS books (
    book_id INT NOT NULL AUTO_INCREMENT,
    book_title VARCHAR(100) NOT NULL,
    author_id INT NOT NULL,
    PRIMARY KEY (book_id),
    constraint book_writing_author FOREIGN KEY(author_id) REFERENCES authors(author_id)
);