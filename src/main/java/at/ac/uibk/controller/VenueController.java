package at.ac.uibk.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import at.ac.uibk.model.Navigation;
import at.ac.uibk.model.Revenue;
import at.ac.uibk.service.RevenueService;

@RestController
public class RevenueController {

	@Autowired
	private RevenueService revenueService;

	private void addStandardNavigation(Navigation navi) {
		navi.add(linkTo(methodOn(RevenueController.class).createRevenue("name", "country", "city", "address", "size"))
				.withSelfRel());
	}

	@RequestMapping("/revenues")
	public HttpEntity<Navigation> getRevenues() {

		Navigation navi = new Navigation("Operations for Revenues");

		addStandardNavigation(navi);

		List<Revenue> revenues = revenueService.getRevenues();
		if (revenues != null) {
			for (Revenue revenue : revenues) {
				navi.add(linkTo(methodOn(RevenueController.class).getRevenue(revenue.getRevenueId())).withSelfRel());
			}
		}
		return new ResponseEntity<>(navi, HttpStatus.OK);
	}

	@RequestMapping("/revenue/{id}")
	public HttpEntity<Revenue> getRevenue(@PathVariable("id") int id) {

		Revenue revenue = revenueService.getRevenue(id);
		revenue.add(linkTo(methodOn(RevenueController.class).getRevenues()).withSelfRel());
		revenue.add(
				linkTo(methodOn(RevenueController.class).createRevenue("name", "country", "city", "address", "size"))
						.withSelfRel());

		return new ResponseEntity<>(revenue, HttpStatus.OK);
	}

	@RequestMapping("/revenue/new")
	public HttpEntity<Navigation> createRevenue(
			@RequestParam(value = "name", required = true, defaultValue = "") String name,
			@RequestParam(value = "country", required = true, defaultValue = "") String country,
			@RequestParam(value = "city", required = true, defaultValue = "") String city,
			@RequestParam(value = "address", required = true, defaultValue = "") String address,
			@RequestParam(value = "size", required = true, defaultValue = "") String size) {

		Navigation navi = new Navigation();
		int id = revenueService.createRevenue(name, country, city, address, size);
		if (-1 < id) {
			navi.setContent("Revenue created");
			addStandardNavigation(navi);
			navi.add(linkTo(methodOn(RevenueController.class).getRevenue(id)).withSelfRel());
			navi.add(linkTo(methodOn(RevenueController.class).getRevenues()).withSelfRel());

			return new ResponseEntity<>(navi, HttpStatus.OK);
		} else {
			navi.setContent("Failed to create Revenue");
			addStandardNavigation(navi);
			navi.add(linkTo(methodOn(RevenueController.class).getRevenues()).withSelfRel());

			return new ResponseEntity<>(navi, HttpStatus.EXPECTATION_FAILED);
		}
	}

}
