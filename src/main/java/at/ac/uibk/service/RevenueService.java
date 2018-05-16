package at.ac.uibk.service;

import java.util.List;

import at.ac.uibk.model.Revenue;
import at.ac.uibk.repository.RevenueRepository;

public class RevenueService {
	private RevenueRepository revenueRepository;

	public RevenueService() {
		this.revenueRepository = new RevenueRepository();
	}

	public Revenue getRevenue(int id) {

		return revenueRepository.getRevenue(id);
	}

	public List<Revenue> getRevenues() {
		return revenueRepository.getRevenues();
	}

	public int createRevenue(String name, String country, String city, String address, String size) {
		return revenueRepository.createRevenue(name, country, city, address, size);
	}
}