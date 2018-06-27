package at.ac.uibk.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import at.ac.uibk.model.Category;
import at.ac.uibk.model.Navigation;
import at.ac.uibk.model.SchemaResponse;
import at.ac.uibk.service.CategoryService;

@RestController
public class CategoryController {
	@Autowired
	private CategoryService categoryService;

	private void addStandardNavigation(ResourceSupport navi) {
		navi.add(linkTo(methodOn(CategoryController.class).getCategories()).withRel("categories"));
		navi.add(linkTo(methodOn(CategoryController.class).createCategory(1, "name", "identifier", "description"))
				.withRel("create"));
	}

	@RequestMapping(value = "/categories/{id}", method = RequestMethod.GET)
	public HttpEntity<ResourceSupport> getCategory(@PathVariable("id") int id) {

		Category cat = categoryService.getCategory(id);
		if (cat == null) {
			Navigation categoryError = new Navigation("Category not found");
			addStandardNavigation(categoryError);

			return new ResponseEntity<>(categoryError, HttpStatus.NOT_FOUND);
		}
		SchemaResponse res = new SchemaResponse("ViewAction");
		res.SetResult("Category", cat);
		res.add(linkTo(methodOn(CategoryController.class).getCategories()).withRel("categories"));
		res.add(linkTo(methodOn(CategoryController.class).getCategory(id)).withSelfRel());

		return new ResponseEntity<ResourceSupport>(res, HttpStatus.OK);
	}

	@RequestMapping(value = "/categories", method = RequestMethod.GET)
	public HttpEntity<ResourceSupport> getCategories() {
		Navigation navi = new Navigation("Operations for Categories");
		List<Category> categories = categoryService.getCategories();

		navi.add(linkTo(methodOn(CategoryController.class).getCategories()).withSelfRel());
		navi.add(linkTo(methodOn(CategoryController.class).createCategory(1, "name", "identifier", "description"))
				.withRel("create"));
		if (categories != null) {
			for (Category cat : categories) {
				navi.add(linkTo(methodOn(CategoryController.class).getCategory(cat.getCategoryId()))
						.withRel("category"));
			}
		}
		return new ResponseEntity<ResourceSupport>(navi, HttpStatus.OK);
	}

	@RequestMapping(value = "/categories", method = RequestMethod.POST)
	public HttpEntity<ResourceSupport> createCategory(
			@RequestParam(value = "id", required = true, defaultValue = "1") int id,
			@RequestParam(value = "name", required = true, defaultValue = "") String name,
			@RequestParam(value = "identifier", required = true, defaultValue = "") String identifier,
			@RequestParam(value = "description", required = true, defaultValue = "") String description) {

		SchemaResponse res = new SchemaResponse("CreateAction");
		categoryService.createCategory(id, name, identifier, description);
		res.SetResult("Category", categoryService.getCategory(id));
		addStandardNavigation(res);
		res.add(linkTo(methodOn(CategoryController.class).getCategory(id)).withSelfRel());

		return new ResponseEntity<>(res, HttpStatus.OK);

	}

	@RequestMapping(value = "/categories/{id}", method = RequestMethod.DELETE)
	public HttpEntity<ResourceSupport> deleteCategories(@PathVariable("id") int id) {
		Category cat = categoryService.getCategory(id);
		boolean ok = categoryService.deleteCategory(id);
		if (!ok) {
			Navigation navi = new Navigation("Category " + id + " not found");
			addStandardNavigation(navi);
			return new ResponseEntity<>(navi, HttpStatus.NOT_FOUND);

		} else {
			SchemaResponse res = new SchemaResponse("DeleteAction");
			addStandardNavigation(res);
			res.SetResult("Category", cat);

			return new ResponseEntity<>(res, HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/categories/{id}", method = RequestMethod.PUT)
	public HttpEntity<ResourceSupport> updateCategories(@PathVariable("id") int id,
			@RequestParam(value = "name", required = true, defaultValue = "") String name,
			@RequestParam(value = "identifier", required = true, defaultValue = "") String identifier,
			@RequestParam(value = "description", required = true, defaultValue = "") String description) {

		boolean ok = categoryService.updateCategory(id, name, identifier, description);
		if (ok) {
			SchemaResponse res = new SchemaResponse("UpdateAction");
			res.SetResult("Category", categoryService.getCategory(id));
			addStandardNavigation(res);
			res.add(linkTo(methodOn(CategoryController.class).getCategory(id)).withSelfRel());

			return new ResponseEntity<>(res, HttpStatus.OK);
		} else {
			Navigation navi = new Navigation();

			navi.setContent("Failed to updated Category");
			addStandardNavigation(navi);

			return new ResponseEntity<>(navi, HttpStatus.EXPECTATION_FAILED);
		}
	}

}
