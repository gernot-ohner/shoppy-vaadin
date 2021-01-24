insert into ingredient (id, name) values (11, 'milk');
insert into ingredient (id, name) values (12, 'greek yoghurt');
insert into ingredient (id, name) values (13, 'oats');
insert into ingredient (id, name) values (14, 'frozen blueberries');

insert into recipe (id, name) values (11, 'my breakfast');
insert into recipe (id, name) values (12, 'porridge');

insert into recipe_ingredients (recipe_id, ingredients_id) values (11, 11);
insert into recipe_ingredients (recipe_id, ingredients_id) values (11, 12);
insert into recipe_ingredients (recipe_id, ingredients_id) values (11, 13);
insert into recipe_ingredients (recipe_id, ingredients_id) values (11, 14);

insert into recipe_ingredients (recipe_id, ingredients_id) values (12, 11);
insert into recipe_ingredients (recipe_id, ingredients_id) values (12, 13);
insert into recipe_ingredients (recipe_id, ingredients_id) values (12, 14);
