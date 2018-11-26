create table user
(
  email     varchar(50) not null
    primary key,
  password  varchar(16) null,
  name      varchar(50) null,
  birthdate date        null
);

create table appointment
(
  id          int auto_increment,
  email       varchar(50) not null,
  date        date        null,
  startTime   time        null,
  endTime     time        null,
  category    varchar(50) null,
  event       varchar(50) null,
  description text        null,
  primary key (id, email),
  constraint appointment_user_fk
  foreign key (email) references user (email)
);

create table setting
(
  email            varchar(50) not null
    primary key,
  calendarmode     varchar(5)  null,
  calendarcolor    varchar(7)  null,
  appointmentcolor varchar(7)  null,
  constraint setting_user_fk
  foreign key (email) references user (email)
);

