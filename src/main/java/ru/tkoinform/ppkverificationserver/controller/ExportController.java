package ru.tkoinform.ppkverificationserver.controller;

import org.apache.poi.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ru.tkoinform.ppkverificationserver.CrossOrigins;
import ru.tkoinform.ppkverificationserver.service.ExportService;
import ru.tkoinform.ppkverificationserver.service.PpkService;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@RestController
@RequestMapping("/api/export")
@CrossOrigin(origins = CrossOrigins.value)
public class ExportController {

    @Autowired
    private ExportService exportService;

    @Autowired
    private PpkService ppkService;

    //@PreAuthorize("hasAnyRole(T(Role).ADMIN)")
    @PostMapping(value = "/excel/{infrastructureObjectId}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @ResponseBody
    public byte[] exportExcelFile(@PathVariable(name = "infrastructureObjectId") UUID infrastructureObjectId, HttpServletRequest request) {
        File excel = null;
        try {
            excel = exportService.exportExcel(infrastructureObjectId, request.getHeader("origin"));
            InputStream in = new FileInputStream(excel);
            byte[] result = IOUtils.toByteArray(in);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (excel != null) {
                excel.delete();
            }
        }

        return null;
    }

    @PostMapping(value = "/excel/{infrastructureObjectId}/mail")
    @ResponseBody
    public void exportExcelFile(@PathVariable(name = "infrastructureObjectId") UUID infrastructureObjectId,
                                @RequestParam(name = "email") String email,
                                @AuthenticationPrincipal Jwt jwt,
                                HttpServletRequest request) {
        File excel = null;
        try {
            excel = exportService.exportExcel(infrastructureObjectId, request.getHeader("origin"));
            ppkService.sendEmail(email, "Экспорт из модуля верификации", "Файл во вложении", excel, jwt.getTokenValue());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (excel != null) {
                excel.delete();
            }
        }
    }
}
