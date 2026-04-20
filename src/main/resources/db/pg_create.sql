-- DROP DATABASE IF EXISTS db1;
-- Role: app_user
-- DROP ROLE IF EXISTS app_user;

CREATE ROLE app_user WITH
    LOGIN
    NOSUPERUSER
    INHERIT
    NOCREATEDB
    NOCREATEROLE
    NOREPLICATION
    NOBYPASSRLS
    PASSWORD 'your_password';

GRANT db_owner TO app_user WITH INHERIT OPTION, SET OPTION;


CREATE DATABASE db1
    WITH
    OWNER = app_user
    ENCODING = 'UTF8'
    LC_COLLATE = 'Portuguese_Brazil.1252'
    LC_CTYPE = 'Portuguese_Brazil.1252'
    LOCALE_PROVIDER = 'libc'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1
    IS_TEMPLATE = False;
-- SCHEMA: app

-- DROP SCHEMA IF EXISTS app ;

CREATE SCHEMA IF NOT EXISTS app
    AUTHORIZATION app_user;

GRANT ALL ON SCHEMA app TO PUBLIC;

GRANT ALL ON SCHEMA app TO app_user;

ALTER ROLE app_user SET search_path TO app;

-- Table: app.users

-- DROP TABLE IF EXISTS app.users;

CREATE TABLE IF NOT EXISTS app.users
(
    id integer NOT NULL DEFAULT nextval('app.users_id_seq'::regclass),
    login character varying(100) COLLATE pg_catalog."default" NOT NULL,
    password character varying(300) COLLATE pg_catalog."default" NOT NULL,
    create_date date,
    active boolean,
    admin boolean,
    CONSTRAINT users_pkey PRIMARY KEY (id)
    )

    TABLESPACE pg_default;

ALTER TABLE IF EXISTS app.users
    OWNER to app_user;

GRANT ALL ON TABLE app.users TO app_user;
GRANT ALL ON TABLE app.users TO PUBLIC;


