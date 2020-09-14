Select * from bolsa_puntos WHERE DATE_PART('day',fecha_caducidad_puntaje::timestamp - current_date::timestamp)=28

Select * from bolsa_puntos where (EXTRACT(DAY from fecha_caducidad_puntaje)-EXTRACT(DAY from fecha_asignacion_puntaje))=4

SELECT TO_DATE('20170103','YYYYMMDD');

