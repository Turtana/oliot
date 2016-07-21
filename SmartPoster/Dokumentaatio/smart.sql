PRAGMA foreign_keys=OFF;
BEGIN TRANSACTION;
CREATE TABLE package (
packageid integer primary key,
codename varchar(16),
speed integer,
maxdistance integer,
size integer,
safe boolean,
desc text,
CHECK (speed > 0 AND speed < 4),
CHECK (size > 0),
CHECK (packageid >= 0));
INSERT INTO "package" VALUES(0,'Pikavauhti',1,150,2,0,'Pikavauhtia määränpäähän vaaroista välittämättä.');
INSERT INTO "package" VALUES(1,'Turvakyyti',2,16000,1,1,'Turvallinen mutta ahdas kyyti varovaisille postittajille.');
INSERT INTO "package" VALUES(2,'Stressipurku',3,1000,3,0,'Timotei-miehellä on känkkäränkkäpäivä. Älä postita särkyvää.');
CREATE TABLE warehouse (
wareid int primary key,
itemid int,
packageid int,
foreign key(packageid) references package(packageid),
foreign key(itemid) references item(itemid),
CHECK (wareid >= 0));
CREATE TABLE smartpost (
smartid integer primary key,
addressid int,
availability varchar (64),
postoffice varchar (128),
coordid int,
foreign key(coordid) references coords(coordid),
foreign key(addressid) references address(addressid),
CHECK (smartid >= 0));
CREATE TABLE coords (
coordid int primary key,
lat double,
lon double,
CHECK (coordid >= 0));
CREATE TABLE address (
addressid integer primary key,
code varchar (10),
city varchar (32),
street varchar (64),
CHECK (addressid >= 0));
CREATE TABLE item (
itemid integer primary key,
name varchar (32),
size integer,
breakable boolean,
description text,
CHECK (itemid >= 0),
CHECK (size > 0));

CREATE TABLE history (
historyid integer primary key,
eventtime varchar (20),
startcity varchar (32),
destcity varchar (32),
packagename varchar (16),
itemname varchar (32),
distance double,
CHECK (historyid >= 0));

CREATE TABLE event(
eventid integer primary key,
startid integer,
destid integer,
packageid integer,
itemid integer,
foreign key(startid) references smartpost(smartid),
foreign key(destid) references smartpost(smartid),
foreign key(packageid) references package(packageid),
foreign key(itemid) references item(itemid),
CHECK (eventid >= 0));
COMMIT;
