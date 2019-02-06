ALTER SESSION SET NLS_DATE_FORMAT = 'YYYY-MM-DD HH24:MI:SS';

DROP TABLE InsurancePayment;
DROP TABLE DebitCreditPayment;
DROP TABLE CheckPayment;
DROP TABLE CashPayment;
DROP TABLE PaymentForService;
DROP TABLE ServiceStaffModifiesRecord;
DROP TABLE MedicalRecord;
DROP TABLE StaffProvideService;
DROP TABLE HospitalStaff;
DROP TABLE ServiceBooking;
DROP TABLE Patient;


CREATE TABLE Patient(
    PatientIDNumber CHAR(9) PRIMARY KEY,
    PatientName VARCHAR(40) NOT NULL,
    DateOfBirth DATE,
    BloodType VARCHAR(3),
    Sex CHAR(1),
    Address VARCHAR(40),
    PhoneNumber CHAR(12),
    PatientPassword VARCHAR(15) NOT NULL,
    CHECK (LENGTH(PatientPassword) > 4),
    CHECK (LENGTH(PhoneNumber) = 12)
);

CREATE TABLE ServiceBooking(
    RoomNumber CHAR(5),
    DateOfIntake DATE,
    PatientIDNumber CHAR(9) NOT NULL,
    DateofDeparture DATE,
    ReasonForVisit VARCHAR(40),
    Cost NUMBER,
    PRIMARY KEY (RoomNumber, DateOfIntake),
    CONSTRAINT PatientService UNIQUE (PatientIDNumber, DateOfIntake),
    FOREIGN KEY (PatientIDNumber) REFERENCES Patient
);

CREATE TABLE HospitalStaff(
    StaffIDNumber CHAR(9) PRIMARY KEY,
    StaffName VARCHAR(40) NOT NULL,
    EmploymentPosition VARCHAR(20) NOT NULL,
    StaffPassword VARCHAR(15) NOT NULL,
    CHECK (LENGTH(StaffPassword) > 4)
);

CREATE TABLE StaffProvideService(
    StaffIDNumber CHAR(9),
    RoomNumber CHAR(5) NOT NULL,
    DateofIntake DATE,
    PRIMARY KEY (StaffIDNumber, DateofIntake),
    FOREIGN KEY (RoomNumber, DateofIntake) REFERENCES ServiceBooking(RoomNumber, DateofIntake)
        ON DELETE CASCADE
);

CREATE TABLE MedicalRecord(
    PatientIDNumber CHAR(9),
    RecordNumber INT,
    Category VARCHAR(20) NOT NULL,
    Description VARCHAR(80),
    DateOfRecord DATE NOT NULL,
    PRIMARY KEY (PatientIDNumber, RecordNumber),
    FOREIGN KEY (PatientIDNumber) REFERENCES Patient(PatientIDNumber)
);

CREATE TABLE ServiceStaffModifiesRecord(
    PatientIDNumber CHAR(9),
    RecordNumber INT,
    StaffIDNumber CHAR(9) NOT NULL,
    RoomNumber CHAR(5),
    DateOfIntake DATE,
    PRIMARY KEY (PatientIDNumber, RecordNumber),
    FOREIGN KEY (PatientIDNumber) REFERENCES Patient(PatientIDNumber),
    FOREIGN KEY (PatientIDNumber, RecordNumber) REFERENCES MedicalRecord(PatientIDNumber, RecordNumber)
        ON DELETE CASCADE,
    FOREIGN KEY (StaffIDNumber) REFERENCES HospitalStaff(StaffIDNumber),
    FOREIGN KEY (RoomNumber, DateOfIntake) REFERENCES ServiceBooking(RoomNumber, DateofIntake)
        ON DELETE SET NULL
);

