--liquibase formatted sql

--changeset makarov:1
CREATE TABLE animal (
    animal_id bigint NOT NULL GENERATED BY DEFAULT AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    first_impression_in_shelter varchar(255),
    house_for_adult_pet varchar(255),
    house_for_baby_pet varchar(255),
    house_for_ill_pet varchar(255),
    initial_appeal_from_dog_handler varchar(255),
    middle_appeal_from_dog_handler varchar(255),
    transportation varchar(255),
    type varchar(255),
    CONSTRAINT animal_pkey PRIMARY KEY (animal_id)
);

CREATE TABLE Shelter (
    shelter_id bigint NOT NULL GENERATED BY DEFAULT AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    shelter_name varchar(255),
    about varchar(255),
    address varchar(255),
    contacts varchar(255),
    documents_for_adoption varchar(255),
    safety_with_pets varchar(255),
    work_hours varchar(255),
    animal_id bigint,
    CONSTRAINT shelter_pkey PRIMARY KEY (shelter_id),
    CONSTRAINT animal_id_fkey FOREIGN KEY (animal_id)
        REFERENCES public.animal (animal_id) MATCH SIMPLE
);

CREATE TABLE client (
    client_id bigint NOT NULL GENERATED BY DEFAULT AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    additional_trail_period integer,
    begin_additional_trail_period date,
    begin_trail_period date,
    chat_id bigint,
    name varchar(255),
    notification_additional_trail_period boolean NOT NULL,
    notification_trail_period_is_over boolean NOT NULL,
    phone varchar(255),
    trail_period_is_over boolean NOT NULL,
    shelter_id bigint,
    CONSTRAINT client_pkey PRIMARY KEY (client_id),
    CONSTRAINT shelter_id_fkey FOREIGN KEY (shelter_id)
        REFERENCES public.shelter (shelter_id) MATCH SIMPLE
);

CREATE TABLE report (
    report_id bigint NOT NULL GENERATED BY DEFAULT AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    data_time_report timestamp without time zone,
    notification_about_quality_report boolean NOT NULL,
    photo oid,
    report oid,
    client_id bigint,
    CONSTRAINT report_pkey PRIMARY KEY (report_id),
    CONSTRAINT client_id_fkey FOREIGN KEY (client_id)
        REFERENCES public.client (client_id) MATCH SIMPLE

);


CREATE TABLE volunteer (
    volunteer_id bigint NOT NULL GENERATED BY DEFAULT AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    chat_id bigint,
    shelter_id bigint,
    CONSTRAINT volunteer_pkey PRIMARY KEY (volunteer_id),
    CONSTRAINT shelter_id_fkey FOREIGN KEY (shelter_id)
        REFERENCES public.shelter (shelter_id) MATCH SIMPLE

)

--changeset makarov:2
INSERT INTO animal(
	animal_id, first_impression_in_shelter, house_for_adult_pet, house_for_baby_pet, house_for_ill_pet, initial_appeal_from_dog_handler, middle_appeal_from_dog_handler, transportation, type)
	VALUES (1, '', '',  '', '', '', '', '', 'cat'),
	 (2, '', '', '', '', '', '', '', 'dog');

INSERT INTO shelter(
	shelter_id, shelter_name, about, address, contacts, documents_for_adoption, safety_with_pets, work_hours, animal_id)
	VALUES (2, 'Собачий приют', 'Делаем мыло', '', '', '', '', '', 2),
	(1, 'Кошачий приют', 'Делаем воротники и меховые варежки', '', '', '', '', '', 1);