package com.example.model.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.entity.Location;
import com.example.model.repo.LocationRepository;

@Service
public class LocationService {
    @Autowired
    private LocationRepository locationRepository;

    // Create a new Location
    public Location createLocation(Location location) {
        return locationRepository.save(location);
    }

    // Retrieve all Locations
    public List<Location> getAllLocations() {
        return locationRepository.findAll();
    }

    // Retrieve a Location by ID
    public Optional<Location> getLocationById(int id) {
        return locationRepository.findById(id);
    }

    // Update an existing Location
    public Location updateLocation(int id, Location locationDetails) {
        Optional<Location> optionalLocation = locationRepository.findById(id);
        if (optionalLocation.isPresent()) {
            Location existingLocation = optionalLocation.get();
            existingLocation.setLocation_name(locationDetails.getLocation_name());
            existingLocation.setLocation_logo(locationDetails.getLocation_logo());
            existingLocation.setLocation_description(locationDetails.getLocation_description());
            return locationRepository.save(existingLocation);
        } else {
            throw new RuntimeException("Location not found with id " + id);
        }
    }

    // Delete a Location by ID
    public void deleteLocation(int id) {
        locationRepository.deleteById(id);
    }
}

