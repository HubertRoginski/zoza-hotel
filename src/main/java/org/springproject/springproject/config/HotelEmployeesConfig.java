package org.springproject.springproject.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@ConfigurationProperties(prefix = "hotel.employee.minions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotelEmployeesConfig {

    private List<String> names;
    private Map<String, String > people;
}
