package ru.kmatrokhin.betoolatest.cultivation.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CultivationRepository extends JpaRepository<UUID, Cultivation> {
}
