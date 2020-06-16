package com.kari.travelfront.operations;

import com.kari.travelfront.domain.Cocktail;
import com.kari.travelfront.domain.Drink;
import com.kari.travelfront.service.CocktailService;
import com.kari.travelfront.service.DrinkService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CocktailSupport {

    private DrinkService service = new DrinkService();
    private CocktailService cocktailService = new CocktailService();

    public Drink getTheDrink(){
        List<Drink> list = service.getDrinks();
       return list.get(list.size()-1);
    }

    public Cocktail getInspired(){
        Cocktail cocktail = cocktailService.getDrink();
        return cocktail;
    }
}
