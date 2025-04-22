package dev.rikthipranadhik.storemanagementsystembackend.mapper.employee;

import dev.rikthipranadhik.storemanagementsystembackend.dto.employee.ManagerDTO;
import dev.rikthipranadhik.storemanagementsystembackend.entity.employee.Manager;

public interface ManagerMapper {
    Manager fromDTO(ManagerDTO managerDTO);
    ManagerDTO toDTO(Manager manager);
}
