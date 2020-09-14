create table cliente(
	id serial primary key,
	nombre character varying (30) NOT NULL,
	apellido character varying (30) NOT NULL,
	numero_documento character varying (30) unique,
	tipo_documento integer not null,
	nacionalidad character varying (30) NOT null,
	email text not null unique,
	telefono text not null,
	fecha_nacimiento timestamp with time zone NOT NULL,
	CHECK (tipo_documento=1 or tipo_documento=2 or tipo_documento=3)
)WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

create table concepto(
	id serial primary key,
	descripcion_concepto text not null,
	puntos_requeridos integer not null
)WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

create table reglas_conceptos(
	id serial primary key,
	limite_inferior integer not null,
	limite_superior integer not null,
	monto_equivalencia numeric DEFAULT 1
)WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

create table vencimientos_puntos(
	id serial primary key,
	fecha_inicio timestamp with time zone NOT NULL,
	dias_duracion_puntos integer not null,
	fecha_fin timestamp with time zone
)WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

create table bolsa_puntos(
	id serial primary key,
	id_cliente integer null,
	fecha_asignacion_puntaje timestamp with time zone NOT NULL,
	fecha_caducidad_puntaje timestamp with time zone NOT NULL,
	puntaje_asignado integer not null,
	puntaje_utilizado integer not null,
	saldo_puntos integer not null,
	monto_operacion integer not null,
	CONSTRAINT fk_id_cliente FOREIGN KEY (id_cliente)
        REFERENCES public.cliente (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

create table uso_puntos_cab(
	id serial primary key,
	id_cliente integer not null,
	puntaje_utilizado integer not null,
	fecha timestamp with time zone NOT NULL,
	id_concepto integer not null,
	CONSTRAINT fk_id_cliente_cab FOREIGN KEY (id_cliente)
        REFERENCES public.cliente (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;
ALTER TABLE uso_puntos_cab
ADD CONSTRAINT id_concepto_fk
FOREIGN KEY (id_concepto)
REFERENCES concepto(id)
ON DELETE CASCADE;

create table uso_puntos_det(
	id_uso_puntos_cab integer not null PRIMARY KEY,
	puntaje_utilizado integer not null,
	id_bolsa integer not null,
	CONSTRAINT fk_id_uso_puntos_cab FOREIGN KEY (id_uso_puntos_cab)
        REFERENCES public.uso_puntos_cab (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
	CONSTRAINT fk_id_bolsa FOREIGN KEY (id_bolsa)
        REFERENCES public.bolsa_puntos (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;


