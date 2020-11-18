# TFM

Servicios web SOAP inseguros para autoaprendizaje en explotación de vulnerabilidades.

## Instalación
Los pasos necesarios para la instalación se los detalla bajo el directorio "documentacion"
### Requisitos
```bash
Java 1.8 o superior
PostgreSql 9.6 o superior
Software que permita gestionar el control de versiones GIT
Eclipse IDE 2020-06
WildFly 17.0.1 Final
```

### Base de datos
Se detalla a continuación el script necesario para generar la base de datos, es necesario recalcar que el usuario por defecto utilizado es “postgres”, también se deja un respaldo de la base de datos con información en el repositorio GIT, para que el mismo pueda ser restaurado.

```sql
/*
PostgreSQL Backup
Database: unir_tfm_rfam_db/public
Backup Time: 2020-09-18 15:42:46
*/

DROP TABLE IF EXISTS "public"."_user";
DROP TABLE IF EXISTS "public"."authority";
DROP TABLE IF EXISTS "public"."bank_information";
DROP TABLE IF EXISTS "public"."debt_information";
DROP TABLE IF EXISTS "public"."housing_project";
DROP TABLE IF EXISTS "public"."patrimonial_information";
DROP TABLE IF EXISTS "public"."postulant";
DROP TABLE IF EXISTS "public"."system_parameter";
DROP TABLE IF EXISTS "public"."user_authority";
CREATE TABLE "_user" (
  "name" varchar COLLATE "pg_catalog"."default",
  "surname" varchar COLLATE "pg_catalog"."default",
  "dni" varchar(15) COLLATE "pg_catalog"."default",
  "email" varchar(80) COLLATE "pg_catalog"."default",
  "username" varchar(20) COLLATE "pg_catalog"."default",
  "password" varchar COLLATE "pg_catalog"."default",
  "isactive" bool,
  "id" int8 NOT NULL,
  "profile_picture_url" varchar(100) COLLATE "pg_catalog"."default",
  "profile_picture_name" varchar(100) COLLATE "pg_catalog"."default"
)
;
ALTER TABLE "_user" OWNER TO "postgres";
CREATE TABLE "authority" (
  "name" varchar(50) COLLATE "pg_catalog"."default" NOT NULL,
  "id" int8 NOT NULL
)
;
ALTER TABLE "authority" OWNER TO "postgres";
CREATE TABLE "bank_information" (
  "id" int8 NOT NULL,
  "name" varchar(100) COLLATE "pg_catalog"."default",
  "account_number" varchar(20) COLLATE "pg_catalog"."default",
  "account_type" varchar(15) COLLATE "pg_catalog"."default",
  "balance" numeric(19,2),
  "patrimonial_information_id" int8
)
;
ALTER TABLE "bank_information" OWNER TO "postgres";
CREATE TABLE "debt_information" (
  "id" int8 NOT NULL,
  "institution_name" varchar(100) COLLATE "pg_catalog"."default",
  "start_date" date,
  "debt_term" int8,
  "amount" numeric(19,2),
  "obligation_type" varchar(25) COLLATE "pg_catalog"."default",
  "credit_card_number" varchar(19) COLLATE "pg_catalog"."default",
  "patrimonial_information_id" int8
)
;
ALTER TABLE "debt_information" OWNER TO "postgres";
COMMENT ON COLUMN "debt_information"."institution_name" IS 'nombre de la institución financiera';
COMMENT ON COLUMN "debt_information"."debt_term" IS 'plazo en meses';
COMMENT ON COLUMN "debt_information"."obligation_type" IS 'tipo de  obligacion de pago, prestamo, tarjeta crédito';
CREATE TABLE "housing_project" (
  "id" int8 NOT NULL,
  "name" varchar(100) COLLATE "pg_catalog"."default",
  "number" varchar(10) COLLATE "pg_catalog"."default",
  "is_enabled" bool
)
;
ALTER TABLE "housing_project" OWNER TO "postgres";
COMMENT ON COLUMN "housing_project"."id" IS 'Identificador';
COMMENT ON COLUMN "housing_project"."name" IS 'Nombre del proyecto';
CREATE TABLE "patrimonial_information" (
  "id" int8 NOT NULL,
  "presentation_date" date,
  "observation" varchar(255) COLLATE "pg_catalog"."default",
  "housing_project_id" int8,
  "postulant_id" int8
)
;
ALTER TABLE "patrimonial_information" OWNER TO "postgres";
CREATE TABLE "postulant" (
  "id" int8 NOT NULL,
  "name" varchar(100) COLLATE "pg_catalog"."default",
  "dni" varchar(20) COLLATE "pg_catalog"."default",
  "address" varchar(200) COLLATE "pg_catalog"."default",
  "postal_code" varchar(10) COLLATE "pg_catalog"."default",
  "phone_number" varchar(15) COLLATE "pg_catalog"."default",
  "email" varchar(100) COLLATE "pg_catalog"."default"
)
;
ALTER TABLE "postulant" OWNER TO "postgres";
CREATE TABLE "system_parameter" (
  "id" int8 NOT NULL,
  "name" varchar(100) COLLATE "pg_catalog"."default",
  "value" varchar(100) COLLATE "pg_catalog"."default"
)
;
ALTER TABLE "system_parameter" OWNER TO "postgres";
CREATE TABLE "user_authority" (
  "user_id" int8 NOT NULL,
  "authority_id" int8 NOT NULL
)
;
ALTER TABLE "user_authority" OWNER TO "postgres";
ALTER TABLE "_user" ADD CONSTRAINT "_user_pk" PRIMARY KEY ("id");
ALTER TABLE "authority" ADD CONSTRAINT "authority_pk" PRIMARY KEY ("id");
ALTER TABLE "bank_information" ADD CONSTRAINT "bank_information_pk" PRIMARY KEY ("id");
ALTER TABLE "debt_information" ADD CONSTRAINT "debt_information_pk" PRIMARY KEY ("id");
ALTER TABLE "housing_project" ADD CONSTRAINT "housing_project_pk" PRIMARY KEY ("id");
ALTER TABLE "patrimonial_information" ADD CONSTRAINT "patrimonial_information_pk" PRIMARY KEY ("id");
ALTER TABLE "postulant" ADD CONSTRAINT "postulant_pk" PRIMARY KEY ("id");
ALTER TABLE "bank_information" ADD CONSTRAINT "bank_information_pi_fk" FOREIGN KEY 
("patrimonial_information_id") REFERENCES "public"."patrimonial_information" ("id") 
ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "debt_information" ADD CONSTRAINT "debt_information_pi_fk" FOREIGN KEY 
("patrimonial_information_id") REFERENCES "public"."patrimonial_information" ("id") 
ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "patrimonial_information" ADD CONSTRAINT "patrimonial_information_hp_fk" FOREIGN KEY 
("housing_project_id") REFERENCES "public"."housing_project" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "patrimonial_information" ADD CONSTRAINT "patrimonial_information_post_fk" FOREIGN KEY 
("postulant_id") REFERENCES "public"."postulant" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "user_authority" ADD CONSTRAINT "user_authority_a_fk" FOREIGN KEY ("authority_id") REFERENCES 
"public"."authority" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "user_authority" ADD CONSTRAINT "user_authority_u_fk" FOREIGN KEY ("user_id") REFERENCES 
"public"."_user" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
```
