CREATE TABLE question (
    questionId UUID NOT NULL PRIMARY KEY,
    contents VARCHAR(100) NOT NULL
);
CREATE TABLE answer (
    contents VARCHAR(100) NOT NULL,
    isCorrect boolean NOT NULL,
    questionId UUID NOT NULL REFERENCES question(questionId)
);