CREATE TABLE PaymentForService(
    ReceiptNumber INT PRIMARY KEY,
    PaymentAmount NUMBER NOT NULL,
    PaymentDate DATE NOT NULL,
    PatientIDNumber CHAR(9) NOT NULL,
    RoomNumber CHAR(5) NOT NULL,
    DateOfIntake DATE NOT NULL,
    FOREIGN KEY (PatientIDNumber) REFERENCES Patient(PatientIDNumber),
    FOREIGN KEY (RoomNumber, DateOfIntake) REFERENCES ServiceBooking(RoomNumber, DateOfIntake)
        ON DELETE CASCADE
);

CREATE TABLE CashPayment(
    ReceiptNumber INT PRIMARY KEY,
    CashPayerName VARCHAR(40) NOT NULL,
    FOREIGN KEY (ReceiptNumber) REFERENCES PaymentForService(ReceiptNumber)
        ON DELETE CASCADE
);

CREATE TABLE CheckPayment(
    ReceiptNumber INT PRIMARY KEY,
    CheckPayerName VARCHAR(40) NOT NULL,
    RoutingNumber VARCHAR(19) NOT NULL,
    FOREIGN KEY (ReceiptNumber) REFERENCES PaymentForService(ReceiptNumber)
        ON DELETE CASCADE
);

CREATE TABLE DebitCreditPayment(
    ReceiptNumber INT PRIMARY KEY,
    CardholderName VARCHAR(40) NOT NULL,
    CardNumber CHAR(16) NOT NULL,
    ExpiryDate DATE NOT NULL,
    SecurityCode INT NOT NULL,
    FOREIGN KEY (ReceiptNumber) REFERENCES PaymentForService(ReceiptNumber)
        ON DELETE CASCADE
);

CREATE TABLE InsurancePayment(
    ReceiptNumber INT PRIMARY KEY,
    InsuranceProvider VARCHAR(40) NOT NULL,
    InsuranceIDNumber VARCHAR(20),
    FOREIGN KEY (ReceiptNumber) REFERENCES PaymentForService(ReceiptNumber)
        ON DELETE CASCADE
);


insert into Patient
values('P00000001', 'Daniel Smith', '1967-06-04', 'O-','M', '2703 Carling Avenue K1Z 7B5', '778-455-5874', 'password');
insert into Patient
values('P00000002', 'John Chan', '1980-08-22', 'AB+', 'M', '2123 49th Avenue X0E 0H0', '778-745-6429', 'tYWdQXio');
insert into Patient
values('P00000003', 'Samantha Ross', '2018-01-01', 'A-','F', '2557 Algonquin Blvd P4N 7C4', '778-752-7569', 'jUnrpsIiX');
insert into Patient
values('P00000004', 'Ashton Peters', '1992-04-16', 'B+','M', '2280 Danforth Avenue M4K 1A6', '778-726-7245', 'BDaLmeDCzZ');
insert into Patient
values('P00000005', 'Lyn Schneider', '1984-12-29', 'A+', 'F', '4573 De L H4N 3C5', '778-328-9512', '0A4X0gnrRz');
insert into Patient
values('P00000006', 'Michael Waters', '1976-07-03', 'O+', 'M', '1181  Halsey Avenue M3B 2W6', '778-536-8365', 'i94MV4ZW9');
insert into Patient
values('P00000007', 'Meghan Ruano', '1954-12-08', 'A-', 'F', '3610 Russell Avenue V4B 3E1', '778-541-1957', 'oBZJ0ZAb0C');
insert into Patient
values('P00000008', 'Brenda Rau', '1980-08-10', 'AB+','F', '2741 Scotts Lane V0R 2T0', '778-111-1111', 'kVR9GHf8X5J');
insert into Patient
values('P00000009', 'Stephen Welles', '1993-02-05', 'AB+', 'M', '317 9th Avenue N4S 6J6', '778-524-8560', 'G86OeKgNYk');
insert into Patient
values('P00000010', 'Patty Becket', '1976-03-01', 'A+', 'F', '1285 Richford Road J0J 2A0', '778-762-7878', 'DD4lJsR5gK');
insert into Patient
values('P00000011', 'Darin Sales', '1971-01-07', 'B-', 'M', '3121 Bay Street M5J 2R8', '778-562-4534', 'eMQq6JErEj');
insert into Patient
values('P00000012', 'Eric Pemberton', '1972-08-09', 'A+','M', '2325 Bay Street M5J 2R8', '778-565-3232', 'tRtqNIaTcP');
insert into Patient
values('P00000013', 'Robin Dewald', '1961-05-09', 'A-', 'F', '2298 Danforth Avenue M4K 1A6', '778-432-6767', 'nAZ5So8IG1M');
insert into Patient
values('P00000014', 'Francis Duck', '1989-07-06', 'O-', 'M', '2470 Carling Avenue K1Z 7B5', '778-627-6222', 'Ndk8fclhKR');
insert into Patient
values('P00000015', 'Lizzie Nguyen', '1994-04-04', 'AB-', 'F', '3140 Terry Fox Dr S4P 3Y2', '778-563-8973', 'NAduhHW495O');
insert into Patient
values('P00000016', 'Morgan Cochran', '1996-04-03', 'AB+', 'F', '295 Rustico Road C1E 0S6', '778-543-8374', 'sBel4WqGHY');
insert into Patient
values('P00000017', 'Joseph Lee', '1984-09-22', 'B+', 'M', '2994 40th Street T2P 4L4', '778-456-0909', '65cw2ibPOQ');
insert into Patient
values('P00000018', 'Frank Espy', '1997-09-07', 'B-', 'M', '621 White Point Road B0W 2Z0', '778-989-9890', 'RjHpdisAwho');
insert into Patient
values('P00000019', 'Kim Lee', '1966-10-06', 'AB+', 'F', '4102 Dry Pine Bay Rd P0M 1B0', '778-898-0000', 'mQjFNUg5v');
insert into Patient
values('P00000020', 'William Web', '1971-04-26','AB-', 'M', '123 Montreal Road K1L 6C7', '778-676-1234', 'pvZR7gfaIss');


