package hr.mario.kalisar.bestburger;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static hr.mario.kalisar.bestburger.BurgerActivity.EXTRA_DESCRIPTION;
import static hr.mario.kalisar.bestburger.BurgerActivity.EXTRA_IMAGE_URL;
import static hr.mario.kalisar.bestburger.BurgerActivity.EXTRA_OPIS;
import static hr.mario.kalisar.bestburger.BurgerActivity.EXTRA_TITLE;

public class RecipesActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipes_activity);

        Intent intent = getIntent();
        String imageUrl = intent.getStringExtra(EXTRA_IMAGE_URL);
        String title = intent.getStringExtra(EXTRA_TITLE);
        String description = intent.getStringExtra(EXTRA_DESCRIPTION);
        //String opis = intent.getStringExtra(EXTRA_OPIS);

        ImageView imageView = findViewById(R.id.imageViewBurger);
        TextView textViewTitle = findViewById(R.id.titleBurger);
        TextView textViewDescription = findViewById(R.id.descriptionBurger);
        ListView listViewingredients = findViewById(R.id.listViewIngredients);


        Picasso.get().load(imageUrl).resize(1300, 750).into(imageView);
        textViewTitle.setText(title);
        textViewDescription.setText(description);


        ArrayList<String> arrayListIngredients = new ArrayList<>();

        if (textViewTitle.getText().equals("Cheddar-Stuffed Burgers")) {
            arrayListIngredients.add("Kosher salt");
            arrayListIngredients.add("8 cups finely shredded green cabbage (from a 1 1/2-pound head) ");
            arrayListIngredients.add("1 cup distilled white vinegar ");
            arrayListIngredients.add("1 tablespoon sugar ");
            arrayListIngredients.add("2 tablespoons yellow mustard seeds ");
            arrayListIngredients.add("Vegetable oil, for frying ");
            arrayListIngredients.add("5 large shallots, very thinly sliced crosswise and separated into rings");
            arrayListIngredients.add("1/4 cup Wondra flour (see Note)");
            arrayListIngredients.add("1 1/2 pounds ground beef chuck, preferably 85 percent lean");
            arrayListIngredients.add("1 teaspoon onion powder ");
            arrayListIngredients.add("1 teaspoon garlic powder ");
            arrayListIngredients.add("1 teaspoon sweet smoked paprika ");
            arrayListIngredients.add("1 tablespoon Worcestershire sauce ");
            arrayListIngredients.add("Freshly ground pepper ");
            arrayListIngredients.add("6 ounces extra-sharp cheddar, shredded ");
            arrayListIngredients.add("2 cups baby arugula ");
            arrayListIngredients.add("4 brioche buns, split and toasted ");
        }
        if (textViewTitle.getText().equals("Double Cheeseburgers")) {
            arrayListIngredients.add("1/3 cup mayonnaise ");
            arrayListIngredients.add("1 tablespoon toasted sesame seeds ");
            arrayListIngredients.add("4 tablespoons unsalted butter, at room temperature ");
            arrayListIngredients.add("4 brioche hamburger buns, split ");
            arrayListIngredients.add("2 pounds ground chuck, shaped into eight 1/4-inch-thick patties ");
            arrayListIngredients.add("Kosher salt ");
            arrayListIngredients.add("Freshly ground pepper ");
            arrayListIngredients.add("2 tablespoons olive oil ");
            arrayListIngredients.add("8 slices of cheddar cheese ");
            arrayListIngredients.add("4 butter lettuce leaves ");
            arrayListIngredients.add("4 shiso or sesame (perilla) leaves ");
            arrayListIngredients.add("4 thin slices of tomato ");
            arrayListIngredients.add("4 thin slices of red onion ");
        }
        if (textViewTitle.getText().equals("Asian-Style Pork Burgers")) {
            arrayListIngredients.add("1 1/2 pounds ground pork ");
            arrayListIngredients.add("2 scallions, thinly sliced ");
            arrayListIngredients.add("1 tablespoon finely grated fresh ginger ");
            arrayListIngredients.add("1 large garlic clove, minced ");
            arrayListIngredients.add("1 1/2 teaspoons Asian sesame oil ");
            arrayListIngredients.add("Kosher salt and freshly ground pepper ");
            arrayListIngredients.add("4 hamburger buns, split ");
            arrayListIngredients.add("2 cups coleslaw mix ");
            arrayListIngredients.add("2 teaspoons rice vinegar ");
            arrayListIngredients.add("1 teaspoon soy sauce ");
        }
        if (textViewTitle.getText().equals("Cheddar BLT Burgers")) {
            arrayListIngredients.add("1/2 cup mayonnaise ");
            arrayListIngredients.add("1/3 cup ketchup ");
            arrayListIngredients.add("1 tablespoon red wine vinegar ");
            arrayListIngredients.add("1 tablespoon grated onion ");
            arrayListIngredients.add("1 tablespoon chopped parsley ");
            arrayListIngredients.add("1 tablespoon chopped tarragon ");
            arrayListIngredients.add("1 teaspoon Worcestershire sauce ");
            arrayListIngredients.add("12 ounces thickly sliced bacon ");
            arrayListIngredients.add("1 1/3 pounds ground beef chuck ");
            arrayListIngredients.add("1 1/3 pounds ground beef sirloin ");
            arrayListIngredients.add("1 teaspoon kosher salt ");
            arrayListIngredients.add("1/2 teaspoon freshly ground pepper ");
            arrayListIngredients.add("2 tablespoons unsalted butter, melted ");
            arrayListIngredients.add("3 ounces sharp cheddar cheese, cut into 6 slices ");
            arrayListIngredients.add("6 hamburger buns, split and toasted ");
            arrayListIngredients.add("6 iceberg lettuce leaves ");
            arrayListIngredients.add("6 slices of tomato ");
            arrayListIngredients.add("6 slices of red onion ");
        }
        if (textViewTitle.getText().equals("Bacon-and-Kimchi Burgers")) {
            arrayListIngredients.add("1/4 cup sambal oelek (Indonesian chile sauce) ");
            arrayListIngredients.add("1/4 cup mayonnaise ");
            arrayListIngredients.add("1/4 cup ketchup ");
            arrayListIngredients.add("4 slices of thick-cut bacon ");
            arrayListIngredients.add("1 1/4 pounds ground beef chuck ");
            arrayListIngredients.add("Kosher salt ");
            arrayListIngredients.add("4 slices of American cheese ");
            arrayListIngredients.add("4 potato buns, toasted ");
            arrayListIngredients.add("1 cup chopped drained cabbage kimchi (6 ounces) ");
        }
        if (textViewTitle.getText().equals("Mini Burgers")) {
            arrayListIngredients.add("1 pound ground chuck ");
            arrayListIngredients.add("1/4 pound boneless short rib, minced ");
            arrayListIngredients.add("6 mini brioche buns, split and toasted ");
            arrayListIngredients.add("Ketchup and pickle slices, for serving ");
            arrayListIngredients.add("Crispy Onion Rings");
        }
        if (textViewTitle.getText().equals("Minetta Burger")) {
            arrayListIngredients.add("2 tablespoons unsalted butter ");
            arrayListIngredients.add("1 large yellow onion, halved and thinly sliced ");
            arrayListIngredients.add("1/4 cup water ");
            arrayListIngredients.add("Kosher salt and freshly ground pepper ");
            arrayListIngredients.add("2 pounds ground sirloin ");
            arrayListIngredients.add("1 tablespoon vegetable oil ");
            arrayListIngredients.add("5 ounces sharp cheddar cheese, thinly sliced ");
            arrayListIngredients.add("4 brioche buns, split and toasted ");
            arrayListIngredients.add("Lettuce, tomato slices and pickles, for serving ");
        }
        if (textViewTitle.getText().equals("Nacho Burgers")) {
            arrayListIngredients.add("1 1/2 pounds ground beef chuck ");
            arrayListIngredients.add("Vegetable oil, for brushing ");
            arrayListIngredients.add("Salt");
            arrayListIngredients.add("Freshly ground pepper ");
            arrayListIngredients.add("4 hamburger buns, split and toasted ");
            arrayListIngredients.add("Sliced pickled jalapeños and blue corn tortilla chips, for topping ");

        }


        ArrayAdapter<String> arrayAdapterIngredients;
        arrayAdapterIngredients = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayListIngredients);

        listViewingredients.setAdapter(arrayAdapterIngredients);


    }
}
