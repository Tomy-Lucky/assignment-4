package com.example.assignment.Tamir.repository

import com.example.assignment.Tamir.model.EmployeeModel
import com.example.assignment.Tamir.model.WorkerType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface EmployeeRepository: JpaRepository<EmployeeModel, Long> {

    fun findAllByType(type: WorkerType): List<EmployeeModel>
}
