INSERT INTO region_list(name) VALUES
('Арбажский'),
('Белохолуницкий'),
('Верхошижемский'),
('Даровской'),
('Зуевский');

INSERT INTO user_(email,password,region_id,surname,firstname,patronymic,is_admin,is_active) VALUES 
('ivanov@gmail.com','$2a$12$lDEBUDH3Jks4egjnjMVl8Oqf8frMPkkaBnMt6S4KbzTCNEPGNwmoC','1','Иванов','Иван','Иванович',true,true),
('alex@gmail.com','$2a$12$lDEBUDH3Jks4egjnjMVl8Oqf8frMPkkaBnMt6S4KbzTCNEPGNwmoC','2','Александров','Александр','Александрович',false,true),
('test@gmail.com','$2a$12$lDEBUDH3Jks4egjnjMVl8Oqf8frMPkkaBnMt6S4KbzTCNEPGNwmoC','3','TEST','TEST','TEST',false,true),
('user@gmail.com','$2a$12$lDEBUDH3Jks4egjnjMVl8Oqf8frMPkkaBnMt6S4KbzTCNEPGNwmoC','4','user','user','user',false,true);