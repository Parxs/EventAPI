package at.ac.uibk.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.ac.uibk.model.Revenue;
import at.ac.uibk.repository.RevenueRepository;

@Service("revenueService")
public class RevenueService {
	@Autowired
	private RevenueRepository revenueRepository;

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