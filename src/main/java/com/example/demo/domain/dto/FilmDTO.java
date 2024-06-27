package com.example.demo.domain.dto;

// Ajoutez les champs et méthodes nécessaires pour le DTO
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class FilmDTO {
    private Long id;

    private String title;
    private String director;
    private String date;
    private String  time;
}

