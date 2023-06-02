package com.senac.springjwt.controllers;

import com.google.api.services.drive.model.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("api/files")
public class FileController {

    @Autowired
    private GoogleDriveService googleDriveService; // Classe de acesso à API do Google Drive

    @PostMapping
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            File uploadedFile = googleDriveService.createFile(file.getOriginalFilename(), file.getBytes());
            return ResponseEntity.ok(uploadedFile.getId());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao fazer o upload do arquivo");
        }
    }

    @GetMapping("/{fileId}")
    public void downloadFile(@PathVariable String fileId, HttpServletResponse response) throws IOException {
        byte[] fileContent = googleDriveService.getFileContent(fileId);
        String filename = fileId;
        String disposition = "attachment; filename=\"" + filename + "\"";
        response.setHeader("Content-Disposition", disposition);
        response.setContentType("application/octet-stream");
        response.getOutputStream().write(fileContent);
        response.getOutputStream().flush();
    }

    @DeleteMapping("/{fileId}")
    public ResponseEntity<String> deleteFile(@PathVariable String fileId) {
        try {
            // Exclui o arquivo do Google Drive
            googleDriveService.deleteFile(fileId);

            return ResponseEntity.ok("Arquivo excluído com sucesso");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao excluir o arquivo");
        }
    }
}