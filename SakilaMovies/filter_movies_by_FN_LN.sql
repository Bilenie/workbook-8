USE sakila;
SELECT
	a.first_name, 
	a.last_name,
	f.title
FROM 
   actor a
    JOIN
   film_actor fa ON ( a.actor_id = fa.actor_id)
    JOIN 
   film f ON (f.film_id = fa.film_id)
   WHERE a.first_name = 'Penel'   AND a.last_name = 'guines'; 