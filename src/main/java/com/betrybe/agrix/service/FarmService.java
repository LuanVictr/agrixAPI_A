package com.betrybe.agrix.service;

import com.betrybe.agrix.model.entities.Farm;
import com.betrybe.agrix.model.repositories.FarmRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Classe FarmService, camada de serviço dos endpoints /farm.
 */
@Service
public class FarmService {

  private FarmRepository farmRepository;

  /**
   * Funcao construtora da classe FarmService.
   *
   * @param farmRepository repositorio recebido do spring
   *                       por injeção de dependencia
   */
  @Autowired
  public FarmService(FarmRepository farmRepository) {
    this.farmRepository = farmRepository;
  }

  public List<Farm> getFarms() {
    List<Farm> allFarms = this.farmRepository.findAll();
    return allFarms;
  }

  public Farm createFarm(Farm newFarm) {
    Farm createdFarm = this.farmRepository.save(newFarm);
    return createdFarm;
  }

  public Farm getFarmById() {
    Farm
  }


}
