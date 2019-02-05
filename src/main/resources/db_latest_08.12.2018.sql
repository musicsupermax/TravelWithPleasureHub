create table if not exists users
(
	id serial not null
		constraint "User_pkey"
			primary key,
	username varchar(40) not null,
	password varchar(40) not null,
	first_name varchar(40),
	second_name varchar(40),
	email varchar(255) not null,
	location varchar(255),
	additional_info text,
	phone_number varchar(20),
	path_to_photo text,
	status text NOT NULL,
	role text
);

alter table users owner to postgres;

create table if not exists property_type
(
	id serial not null
		constraint "Property_type_pkey"
			primary key,
	title varchar(255) not null
);

alter table property_type owner to postgres;

create table if not exists user_review
(
	id serial not null
		constraint "User_review_pkey"
			primary key,
	made_by_user_id integer not null,
	review_text text not null,
	rate integer not null,
	date_rated date not null,
	user_id integer not null
		constraint user_id
			references users
);

alter table user_review owner to postgres;

create table if not exists property
(
	id serial not null
		constraint property_pkey
			primary key,
	title varchar(255) not null,
	description text not null,
	locality varchar(255) not null,
	address varchar(255) not null,
	user_id integer not null
		constraint user_id
			references users,
	property_type_id integer not null
		constraint property_type_id
			references property_type,
	price integer not null,
	path_to_photo text
);

alter table property owner to postgres;

create index if not exists fki_user_id
	on property (user_id);

create table if not exists property_review
(
	id serial not null
		constraint "Property_review_pkey"
			primary key,
	review_text text not null,
	rate integer not null,
	date_rated date not null,
	user_id integer not null
		constraint user_id
			references users,
	property_id integer not null
		constraint property_id
			references property
);

alter table property_review owner to postgres;

create table if not exists application
(
	id serial not null
		constraint "Application_pkey"
			primary key,
	rent_since date not null,
	rent_until date not null,
	application_text text,
	is_approved boolean,
	property_id integer not null
		constraint property_id
			references property,
	user_id integer not null
		constraint user_id
			references users
);

alter table application owner to postgres;

create index if not exists fki_property_id
	on application (property_id);

create table if not exists property_availability
(
	id serial not null
		constraint "Availability_of_property_pkey"
			primary key,
	booked_since date not null,
	booked_until date not null,
	property_id integer not null
		constraint property_id
			references property
);

alter table property_availability owner to postgres;


 create table if not exists meeting
(

  id serial not null constraint "Meeting_pkey"
   primary key,

  header varchar(40) not null,

  meeting_type integer not null,

  content varchar not null,

  location varchar not null,

  date_time timestamptz AT TIME ZONE 'UTC' not null,

  owner_id serial not null
    constraint owner_id
      references users

);
alter table meeting OWNER to postgres;

SET timezone TO 'UTC';

  create table if not exists link
(

  id serial not null constraint "link_pkey"
    primary key,

  links varchar not null,

  meeting_id integer
    constraint meeting_id
    not null references meeting
);
alter table link OWNER to postgres;


create table if not exists meeting_feedback
(
  id serial NOT NULL constraint "meeting_feedback_pkey"
    primary key,

  text varchar NOT NULL,

  feedback_type varchar  not null,

  owner_id integer not null constraint owner_id
   references users,

  meeting_id integer not null constraint meeting_id
    not null references meeting
  ON UPDATE CASCADE ON DELETE SET NULL
);

ALTER TABLE meeting_feedback

OWNER TO postgres;


create table if not exists wishing_users
(
  meeting_id integer not null constraint meeting_id
     references meeting
       ON UPDATE CASCADE ON DELETE SET NULL,

  user_id integer not null constraint user_id
     references users
       ON UPDATE CASCADE ON DELETE SET NULL,

    primary key(meeting_id,user_id)
);

ALTER TABLE wishing_users

OWNER TO postgres;


create table if not exists confirmed_users
(
  meeting_id integer not null constraint meeting_id
     references meeting
       ON UPDATE CASCADE ON DELETE SET NULL,

  user_id integer not null constraint user_id
     references users
       ON UPDATE CASCADE ON DELETE SET NULL,

    primary key(meeting_id,user_id)
);

ALTER TABLE confirmed_users

OWNER TO postgres;

