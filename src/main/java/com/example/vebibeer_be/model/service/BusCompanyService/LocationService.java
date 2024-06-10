package com.example.vebibeer_be.model.service.BusCompanyService;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.vebibeer_be.model.entities.BusCompany.Location;
import com.example.vebibeer_be.model.repo.BusCompanyRepo.LocationRepo;


@Service
public class LocationService {
    @Autowired
    private LocationRepo locationRepo;

    // Create a new Location
    public Location createLocation(Location location) {
        return locationRepo.save(location);
    }

    // Retrieve all Locations
    public List<Location> getAllLocations() {
        return locationRepo.findAll();
    }

    // Retrieve a Location by ID
    public Optional<Location> getLocationById(int id) {
        return locationRepo.findById(id);
    }

    // Update an existing Location
    public Location updateLocation(int id, Location locationDetails) {
        Optional<Location> optionalLocation = locationRepo.findById(id);
        if (optionalLocation.isPresent()) {
            Location existingLocation = optionalLocation.get();
            existingLocation.setLocation_name(locationDetails.getLocation_name());
            existingLocation.setLocation_imgUrl(locationDetails.getLocation_imgUrl());
            existingLocation.setLocation_description(locationDetails.getLocation_description());
            return locationRepo.save(existingLocation);
        } else {
            throw new RuntimeException("Location not found with id " + id);
        }
    }

    // Delete a Location by ID
    public void deleteLocation(int id) {
        locationRepo.deleteById(id);
    }
}