insert into ServiceBooking
values('G-302', '2018-02-01 12:30', 'P00000001', '2018-02-01 13:00', 'Appointment', 70);
insert into ServiceBooking 
values('A-101', '2018-12-01 23:00', 'P00000002', '2018-12-06 15:15', 'ER', 5000);
insert into ServiceBooking
values('A-107', '2018-01-03 10:00', 'P00000003', '2018-01-06 14:00', 'Surgery', 7000);
insert into ServiceBooking
values('A-001', '2018-05-25 12:30', 'P00000004', '2018-05-25 13:00', 'Appointment', 70);
insert into ServiceBooking
values('F-023', '2017-09-16 09:00', 'P00000005', '2017-09-16 10:00', 'Maternity', 120);
insert into ServiceBooking
values('A-101', '2017-06-02 10:00', 'P00000017', '2017-06-02 12:00', 'ER', 200);
insert into ServiceBooking
values('F-023', '2017-12-12 04:00', 'P00000005', '2017-12-12 16:00', 'Maternity', 300);
insert into ServiceBooking
values('G-333', '2018-01-01 09:00', 'P00000005', '2018-01-01 10:00', 'Appointment', 100);
insert into ServiceBooking
values('A-101', '2018-03-02 08:00', 'P00000005', '2018-03-02 15:00', 'ER', 150);
insert into ServiceBooking
values('A-107', '2018-03-02 15:00', 'P00000005', '2018-03-02 18:00', 'Surgery', 2000);
insert into ServiceBooking
values('A-108', '2018-06-06 12:00', 'P00000015', '2018-06-06 15:45', 'Surgery', 2150);
insert into ServiceBooking
values('A-002', '2017-07-07 14:30', 'P00000014', '2017-07-07 15:00', 'Appointment', 100);
insert into ServiceBooking
values('B-102', '2016-10-16 16:00', 'P00000020', '2016-10-16 17:00', 'ER', 100);
insert into ServiceBooking
values('F-023', '2017-04-07 07:00', 'P00000016', '2017-04-08 14:00', 'Maternity', 300);
insert into ServiceBooking
values('A-108', '2017-02-08 09:00', 'P00000008', '2017-02-08 13:00', 'Surgery', 1500);
insert into ServiceBooking
values('G-333', '2017-11-22 14:00', 'P00000009', '2017-11-22 14:30', 'Appointment', 100);
insert into ServiceBooking
values('A-001', '2018-02-18 12:00', 'P00000006', '2016-02-18 12:30', 'Appointment', 100);
insert into ServiceBooking
values('A-001', '2018-04-10 12:00', 'P00000007', '2018-04-10 12:30', 'Appointment', 100);
insert into ServiceBooking
values('A-107', '2016-11-20 01:00', 'P00000010', '2016-11-21 12:00', 'Surgery', 3000);
insert into ServiceBooking
values('A-101', '2017-07-12 04:00', 'P00000011', '2017-07-12 06:00', 'ER', 100);
insert into ServiceBooking
values('A-002', '2018-07-12 10:30', 'P00000012', '2018-07-12 11:00', 'Appointment', 120);
insert into ServiceBooking
values('F-023', '2017-06-04 14:00', 'P00000013', '2017-06-04 15:00', 'Maternity', 150);
insert into ServiceBooking 
values('F-023', '2018-04-18 15:00', 'P00000018', '2018-04-18 15:30', 'Appointment', 110);
insert into ServiceBooking
values('A-002', '2017-11-11 16:30', 'P00000019', '2017-11-11 17:00', 'Appointment', 100);
insert into ServiceBooking
values('A-002', '2018-10-07 18:00', 'P00000020', '2017-10-22 17:00', 'Surgery', 50000);


