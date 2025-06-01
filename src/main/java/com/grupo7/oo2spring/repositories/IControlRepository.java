package com.grupo7.oo2spring.repositories;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.grupo7.oo2spring.models.Control;

@Repository("controlRepository")
public interface IControlRepository extends JpaRepository<Control, Integer> {
	
	// Buscar controles por ticket
    List<Control> findByTicketIdTicket(int ticketId);
    
    // Buscar controles por empleado
    List<Control> findByEmpleadoIdEmpleado(int empleadoId);
	
 // Buscar controles finalizados o no finalizados
    List<Control> findByFinalizado(boolean finalizado);
	
    // Buscar controles con fecha de entrada entre dos fechas
    List<Control> findByFechaEntradaBetween(LocalDate inicio, LocalDate fin);
    
    // Buscar controles con fecha de salida entre dos fechas
    List<Control> findByFechaSalidaBetween(LocalDate inicio, LocalDate fin);
    
    // Buscar controles por empleado y finalizado
    List<Control> findByEmpleadoIdEmpleadoAndFinalizado(int empleadoId, boolean finalizado);
    
    // Buscar controles de un ticket que no estén finalizados (ej: en curso)
    List<Control> findByTicketIdTicketAndFinalizado(int ticketId, boolean finalizado);
    
	
	

}
