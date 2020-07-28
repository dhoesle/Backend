package com.elton.watermyplants.Services;

import com.elton.watermyplants.Models.Location;
import com.elton.watermyplants.Models.Plant;
import com.elton.watermyplants.Models.PlantLocation;
import com.elton.watermyplants.Models.User;
import com.elton.watermyplants.Repos.LocationRepo;
import com.elton.watermyplants.Repos.PlantRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service(value = "plantService")
public class PlantServiceIMPL implements PlantService
{
    @Autowired
    private LocationRepo locationRepo;

    @Autowired
    private PlantService plantService;

    @Autowired
    private PlantRepo plantRepo;

    @Override
    public List<Plant> findAll()
    {
        List<Plant> list = new ArrayList<>();
        /*
         * findAll returns an iterator set.
         * iterate over the iterator set and add each element to an array list.
         */
        plantRepo.findAll()
                .iterator()
                .forEachRemaining(list::add);
        return list;
    }

    @Override
    public Plant findLocationById(long id)
    {
        return plantRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Plant id " + id + " not found!"));
    }

    @Override
    public void delete(long id)
    {
        plantRepo.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Product id " + id + " not found!"));
        plantRepo.deleteById(id);

    }

    @Override
    public Plant save(Plant plant)
    {

        Plant newPlant = new Plant();

        newPlant.setFrequency(plant.getFrequency());
        newPlant.setNickname(plant.getNickname());
        newPlant.setPlantLocations(plant.getPlantLocations());
        newPlant.setSpecies(plant.getSpecies());

        newPlant.getPlantLocations()
                .clear();
        for (PlantLocation p : plant.getPlantLocations())
        {
            newPlant.getPlantLocations()
                    .add(new PlantLocation( p.getLocation(),p.getPlant()));
        }


        return plantRepo.save(newPlant);
    }
}
