package dao;

import models.HuespedDTO;
import java.io.*;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class FileHuespedDAO implements HuespedDAO {
    private final Path archivo = Paths.get("data/ListaHuespedes.csv");

    @Override
    public List<HuespedDTO> listar() {
        try {
            return Files.lines(archivo)
                    .map(String::trim)
                    .filter(line -> !line.isEmpty())
                    .map(this::parseHuesped)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException("Error leyendo archivo de huéspedes", e);
        }
    }

    private HuespedDTO parseHuesped(String linea) {
        String[] p = linea.split(",", -1);
        return new HuespedDTO(
            p[0], p[1], p[2], Integer.parseInt(p[3]), p[4],
            Long.parseLong(p[5]), Long.parseLong(p[6]),
            LocalDate.parse(p[7]), p[8], p[9], p[10],
            p.length > 11 && p[11].trim().equals("1") 
        );
    }

    @Override
    public Optional<HuespedDTO> buscarPorDocumento(String tipo, int numero) {
        return listar().stream()
                .filter(h -> h.getTipoDeDocumento().equalsIgnoreCase(tipo)
                        && h.getNumeroDeDocumento() == numero)
                .findFirst();
    }

    @Override
    public void eliminar(HuespedDTO huesped) {
        List<HuespedDTO> todos = listar().stream()
                .filter(h -> !(h.getTipoDeDocumento().equalsIgnoreCase(huesped.getTipoDeDocumento())
                        && h.getNumeroDeDocumento() == huesped.getNumeroDeDocumento()))
                .collect(Collectors.toList());
        try (BufferedWriter writer = Files.newBufferedWriter(archivo)) {
            for (HuespedDTO h : todos) {
                writer.write(h.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Error al eliminar huésped del archivo", e);
        }
    }
}
