package com.ired.prototype.restfulAPI.service;

import com.ired.prototype.restfulAPI.model.CommandExecution;
import com.ired.prototype.restfulAPI.repository.CommandExecutionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommandExecutionService {
    private final CommandExecutionRepository repository;

    public String executeCommand(String command) {
        StringBuilder output = new StringBuilder();
        try {
            // Dynamically adjust the command for the current OS
            String[] cmdArray = getCommandArray(command);

            Process process = Runtime.getRuntime().exec(cmdArray);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }

            // Save the executed command and its output to the database
            CommandExecution execution = CommandExecution.builder()
                    .command(command)
                    .output(output.toString())
                    .executedAt(LocalDateTime.now())
                    .build();
            repository.save(execution);

            return output.toString();
        } catch (Exception e) {
            return "Error executing command: " + e.getMessage();
        }
    }

    public List<CommandExecution> getExecutionHistory() {
        return repository.findAll();
    }

    /**
     * Adjust the command based on the operating system.
     */
    private String[] getCommandArray(String command) {
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("win")) { // Windows
            return new String[] { "cmd.exe", "/c", command };
        } else { // Unix/Linux/macOS
            return new String[] { "/bin/sh", "-c", command };
        }
    }
}