insert into HospitalStaff
values('S00000001', 'Donna Keller', 'Nurse', 'password');
insert into HospitalStaff
values('S00000002', 'Veronica Whitt', 'Physician', '4bFmQjeHM6');
insert into HospitalStaff
values('S00000003', 'Stephon Avery', 'Surgeon', '9tsgUmtPI');
insert into HospitalStaff
values('S00000004','John Cardos','Receptionist', 'password');
insert into HospitalStaff
values('S00000005', 'Arthur Smith', 'Physician', 'bmjjMDzxIl');
insert into HospitalStaff
values('S00000006', 'Jean Patel','Surgeon','QnmXVuGXlOR');
insert into HospitalStaff
values('S00000007', 'Seiji Ito', 'Nurse', 'BpGw4rIzaU');
insert into HospitalStaff
values('S00000008', 'Corey McIntire', 'Receptionist', 'sMcyGtaD6y');
insert into HospitalStaff
values('S00000009', 'Justin Sims', 'Physician','veGE67FECUa');


insert into StaffProvideService
values('S00000002', 'G-302', '2018-02-01 12:30');
insert into StaffProvideService
values('S00000001', 'A-101', '2018-12-01 23:00');
insert into StaffProvideService
values('S00000003', 'A-107', '2018-01-03 10:00');
insert into StaffProvideService
values('S00000005', 'A-001', '2018-05-25 12:30');
insert into StaffProvideService
values('S00000005', 'F-023', '2017-09-16 09:00');
insert into StaffProvideService
values('S00000009', 'A-101', '2017-06-02 10:00');
insert into StaffProvideService
values('S00000002', 'F-023', '2017-12-12 04:00');
insert into StaffProvideService
values('S00000007', 'G-333', '2018-01-01 09:00');
insert into StaffProvideService
values('S00000005', 'A-101', '2018-03-02 08:00');
insert into StaffProvideService
values('S00000006', 'A-107', '2018-03-02 15:00');
insert into StaffProvideService
values('S00000003', 'A-108', '2018-06-06 12:00');
insert into StaffProvideService
values('S00000009', 'A-002', '2017-07-07 14:30');
insert into StaffProvideService
values('S00000007', 'B-102', '2016-10-16 16:00');
insert into StaffProvideService
values('S00000002', 'F-023', '2017-04-07 07:00');
insert into StaffProvideService
values('S00000006', 'A-108', '2017-02-08 09:00');
insert into StaffProvideService
values('S00000002', 'G-333', '2017-11-22 14:00');
insert into StaffProvideService
values('S00000005', 'A-001', '2018-02-18 12:00');
insert into StaffProvideService
values('S00000002', 'A-001', '2018-04-10 12:00');
insert into StaffProvideService
values('S00000006', 'A-107', '2016-11-20 01:00');
insert into StaffProvideService
values('S00000005', 'A-101', '2017-07-12 04:00');
insert into StaffProvideService
values('S00000001', 'A-002', '2018-07-12 10:30');
insert into StaffProvideService
values('S00000009', 'F-023', '2017-06-04 14:00');
insert into StaffProvideService
values('S00000005', 'F-023', '2018-04-18 15:00');
insert into StaffProvideService
values('S00000002', 'A-002', '2017-11-11 16:30');
insert into StaffProvideService
values('S00000003', 'A-002', '2018-10-07 18:00');


