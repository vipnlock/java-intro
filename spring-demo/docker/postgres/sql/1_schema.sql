create table city
(
  id          serial,
  name        varchar(35) not null,
  countrycode varchar(3)  not null,
  district    varchar(20) not null,
  population  int         not null,
  primary key (id)
);


create type continent as enum ('Asia','Europe','North America','Africa','Oceania','Antarctica','South America');


create table country
(
  code           char(3) primary key,
  name           varchar(52)      not null,
  continent      continent        not null,
  region         varchar(26)      not null,
  surfacearea    double precision not null,
  indepyear      int,
  population     int              not null,
  lifeexpectancy double precision,
  gnp            double precision,
  gnpold         double precision,
  localname      varchar(45)      not null,
  governmentform varchar(45)      not null,
  headofstate    varchar(60),
  capital        int,
  code2          varchar(2)       not null
);

create index idx_country_name on country (name);

create table language
(
  countrycode char(3)          not null,
  language    char(30)         not null,
  isofficial  boolean          not null,
  percentage  double precision not null,
  primary key (countrycode, language)
);

create index idx_city_countrycode on city (upper(countrycode));
create index idx_country_localname on country (lower(localname));
create index idx_language_countrycode on language (upper(countrycode));
create index idx_language_language on language (lower(language));

