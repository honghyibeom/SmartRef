package com.hong.smartref.controller;

import com.hong.smartref.service.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/fridge")
public class StorageController {
    private final StorageService storageService;


}
