-- ers_reimbursement table
create table ers_reimbursement(
	reimb_id serial primary key,
	reimb_amount numeric(10,2) not null,
	reimb_submitted timestamp without time zone default current_timestamp not null,
	reimb_resolved timestamp without time zone,
	reimb_description varchar(250),
	reimb_receipt boolean default false,
	reimb_author int references ers_users(ers_users_id) not null,
	reimb_resolver int references ers_users(ers_users_id),
	reimb_status_id int references ers_reimbursement_status(reimb_status_id) not null,
	reimb_type_id int references ers_reimbursement_type(reimb_type_id) not null	
);

drop table ers_reimbursement ;

--ers_users table
create table ers_users(
	ers_users_id serial primary key,
	ers_username varchar(50) unique not null,
	ers_password varchar(50) not null,
	user_first_name varchar(100) not null,
	user_last_name varchar(100) not null,
	user_email varchar(100) unique not null,
	user_role_id int references ers_user_role(ers_user_role_id) not null
);

--user_roles reference table
create table ers_user_role(
	ers_user_role_id serial primary key,
	user_role varchar(10) not null
);

--ers_reimbursement_typ table
create table ers_reimbursement_type(
	reimb_type_id serial primary key,
	reimb_type varchar(10) not null
);

--ers_reimbursement_status
create table ers_reimbursement_status(
	reimb_status_id serial primary key,
	reimb_status varchar(10) not null
);


insert into ers_reimbursement_status(reimb_type) values ('Pending'),
('Approved'),
('Denied');

insert into ers_reimbursement_type(reimb_type) values ('Lodging'),
('Travel'),
('Food'),
('Other');

insert into ers_user_role(user_role) values ('Employee'),
('Manager');

insert into ers_users values (1,'silas839','verysecurepassword','Silas','Kidd Myers','silaskiddmyers@gmail.com',2),
(2,'hpotter', 'magic4life','Harry','Potter','hpotter@gmail.com',1 );

-- Insert new receipt
insert into ers_reimbursement (reimb_amount, reimb_description, reimb_author, reimb_status_id, reimb_type_id) values (222.22, 'Wanted to buy some jelly beans', 2,1,3);

-- Retrieve all receipts of a given status reimb_id, reimb_amount, reimb_submitted, reimb_resolved, reimb_description, reimb_receipt, ers_username, reimb_status, reimb_type
select reimb_id, reimb_amount, reimb_submitted, reimb_resolved, reimb_description, reimb_receipt, ers_username, reimb_status, reimb_type from
((select * from 
	(select * from ers_reimbursement er inner join ers_reimbursement_status ers on er.reimb_status_id = ers.reimb_status_id) ta 
		inner join (select ers_users_id, ers_username from ers_users) eu on ta.reimb_author = eu.ers_users_id) three 
	inner join ers_reimbursement_type rt on rt.reimb_type_id = three.reimb_type_id) where reimb_status = 'Pending';

--select reimbursement resolver
select ers_username from ers_reimbursement er inner join ers_users eu on er.reimb_resolver = eu.ers_users_id where er.reimb_id = 1;
select* from ers_reimbursement er ;

--Retrieve a User given a username- use reference table for user role
select ers_users_id ,ers_username , ers_password, user_first_name, user_last_name, user_email, eur.user_role from (select * from ers_users eu1 left outer join ers_user_role eur2 on eur2.ers_user_role_id = eu1.user_role_id) eur where ers_username = 'silas839';

-- Retreive all reimbursements reimb_id ,reimb_amount ,reimb_submitted ,reimb_resolved ,reimb_description ,reimb_receipt ,
select
	reimb_id ,reimb_amount ,reimb_submitted ,reimb_resolved ,reimb_description ,reimb_receipt , reimb_status, reimb_type, eu.ers_username, eu2.ers_username
from
	ers_reimbursement er
left outer join ers_reimbursement_status ers on
	ers.reimb_status_id = er.reimb_status_id
left outer join ers_reimbursement_type ert on
	er.reimb_type_id = ert.reimb_type_id
left outer join (select ers_users_id, ers_username from ers_users) eu on 
	er.reimb_author = eu.ers_users_id 
left outer join  (select ers_users_id, ers_username from ers_users) eu2 on er.reimb_resolver = eu.ers_users_id where eu.ers_username = 'hpotter';
;

alter table ers_reimbursement alter column reimb_submitted set data type timestamp without time zone;

update ers_reimbursement set reimb_status_id = 2, reimb_resolved = current_timestamp, reimb_resolver = 1 where reimb_id = 1;

select * from ers_reimbursement er ;

delete from ers_reimbursement where reimb_id = 23;

alter table ers_users alter column ers_password set data type varchar(100);
update ers_users set ers_password = '1d604998daff644540f1390636ee61e88f8d1b2a83e04521e494d48ca272a6bc' where ers_username = 'silas839';




