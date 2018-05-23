package at.ac.uibk.repository;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Service;

import at.ac.uibk.model.Category;

@Service("categoryRepository")
public class CategoryRepository {
	ArrayList<Category> categories = new ArrayList<>();
	
	public CategoryRepository() {
        JSONParser parser = new JSONParser();
        try {
        	Object obj = parser.parse(new FileReader("events.json"));
        	JSONObject jsonObj = (JSONObject) obj;
			JSONArray jsonArr = (JSONArray)((JSONObject)jsonObj.get("body")).get("Events");
			
			for (int i = 0; i < jsonArr.size(); i++) {
				JSONObject event = (JSONObject) jsonArr.get(i);
				JSONObject category = (JSONObject)((JSONArray)event.get("Categories")).get(0);
				
				int id = Integer.parseInt(category.get("id").toString());
				String name = category.get("name").toString();
				String identifier = category.get("identifier").toString();
				String description = category.get("description").toString();
				
				updateCategory(id, name, identifier, description);
			}


        	
        }catch (Exception e) {
        	e.printStackTrace();
		}
        System.out.println("we have " + categories.size() + " categories");
	}
	
	public Category getCategory(int id) {
		for (Category cat : categories) {
			if(cat.getCategoryId() == id) {
				return cat;
			}
		}
		return null;
	}
	
	public boolean deleteCategory(int id) {
		Category toRemove = this.getCategory(id);
		if(toRemove == null)
			return false;
		this.categories.remove(toRemove);
		return true;
	}
	
	public void removeCategory(int id) {
		for (int i = 0; i < categories.size(); i++) {
			if(categories.get(i).getCategoryId() == id) {
				categories.remove(i);
				return;
			}
		}
	}
	
	public List<Category> getCategories(){
		return categories;
	}
	
	public void createCategory(int id, String name, String identifier, String description) {
		Category cat = new Category(id, name, identifier, description);
		this.categories.add(cat);
		return;
	}
	
	public boolean updateCategory(int id, String name, String identifier, String description) {
		this.removeCategory(id);
		Category cat = new Category(id, name, identifier, description);
		categories.add(cat);
		return true;
	}

	
}