insert into MedicalRecord 
values ('P00000001', 1, 'Allergy', 'Allergic to apples', '2018-02-01');
insert into MedicalRecord 
values ('P00000002', 2, 'Broken bone', 'Broken Tibia in three parts','2018-12-01');
insert into MedicalRecord
values ('P00000003', 3, 'Heart Condition', 'Artery damage in the aorta', '2018-01-03');
insert into MedicalRecord
values ('P00000004', 4, 'Disease', 'Lupus','2018-05-25');
insert into MedicalRecord
values ('P00000005', 5, 'Pregnancy', 'Four-month pregnancy', '2017-09-16');
insert into MedicalRecord
values ('P00000006', 6, 'Virus Infection','Streptococcus', '2018-02-18');
insert into MedicalRecord
values ('P00000007', 7, 'Disease', 'Diabetes Mellitus', '2018-04-10');
insert into MedicalRecord
values ('P00000008', 8, 'Disease', 'Cancer','2017-02-08');
insert into MedicalRecord
values ('P00000009', 9,'Disease', 'Gastritis', '2017-11-22');
insert into MedicalRecord
values ('P00000010', 10, 'Broken Bone', 'Fragmented femur', '2016-11-20');
insert into MedicalRecord
values ('P00000011', 11, 'Chest Pain', 'Panic Attack', '2017-07-12');
insert into MedicalRecord
values ('P00000012', 12, 'Disease', 'Asthma', '2018-07-12');
insert into MedicalRecord
values ('P00000013', 13, 'Pregnancy', 'Two-month pregnancy','2017-06-04');
insert into MedicalRecord
values ('P00000014', 14, 'Allergy', 'Seafood allergy', '2017-07-07');
insert into MedicalRecord
values ('P00000015', 15, 'Condition', 'Third-degree burn','2018-06-06');
insert into MedicalRecord
values ('P00000016', 16, 'Birth', 'Gave birth to a boy', '2017-04-07');
insert into MedicalRecord
values ('P00000017', 17, 'Heart Codition', 'Heart Arrhythmia','2017-06-02');
insert into MedicalRecord
values ('P00000018', 18, 'Disease', 'Cystic Fibrosis','2018-04-18');
insert into MedicalRecord
values ('P00000019', 19, 'Allergy', 'Allergic to Soy', '2017-11-11');
insert into MedicalRecord
values ('P00000020', 20, 'Heart Codition', 'Angina Pectoris', '2016-10-16');


