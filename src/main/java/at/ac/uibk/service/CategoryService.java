package at.ac.uibk.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.ac.uibk.model.Category;
import at.ac.uibk.repository.CategoryRepository;

@Service("categoryService")
public class CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;
	
	public CategoryService() {
		
	}
	
	public Category getCategory(int id) {
		return categoryRepository.getCategory(id);
	}
	
	public boolean deleteCategory(int id) {
		return categoryRepository.deleteCategory(id);
	}
	
	public List<Category> getCategories() {
		return categoryRepository.getCategories();
	}
	
	public void createCategory(int id, String name, String identifier, String description) {
		categoryRepository.createCategory(id, name, identifier, description);
	}
	
	public boolean updateCategory(int id, String name, String identifier, String description) {
		return categoryRepository.updateCategory(id, name, identifier, description);
	}
	
}
