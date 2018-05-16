package at.ac.uibk.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import at.ac.uibk.model.Revenue;

// TODO use real data and db
@Service("revenueRepository")
public class RevenueRepository {

	ArrayList<Revenue> revenues = new ArrayList<>();

	// TODO threadsafe solution
	private int currentId = 100;

	public Revenue getRevenue(int id) {
		for (Revenue revenue : revenues) {
			if (revenue.getRevenueId() == id) {
				return revenue;
			}
		}

		if (id < 100) {
			Revenue artist = new Revenue(id, "name" + id, "country" + id, "city" + id, "address" + id,
					"" + id * 200 + " Personen");
			revenues.add(artist);
			return artist;
		}
		return null;
	}

	public List<Revenue> getRevenues() {
		return revenues;
	}

	public int createRevenue(String name, String country, String city, String address, String size) {
		Revenue revenue = new Revenue(currentId, name, country, city, address, size);
		revenues.add(revenue);
		currentId++;
		return currentId - 1;
	}
}
