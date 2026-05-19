package ni.edu.uam.ejemplo_api.controllers;

import ni.edu.uam.ejemplo_api.models.Estudiante;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/estudiante")

public class EstudianteController {
    @PostMapping
    public Map<String, String>EvaluaNota(@RequestBody Estudiante est)
    {
        Map<String, String> respuesta = new HashMap<>();
        respuesta.put("Estudiante", est.getNombre());
        respuesta.put("Asignatura", est.getAsignatura());
        respuesta.put("Nota Final", String.valueOf(getNotaFinal(est.getCorte1(), est.getCorte2(), est.getCorte3())));
        return respuesta;
    }

    private int getNotaFinal(int nota1, int nota2, int nota3)
    {
        return (int) (nota1 + nota2 + nota3) / 3;
    }
}