insert into ServiceStaffModifiesRecord
values ('P00000001', 1, 'S00000002', 'G-302', '2018-02-01 12:30');
insert into ServiceStaffModifiesRecord
values ('P00000002', 2, 'S00000001', 'A-101', '2018-12-01 23:00');
insert into ServiceStaffModifiesRecord
values ('P00000003', 3, 'S00000003', 'A-107', '2018-01-03 10:00');
insert into ServiceStaffModifiesRecord
values ('P00000004', 4, 'S00000005', 'A-001', '2018-05-25 12:30');
insert into ServiceStaffModifiesRecord
values ('P00000005', 5, 'S00000005', 'F-023', '2017-09-16 09:00');
insert into ServiceStaffModifiesRecord
values ('P00000006', 6, 'S00000005', 'A-001', '2018-02-18 12:00');
insert into ServiceStaffModifiesRecord
values ('P00000007', 7, 'S00000002', 'A-001', '2018-04-10 12:00');
insert into ServiceStaffModifiesRecord
values ('P00000008', 8, 'S00000006', 'A-108', '2017-02-08 09:00');
insert into ServiceStaffModifiesRecord
values ('P00000009', 9, 'S00000002', 'G-333', '2017-11-22 14:00');
insert into ServiceStaffModifiesRecord
values ('P00000010', 10, 'S00000006', 'A-107', '2016-11-20 01:00');
insert into ServiceStaffModifiesRecord
values ('P00000011', 11, 'S00000005', 'A-101', '2017-07-12 04:00');
insert into ServiceStaffModifiesRecord
values ('P00000012', 12, 'S00000001', 'A-002', '2018-07-12 10:30');
insert into ServiceStaffModifiesRecord
values ('P00000013', 13, 'S00000009', 'F-023', '2017-06-04 14:00');
insert into ServiceStaffModifiesRecord
values ('P00000014', 14, 'S00000009', 'A-002', '2017-07-07 14:30');
insert into ServiceStaffModifiesRecord
values ('P00000015', 15, 'S00000003', 'A-108', '2018-06-06 12:00');
insert into ServiceStaffModifiesRecord
values ('P00000016', 16, 'S00000002', 'F-023', '2017-04-07 07:00');
insert into ServiceStaffModifiesRecord
values ('P00000017', 17, 'S00000009', 'A-101', '2017-06-02 10:00');
insert into ServiceStaffModifiesRecord
values ('P00000018', 18, 'S00000005', 'F-023', '2018-04-18 15:00');
insert into ServiceStaffModifiesRecord
values ('P00000019', 19, 'S00000002', 'A-002', '2017-11-11 16:30');
insert into ServiceStaffModifiesRecord
values ('P00000020', 20, 'S00000007', 'B-102', '2016-10-16 16:00');


