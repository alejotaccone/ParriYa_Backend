package com.parriya.parriya_api.entidades.dto.Reserva;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.parriya.parriya_api.entidades.Reserva;
import com.parriya.parriya_api.entidades.Usuario;

import lombok.Data;

@Data
public class ReservaDelDiaResponse {
    private int totalReservas;
    private int totalPersonas;

    private List<Reserva> turnoTarde = new ArrayList<>();
    private List<Reserva> turnoNoche = new ArrayList<>();
}
