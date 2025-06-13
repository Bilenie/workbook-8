USE sakila;

-- Prompt the user to search for actor by name and display the results of the search.

SELECT
	a.*
FROM 
   actor a
WHERE 
  a.first_name = 'Penelope'   AND a.last_name = 'guiness'; -- on my query this will be question mark name take the full name split it and get it 
  
-- Add another prompt to ask the user to enter an actor id, then search for and - where actor id = ?
-- display a list of movies that the actor has appeared in.

SELECT
	a.actor_id,
    a.first_name, 
	a.last_name,
	f.title
FROM 
   actor a
    JOIN
   film_actor fa ON ( a.actor_id = fa.actor_id)
    JOIN 
   film f ON (f.film_id = fa.film_id)
  WHERE a.actor_id = 1; -- user input


