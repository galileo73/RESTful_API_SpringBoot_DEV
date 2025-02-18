package com.ired.prototype.restfulAPI.controller;

import com.ired.prototype.restfulAPI.model.*;
import com.ired.prototype.restfulAPI.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CommandExecutionController {
    private final CommandExecutionService commandExecutionService;

    @PostMapping("/execute")
    public String executeCommand(@RequestParam String command) {
        return commandExecutionService.executeCommand(command);
    }

    @GetMapping("/history")
    public List<CommandExecution> getCommandHistory() {
        return commandExecutionService.getExecutionHistory();
    }
}