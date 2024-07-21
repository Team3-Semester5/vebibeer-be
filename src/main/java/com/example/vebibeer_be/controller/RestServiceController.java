// package com.example.vebibeer_be.controller;

// import java.util.List;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;

// import com.example.vebibeer_be.model.entities.BusCompany.Service;
// import com.example.vebibeer_be.model.service.BusCompanyService.SerService;

// @RestController
// @RequestMapping("/admin/service")
// public class RestServiceController {
//     @Autowired
//     private SerService servicesService;

//  
//     public ResponseEntity<List<Service>> showList() {
//         List<Service> services = servicesService.getAll();
//         return services.isEmpty() ?
//             new ResponseEntity<>(HttpStatus.NO_CONTENT) :
//             new ResponseEntity<>(services, HttpStatus.OK);
//     }

//     @PostMapping(value = {"/save", "/save/"})
//     public ResponseEntity<Service> save(@RequestBody Service service) {
//         if (service == null) {
//             return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//         }
//         Service existingService = servicesService.getById(service.getService_id());
//         servicesService.save(service);
//         return existingService == null ?
//             new ResponseEntity<>(HttpStatus.CREATED) :
//             new ResponseEntity<>(HttpStatus.OK);
//     }

//     @GetMapping(value = {"/{id}", "/{id}/"})
//     public ResponseEntity<Service> getById(@PathVariable int service_id) {
//         Service service = servicesService.getById(service_id);
//         return service == null ?
//             new ResponseEntity<>(HttpStatus.NOT_FOUND) :
//             new ResponseEntity<>(service, HttpStatus.OK);
//     }

//     @DeleteMapping(value = {"/delete/{id}", "/delete/{id}/"})
//     public ResponseEntity<HttpStatus> delete(@PathVariable int service_id){
//         servicesService.delete(service_id);
//         return new ResponseEntity<>(HttpStatus.OK);
//     }
// }


package com.example.vebibeer_be.controller;


import com.example.vebibeer_be.model.entities.BusCompany.Service;
import com.example.vebibeer_be.model.service.BusCompanyService.ServiceService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admin/service")
public class RestServiceController {

    @Autowired
    private ServiceService serviceService;

    @PostMapping(value = {"/save", "/save/"})
    public Service createService(@RequestBody Service service) {
        return serviceService.createService(service);
    }

    @GetMapping(value = {"", "/"})
    public List<Service> getAllServices() {
        return serviceService.getAllServices();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Service> getServiceById(@PathVariable int id) {
        Optional<Service> service = serviceService.getServiceById(id);
        if (service.isPresent()) {
            return ResponseEntity.ok(service.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Service> updateService(@PathVariable int id, @RequestBody Service serviceDetails) {
        try {
            Service updatedService = serviceService.updateService(id, serviceDetails);
            return ResponseEntity.ok(updatedService);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(value = {"/delete/{id}", "/delete/{id}/"})
    public ResponseEntity<Void> deleteService(@PathVariable int id) {
        serviceService.deleteService(id);
        return ResponseEntity.noContent().build();
    }
}

