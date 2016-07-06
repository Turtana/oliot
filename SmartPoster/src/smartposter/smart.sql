PRAGMA foreign_keys=OFF;
BEGIN TRANSACTION;
CREATE TABLE package
(
packageid integer primary key,
codename varchar(16),
speed integer,
maxdistance integer,
size integer,
safe boolean);
INSERT INTO "package" VALUES(0,'pikavauhti',3,150,2,'false');
INSERT INTO "package" VALUES(1,'turvakyyti',2,5000,1,'true');
INSERT INTO "package" VALUES(2,'stressipurku',1,1000,3,'false');
CREATE TABLE warehouse (
wareid int primary key,
itemid int,
packageid int,
smartid int,
foreign key(packageid) references package(packageid),
foreign key(itemid) references item(itemid),
foreign key(smartid) references smartpost(smartid));
CREATE TABLE smartpost (
smartid integer primary key,
code int,
city varchar (32),
address varchar (64),
availability varchar (64),
postoffice varchar (128),
lat double,
lon double);
CREATE TABLE item (
itemid integer primary key,
name varchar (32),
size integer,
breakable bool,
description text);
INSERT INTO "item" VALUES(0,'Taulutelkkari',2,'true','Pysy ajan tasalla maailman menosta, käytä pelikonsolin kuvaputkena tai syövytä aivosi tosi-tv:llä. Kallis ja särkyvä!');
INSERT INTO "item" VALUES(1,'Maitotölkki',1,'false','Tölkki kestää kovaakin kuritusta, mutta kannattaa toimittaa äkkiä perille, muuten vastaanottaja saa hörppiä pelkkää piimää.');
INSERT INTO "item" VALUES(2,'Halkomotti',3,'false','Kuutiometri koivunklapeja talvi-iltojen lämmikkeeksi. Tai askartele oma Mölkky.');
INSERT INTO "item" VALUES(3,'Jalkapallo',1,'false','Aseta maahan, potkaise ja juokse karkuun ennen kuin ikkunan maksajaa aletaan etsiskellä.');
INSERT INTO "item" VALUES(4,'Joustinpatja',3,'false','Herneitä patjan alta on turha hakea, mutta muuta elämää löytyy senkin edestä. Myrkky maksaa erikseen.');
INSERT INTO "item" VALUES(5,'Pyrypallo',1,'true','Sen sisällä on kokonainen pieni maailma. Ikävä kyllä kesä ei koita koskaan.');
INSERT INTO "item" VALUES(6,'Moai-patsas',10,'false','Et edes halua tietää, miten kivipää on päätynyt tänne Pääsiäissaarelta. Mihinkään pakettiin sitä taitaa olla turha yrittää tunkea.');
INSERT INTO "item" VALUES(7,'Dynamiittilaatikko',1,'true','Työajan säästämiseksi nallit on viritetty valmiiksi. Kannattaa ehkä käsitellä varovasti.');
COMMIT;