insert into PaymentforService
values(1, 70, '2018-02-01','P00000001', 'G-302', '2018-02-01 12:30');
insert into PaymentforService
values(2, 5000, '2018-12-02', 'P00000002', 'A-101', '2018-12-01 23:00');
insert into PaymentforService
values(3, 7000, '2018-12-05', 'P00000003', 'A-107', '2018-01-03 10:00');
insert into PaymentforService
values(4, 70, '2018-05-26', 'P00000004', 'A-001', '2018-05-25 12:30');
insert into PaymentforService
values(5, 120, '2017-09-16','P00000005', 'F-023', '2017-09-16 09:00');
insert into PaymentforService
values(6, 200,'2017-06-02','P00000017', 'A-101', '2017-06-02 10:00');
insert into PaymentforService
values(7, 300,'2017-12-13','P00000005', 'F-023', '2017-12-12 04:00');
insert into PaymentforService
values(8, 100, '2018-01-02', 'P00000005', 'G-333', '2018-01-01 09:00');
insert into PaymentforService
values(9, 150, '2018-03-07','P00000005', 'A-101', '2018-03-02 08:00');
insert into PaymentforService
values(10, 2000, '2018-03-02', 'P00000005', 'A-107', '2018-03-02 15:00');
insert into PaymentforService
values(11, 2150, '2018-07-06', 'P00000015', 'A-108', '2018-06-06 12:00');
insert into PaymentforService
values(12, 100, '2017-07-07', 'P00000014', 'A-002', '2017-07-0 14:30');
insert into PaymentforService
values(13, 100, '2016-10-22','P00000020', 'B-102', '2016-10-16 16:00');
insert into PaymentforService
values(14, 300, '2017-04-30','P00000016', 'F-023', '2017-04-07 07:00');
insert into PaymentforService
values(15, 1500, '2017-02-08', 'P00000008', 'A-108', '2017-02-08 09:00');
insert into PaymentforService
values(16, 100,'2017-12-01','P00000009','G-333', '2017-11-22 14:00');
insert into PaymentforService
values(17, 100, '2016-02-18', 'P00000006','A-001','2018-02-18 12:00');
insert into PaymentforService
values(18, 100, '2018-04-10', 'P00000007','A-001', '2018-04-10 12:00');
insert into PaymentforService
values(19, 3000, '2016-11-28','P00000010', 'A-107','2016-11-20 01:00');  
insert into PaymentforService
values(20, 100,'2017-07-12','P00000011', 'A-101','2017-07-12 04:00');
insert into PaymentforService
values(21, 120,'2018-07-15', 'P00000012', 'A-002', '2018-07-12 10:30');
insert into PaymentforService
values(22, 150,'2017-06-04','P00000013','F-023','2017-06-04 14:00');
insert into PaymentforService
values(23, 110, '2018-04-19','P00000018', 'F-023','2018-04-18 15:00');
insert into PaymentforService
values(24, 100, '2017-11-11', 'P00000019', 'A-002', '2017-11-11 16:30');
insert into PaymentforService
values(25, 50000,'2017-12-01','P00000020', 'A-002', '2018-10-07 18:00');


insert into CashPayment
values (1, 'Daniel Smith');
insert into CheckPayment
values (2, 'John Chan', '6238357434648362005');
insert into DebitCreditPayment
values (3, 'Diana Ross', '3735735373537367', '2019-03-12', '965');
insert into DebitCreditPayment
values (4, 'Ashton Peters', '6554533537377343', '2020-03-01', '956');
insert into InsurancePayment
values (5, 'Medavie Blue Cross', '555sg63hd7h');
insert into InsurancePayment
values (6, 'Pacific Blue Cross', 'Gg565863345');
insert into InsurancePayment
values (7, 'SSQ Financial Group', 'A5fghd73jjjd');
insert into CashPayment
values (8, 'Lyn Schneider');
insert into CashPayment
values (9, 'Lyn Schneider');
insert into CashPayment
values (10, 'Lyn Schneider');
insert into CheckPayment
values (11, 'Lizzie Nguyen', '5373828283637382789');
insert into InsurancePayment
values (12, 'Medavie Blue Cross', '6467hhd7hu7');
insert into DebitCreditPayment
values (13, 'William Web', '5366373637865267', '2021-04-12', '900');
insert into DebitCreditPayment
values (14,'Morgan Cochran', '6367389635637832', '2022-02-02', '756');
insert into InsurancePayment
values (15, 'Desjardins Insurance', 'G5f7hsf73hd8');
insert into InsurancePayment
values (16, 'Sun Life Financial', 'Rgstu2654783');
insert into InsurancePayment
values (17, 'SSQ Financial Group', 'A5fghd73jjjd');
insert into CashPayment
values (18, 'Meghan Ruano');
insert into CashPayment
values (19,'Patty Becket');
insert into InsurancePayment
values (20, 'Medavie Blue Cross', '555sg63hd7h');
insert into InsurancePayment
values (21, 'Pacific Blue Cross', 'Gg565863345');
insert into InsurancePayment
values (22, 'Desjardins Insurance', 'G5f7hsf73hd8');
insert into CheckPayment
values (23, 'Frank Espy', '7635678534562716567');
insert into CashPayment
values (24, 'Kim Lee');
insert into DebitCreditPayment
values (25, 'William Web', '5366373637865267', '2021-04-12', '900');


