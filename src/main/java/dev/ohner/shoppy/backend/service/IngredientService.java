package dev.ohner.shoppy.backend.service;

import dev.ohner.shoppy.backend.persistence.IngredientRepository;
import dev.ohner.shoppy.backend.persistence.model.Ingredient;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngredientService {

    private final IngredientRepository ingredientRepository;

    public IngredientService(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    public List<Ingredient> findAll() {
        return ingredientRepository.findAll();
    }

    public List<Ingredient> findAll(String searchTerm) {
        if (searchTerm == null) {
            return findAll();
        } else {
            return ingredientRepository.search(searchTerm);
        }
    }

    public void save(Ingredient ingredient) {
        ingredientRepository.save(ingredient);
    }
}
