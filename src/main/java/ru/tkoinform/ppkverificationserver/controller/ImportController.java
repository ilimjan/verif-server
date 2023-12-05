package ru.tkoinform.ppkverificationserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.tkoinform.ppkverificationserver.CrossOrigins;
import ru.tkoinform.ppkverificationserver.service.ExportService;

@RestController
@RequestMapping("/api/import")
@CrossOrigin(origins = CrossOrigins.value)
public class ImportController {

    @Autowired
    private ExportService exportService;

    @PostMapping(value = "/excel")
    @ResponseBody
    public void importExcelFile(@RequestParam final MultipartFile file, @AuthenticationPrincipal Jwt jwt) {
        exportService.importExcel(file, jwt.getClaimAsString("region_id"));
    }
}
