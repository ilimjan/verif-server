package ru.tkoinform.ppkverificationserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.tkoinform.ppkverificationserver.CrossOrigins;
import ru.tkoinform.ppkverificationserver.dto.FiasAddressDto;
import ru.tkoinform.ppkverificationserver.dto.FnsDto;
import ru.tkoinform.ppkverificationserver.service.FiasService;

import java.util.List;

@RestController
@RequestMapping(FiasController.FIAS_CONTROLLER_PATH)
@CrossOrigin(origins = CrossOrigins.value)
public class FiasController {

    public static final String FIAS_CONTROLLER_PATH = "/api/fias";

    @Autowired
    private FiasService fiasService;

    @GetMapping("/search")
    //@PreAuthorize("hasRole('ROLE_fs_monitoring')")
    public ResponseEntity<List<FiasAddressDto>> fiasSearch(
            @AuthenticationPrincipal Jwt jwt,
            @RequestParam(name = "query") String query
    ) {
        if (!StringUtils.isEmpty(query)) {
            return ResponseEntity.ok(fiasService.findAddressesByQuery(query));
        } else {
            throw new IllegalArgumentException("Передана пустая строка!");
        }
    }

    @GetMapping("/geolocate")
    //@PreAuthorize("hasRole('ROLE_fs_monitoring')")
    public ResponseEntity<List<FiasAddressDto>> fiasGeolocate(
            @AuthenticationPrincipal Jwt jwt,
            @RequestParam(name = "latitude") Float latitude,
            @RequestParam(name = "longitude") Float longitude
    ) {
        if (latitude != null && longitude != null) {
            return ResponseEntity.ok(fiasService.findAddressesByGeo(latitude, longitude));
        } else {
            throw new IllegalArgumentException("Не переданы координаты");
        }
    }

    @GetMapping("/inn")
    //@PreAuthorize("hasRole('ROLE_fs_monitoring')")
    public ResponseEntity<FnsDto> fnsByInn(
            @AuthenticationPrincipal Jwt jwt,
            @RequestParam(name = "inn") String inn
    ) {
        if (!StringUtils.isEmpty(inn)) {
            return ResponseEntity.ok(fiasService.findFnsEntityByTin(inn));
        } else {
            throw new IllegalArgumentException("Пустой ИНН");
        }
    }
}
