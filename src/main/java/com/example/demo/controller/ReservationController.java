package com.example.demo.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import java.util.List;
import com.example.demo.service.ReservationService;
import com.example.demo.domain.dto.ReservationDTO;
import com.example.demo.domain.model.Reservation;

/**
 * CONTROLLER RESERVATION - LOGIQUE CRUD
 *
 *  /reservations -> GET : list of reservations
 *  /reservations/{id} -> GET : get a reservation by ID
 *  /reservations -> POST : create a new reservation
 *  /reservations/{id} -> PUT : update a reservation by ID
 *  /reservations/{id} -> DELETE : delete a reservation by ID
 */
@RestController
@RequestMapping("/reservations")
@CrossOrigin(origins = "https://didactic-space-carnival-9vvjwgrpv6jcrw4-4200.app.github.dev")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    /**
     * POST
     * Créer une nouvelle entité.
     */
    @PostMapping
    public ResponseEntity<ReservationDTO> create(@RequestBody Reservation reservation) {
        ReservationDTO createdReservationDTO = reservationService.createReservation(reservation);
        return ResponseEntity.ok(createdReservationDTO);
    }

    /**
     * GET
     * Récupérer la liste de tous les reservations.
     */
    @GetMapping
    public ResponseEntity<List<ReservationDTO>> list() {
        List<ReservationDTO> allReservationDTOs = reservationService.getAllReservations();
        return ResponseEntity.ok(allReservationDTOs);
    }

    /**
     * GET
     * Récupérer une entité par ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ReservationDTO> get(@PathVariable Long id) {
        ReservationDTO foundReservationDTO = reservationService.getReservationById(id).orElse(null);
        return ResponseEntity.ok(foundReservationDTO);
    }

    /**
     * PUT
     * Mettre à jour une entité.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ReservationDTO> update(@PathVariable Long id, @RequestBody Reservation reservation) {
        ReservationDTO updatedReservationDTO = reservationService.updateReservation(id, reservation);
        return ResponseEntity.ok(updatedReservationDTO);
    }

    /**
     * DELETE
     * Supprimer une entité.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        reservationService.deleteReservation(id);
        return ResponseEntity.noContent().build();
    }
}

