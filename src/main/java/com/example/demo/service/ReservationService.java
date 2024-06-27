package com.example.demo.service;

import org.springframework.stereotype.Service;
import com.example.demo.domain.model.Reservation;
import com.example.demo.domain.dto.ReservationDTO;
import com.example.demo.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.Optional;
import org.modelmapper.ModelMapper;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private ModelMapper modelMapper;

    public ReservationDTO createReservation(Reservation reservation) {
        Reservation createdReservation = reservationRepository.save(reservation);
        return modelMapper.map(createdReservation, ReservationDTO.class);
    }

    public List<ReservationDTO> getAllReservations() {
        List<Reservation> allReservations = reservationRepository.findAll();
        return convertListToDTOs(allReservations);
    }

    public Optional<ReservationDTO> getReservationById(Long id) {
        Optional<Reservation> entity = reservationRepository.findById(id);
        return entity.map(e -> modelMapper.map(e, ReservationDTO.class));
    }

    public ReservationDTO updateReservation(Long id, Reservation reservation) {
        if (!reservationRepository.existsById(id)) {
            return null; // Gérer le cas où l'entité n'existe pas
        }
        Reservation updatedReservation = reservationRepository.save(reservation);
        return modelMapper.map(updatedReservation, ReservationDTO.class);
    }

    public void deleteReservation(Long id) {
        reservationRepository.deleteById(id);
    }

    private List<ReservationDTO> convertListToDTOs(List<Reservation> entities) {
        return entities.stream()
                .map(e -> modelMapper.map(e, ReservationDTO.class))
                .toList();
    }
}

