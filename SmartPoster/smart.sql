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

INSERT INTO "item" VALUES(0,'Taulutelkkari',2,1,'Pysy ajan tasalla maailman menosta, käytä pelikonsolin kuvaputkena tai syövytä aivosi tosi-tv:llä.');
INSERT INTO "item" VALUES(1,'Maitotölkki',1,0,'Tölkki kestää kovaakin kuritusta. Ja vahvistaa luita. Siis sisällä oleva maito.');
INSERT INTO "item" VALUES(2,'Halkomotti',3,0,'Kuutiometri koivunklapeja talvi-iltojen lämmikkeeksi. Tai askartele oma Mölkky.');
INSERT INTO "item" VALUES(3,'Jalkapallo',1,0,'Aseta maahan, potkaise ja juokse karkuun ennen kuin ikkunan maksajaa aletaan etsiskellä.');
INSERT INTO "item" VALUES(4,'Joustinpatja',3,0,'Herneitä patjan alta on turha hakea, mutta muuta elämää löytyy senkin edestä. Myrkky maksaa erikseen.');
INSERT INTO "item" VALUES(5,'Pyrypallo',1,1,'Sen sisällä on kokonainen pieni maailma. Ikävä kyllä kesä ei koita koskaan.');
INSERT INTO "item" VALUES(6,'Moai-patsas',10,0,'Jonkun matkamuisto Pääsiäissaarelta. Mihinkään pakettiin sitä taitaa olla turha yrittää tunkea.');
INSERT INTO "item" VALUES(7,'Dynamiittilaatikko',1,1,'Työajan säästämiseksi nallit on viritetty valmiiksi. Kannattaa ehkä käsitellä varovasti.');

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
