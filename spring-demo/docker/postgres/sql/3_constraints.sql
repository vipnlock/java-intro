alter table city
  add constraint city_country_fk
    foreign key (countrycode) references country (code);

alter table country
  add constraint city_capital_fk
    foreign key (capital) references city (id);

alter table language
  add constraint language_country_fk
    foreign key (countrycode) references country (code);

select setval('city_id_seq', 4079);