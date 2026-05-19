package ni.edu.uam.docenteuam.modelos;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "carreras")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Carrera {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_carreras")
    private Long id;

    @Column(name = "nombre_carrera", nullable = false, length = 100)
    private String nombre;

    @Column(name = "descripcion_carrera", length = 250)
    private String descripcion;
}
