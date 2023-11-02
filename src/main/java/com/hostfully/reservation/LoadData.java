package com.hostfully.reservation;

import com.hostfully.reservation.entity.Property;
import com.hostfully.reservation.repository.PropertyRepository;

import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
@AllArgsConstructor
public class LoadData  implements CommandLineRunner {

        private final PropertyRepository propertyRepository;


        @Override
        public void run(String... args) {
            if (propertyRepository.count() == 0) {
                List<Property> properties = createProperties();
                propertyRepository.saveAll(properties);
            }
        }

        private List<Property> createProperties() {
            List<Property> properties = new ArrayList<>();

            for (int i = 1; i <= 10; i++) {
                Property property = new Property();
                property.setName("Property " + i);
                property.setAddress("Address " + i);
                property.setDescription("Description for Property " + i);

                properties.add(property);
            }

            return properties;
        }


}
