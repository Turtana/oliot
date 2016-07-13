PRAGMA foreign_keys=OFF;
BEGIN TRANSACTION;
CREATE TABLE package (
packageid integer primary key,
codename varchar(16),
speed integer,
maxdistance integer,
size integer,
safe boolean,
desc text);
INSERT INTO "package" VALUES(0,'Pikavauhti',1,150,2,'false', 'Pikavauhtia määränpäähän vaaroista välittämättä. Ei kulje 150 km pidemmälle.

Koko: 2
Nopeus: 3
Turvallinen: Ei');
INSERT INTO "package" VALUES(1,'Turvakyyti',2,16000,1,'true', 'Turvallinen mutta ahdas kyyti varovaisille postittajille.

Koko: 1
Nopeus: 2
Turvallinen: Kyllä');
INSERT INTO "package" VALUES(2,'Stressipurku',3,1000,3,'false', 'Timotei-miehellä on känkkäränkkäpäivä. Älä postita särkyvää.

Koko: 3
Nopeus: 1
Turvallinen: Ei');
CREATE TABLE warehouse (
wareid int primary key,
itemid int,
packageid int,
foreign key(packageid) references package(packageid),
foreign key(itemid) references item(itemid));
CREATE TABLE smartpost (
smartid integer primary key,
addressid int,
availability varchar (64),
postoffice varchar (128),
coordid int,
foreign key(coordid) references coords(coordid),
foreign key(addressid) references address(addressid));
CREATE TABLE coords (
coordid int primary key,
lat double,
lon double);
CREATE TABLE address (
addressid integer primary key,
code varchar (10),
city varchar (32),
street varchar (64));
CREATE TABLE item (
itemid integer primary key,
name varchar (32),
size integer,
breakable bool,
description text);
INSERT INTO "item" VALUES(0,'Taulutelkkari',2,'true','Pysy ajan tasalla maailman menosta, käytä pelikonsolin kuvaputkena tai syövytä aivosi tosi-tv:llä.

Koko: 2
Särkyvä');
INSERT INTO "item" VALUES(1,'Maitotölkki',1,'false','Tölkki kestää kovaakin kuritusta. Ja vahvistaa luita. Siis sisällä oleva maito.

Koko: 1
Kestävä');
INSERT INTO "item" VALUES(2,'Halkomotti',3,'false','Kuutiometri koivunklapeja talvi-iltojen lämmikkeeksi. Tai askartele oma Mölkky.

Koko: 3
Kestävä');
INSERT INTO "item" VALUES(3,'Jalkapallo',1,'false','Aseta maahan, potkaise ja juokse karkuun ennen kuin ikkunan maksajaa aletaan etsiskellä.

Koko: 1
Kestävä');
INSERT INTO "item" VALUES(4,'Joustinpatja',3,'false','Herneitä patjan alta on turha hakea, mutta muuta elämää löytyy senkin edestä. Myrkky maksaa erikseen.

Koko: 3
Kestävä');
INSERT INTO "item" VALUES(5,'Pyrypallo',1,'true','Sen sisällä on kokonainen pieni maailma. Ikävä kyllä kesä ei koita koskaan.

Koko: 1
Särkyvä');
INSERT INTO "item" VALUES(6,'Moai-patsas',10,'false','Jonkun matkamuisto Pääsiäissaarelta. Mihinkään pakettiin sitä taitaa olla turha yrittää tunkea.

Koko: 10
Kestävä');
INSERT INTO "item" VALUES(7,'Dynamiittilaatikko',1,'true','Työajan säästämiseksi nallit on viritetty valmiiksi. Kannattaa ehkä käsitellä varovasti.

Koko: 1
Särkyvä');
COMMIT;
