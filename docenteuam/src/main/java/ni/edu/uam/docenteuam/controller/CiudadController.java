package ni.edu.uam.docenteuam.controller;

import ni.edu.uam.docenteuam.models.Carrera;
import ni.edu.uam.docenteuam.service.CarreraService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/carreras")
public class CiudadController {
    private final CarreraService service;

    public CarreraController(CarreraService service) {
        this.service = service;
    }

    @GetMapping
    public List<Carrera> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Carrera findById(Long id) {
        return service.findById(id);
    }

    @PostMapping
    public Carrera save(Carrera carrera) {
        return service.save(carrera);
    }

    @PutMapping("/update/{id}")
    public Carrera update(Long id, Carrera carrera) {
        carrera.setId(id);
        return service.save(carrera);
    }

    @DeleteMapping("/{id}")
    public void delete(Long id) {
        service.delete(id);
    }

}
