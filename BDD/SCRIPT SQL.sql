DROP TABLE if exists Ligue;
DROP TABLE if exists Employe;

CREATE TABLE Ligue(
    ID_LIGUE INT,
    NOM_L VARCHAR(),
    PRIMARY KEY(ID_LIGUE)
)engine = innodb;

CREATE TABLE Employe(
    ID_EMP INT,
    NOM_EMP VARCHAR(),
    PRENOM_EMP VARCHAR(),
    MDP_EMP VARCHAR(),
    DATE_ARRIVE DATE(),
    DATE_DEPART DATE(),  
    ADMIN BOOLEAN,
    MAIL_EMP VARCHAR(),
    PRIMARY KEY(ID_EMP),
    FOREIGN KEY(ID_LIGUE) references LIGUE(ID_LIGUE)
)engine = innodb;