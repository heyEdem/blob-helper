package com.edem.blobhelper.dashboard.registration;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/api/v1/instances")
public class InstanceRegistrationController {

    private final ConcurrentHashMap<java.util.UUID, InstanceRegistration> registrations = new ConcurrentHashMap<>();

    @PostMapping
    public InstanceRegistration register(@RequestBody InstanceRegistration registration) {
        InstanceRegistration updated = new InstanceRegistration(
                registration.instanceId(),
                registration.instanceName(),
                registration.advertisedUrl(),
                Instant.now());
        registrations.put(updated.instanceId(), updated);
        return updated;
    }

    @GetMapping
    public List<InstanceRegistration> instances() {
        return registrations.values().stream()
                .sorted(Comparator.comparing(InstanceRegistration::instanceName))
                .toList();
    }
}
