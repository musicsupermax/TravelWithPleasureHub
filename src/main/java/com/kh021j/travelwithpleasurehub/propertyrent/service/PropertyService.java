package com.kh021j.travelwithpleasurehub.propertyrent.service;

import com.kh021j.travelwithpleasurehub.controller.enumeration.SortType;
import com.kh021j.travelwithpleasurehub.propertyrent.model.Property;
import com.kh021j.travelwithpleasurehub.propertyrent.model.PropertyImage;
import com.kh021j.travelwithpleasurehub.propertyrent.repository.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class PropertyService {

    @Autowired
    private PropertyRepository propertyRepository;

    @Autowired
    private PropertyTypeService propertyTypeService;

    @Autowired
    private ImgurAPIService imgurAPIService;

    @Autowired
    private HEREMapsGeocoderAPIService hereMapsGeocoderAPIService;

    @Autowired
    private PropertyImageService propertyImageService;


    public List<Property> findAll(){
        return propertyRepository.findAll();
    }

    public Property add(Property property, MultipartFile[] photos) {
        String fullAddress = property.getAddress() + " " + property.getLocality();
        List<Double> coordinates = hereMapsGeocoderAPIService.getCoordinatesByAddress(fullAddress);

        property.setPropertyType(propertyTypeService.findById(1));
        property.setLatitude(coordinates.get(0));
        property.setLongitude(coordinates.get(1));

        Property savedProperty = propertyRepository.save(property);
        for(MultipartFile photo : photos) {
            propertyImageService.add(new PropertyImage(imgurAPIService.uploadPictures(photo), savedProperty));
        }

        return savedProperty;
    }

    public Property update(Property property) {
        if(propertyRepository.findById(property.getId()).isPresent())
            return propertyRepository.save(property);
        else return null;
    }

    public void delete(Property property) {
        propertyRepository.delete(property);
    }

    public Property findById(Integer id) {
        return propertyRepository.findById(id).orElse(new Property());
    }

    public List<Property> findBy5kmRadius(Double latitude, Double longitude) {
        List<Property> allProperties = findAll();
        List<Property> in5kmRadius = new ArrayList<>();

        for(Property property : allProperties) {
            if(checkIfIn5kmRadius(latitude, longitude, property.getLatitude(), property.getLongitude()))
                in5kmRadius.add(property);
        }
        return in5kmRadius;
    }

    private boolean checkIfIn5kmRadius(Double centerLatitude, Double centerLongitude,
                                       Double givenLatitude, Double givenLongitude
    ) {
        Double ky = 40000D / 360D;
        Double kx = Math.cos(Math.PI * centerLatitude / 180.0) * ky;
        Double dx = Math.abs(centerLongitude - givenLongitude) * kx;
        Double dy = Math.abs(centerLatitude - givenLatitude) * ky;

        return Math.sqrt(dx * dx + dy * dy) <= 5;
    }

    public List<Property> findByPriceLessThanEqual(Integer price) {
        return propertyRepository.findByPriceLessThanEqual(price).orElse(null);
    }

    public List<Property> findAllByOrderByPrice(String sortByPrice) {
        switch (SortType.valueOf(sortByPrice.toUpperCase())) {
            case ASC:
                return propertyRepository.findAllByOrderByPriceAsc().orElse(new ArrayList<>());
            case DESC:
                return propertyRepository.findAllByOrderByPriceDesc().orElse(new ArrayList<>());
            default:
                return propertyRepository.findAll();
        }
    }

    public Iterable<Property> filterProperties(String locality, String address, String checkIn, String checkOut) {

        if(!locality.equals("") && address.equals("") && checkIn.equals("") && checkOut.equals("")) {
            return propertyRepository.findByLocality(locality).orElse(new ArrayList<>());
        } else if(locality.equals("") && !address.equals("") && checkIn.equals("") && checkOut.equals("")) {
            return propertyRepository.findByAddressContaining(address).orElse(new ArrayList<>());
        } else if(!locality.equals("") && !address.equals("") && checkIn.equals("") && checkOut.equals("")) {
            return propertyRepository.findByLocalityContainingAndAddressContaining(locality, address).orElse(new ArrayList<>());
        } else if(!locality.equals("") && address.equals("") && !checkIn.equals("") && !checkOut.equals("")) {
            LocalDate checkInDate = LocalDate.parse(checkIn);
            LocalDate checkOutDate = LocalDate.parse(checkOut);
            return propertyRepository.findByAvailabilityInPeriodAndLocality(checkInDate, checkOutDate, locality)
                    .orElse(new ArrayList<>());
        } else if(!locality.equals("") && !address.equals("") && !checkIn.equals("") && !checkOut.equals("")) {
            LocalDate checkInDate = LocalDate.parse(checkIn);
            LocalDate checkOutDate = LocalDate.parse(checkOut);
            if(checkOutDate.isAfter(checkInDate))
                return propertyRepository.findByAvailabilityInPeriodAndLocalityAndAddress(
                        checkInDate, checkOutDate, locality, address).orElse(new ArrayList<>());
            else return new ArrayList<>();
        }
        return propertyRepository.findAll();
    }


    public List<Property> findByAvailabilityInPeriod(String since, String until) {
        LocalDate sinceDate = LocalDate.parse(since);
        LocalDate untilDate = LocalDate.parse(until);
        return propertyRepository.
                findByAvailabilityInPeriod(sinceDate, untilDate).orElse(new ArrayList<>());
    }

    public List<Property> findByAvailabilityInPeriodAndSort(String since, String until, String sortByPrice) {
        LocalDate sinceDate = LocalDate.parse(since);
        LocalDate untilDate = LocalDate.parse(until);
        switch (SortType.valueOf(sortByPrice.toUpperCase())) {
            case ASC:
                return propertyRepository.findByAvailabilityInPeriodAndSort(
                        sinceDate, untilDate, SortType.ASC.name()).orElse(new ArrayList<>());
            case DESC:
                return propertyRepository.findByAvailabilityInPeriodAndSort(
                        sinceDate, untilDate, SortType.DESC.name()).orElse(new ArrayList<>());
            default:
                return propertyRepository.
                        findByAvailabilityInPeriod(sinceDate, untilDate)
                        .orElse(new ArrayList<>());
        }
    }

}