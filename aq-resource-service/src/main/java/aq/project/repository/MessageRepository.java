package aq.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import aq.project.entity.Message;

public interface MessageRepository extends JpaRepository<Message, Integer> {

}
