package com.example.assignment.Tamir.service

import com.example.assignment.Tamir.dto.Employee
import com.example.assignment.Tamir.dto.TotalSalaryPayments
import com.example.assignment.Tamir.exception.EmployeeNotFoundByIdException
import com.example.assignment.Tamir.exception.throwMissingFieldException
import com.example.assignment.Tamir.model.EmployeeModel
import com.example.assignment.Tamir.model.PaymentModel
import com.example.assignment.Tamir.model.WorkerType
import com.example.assignment.Tamir.repository.EmployeeRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class EmployeeService(
    private val employeeRepository: EmployeeRepository
) {

    @Transactional
    fun addEmployee(employee: Employee): Employee {
        when (employee.type) {
            WorkerType.FIXED_SALARY ->
                if (employee.payment.monthPayment == null) throwMissingFieldException("monthPayment")
            WorkerType.HOURLY_PAYMENTS -> {
                if (employee.payment.workedHours == null) throwMissingFieldException("workedHours")
                if (employee.payment.hourPayment == null) throwMissingFieldException("hourPayment")
                if (employee.payment.overWorkHours == null) throwMissingFieldException("overWorkHours")
                if (employee.payment.overWorkCoefficient == null) throwMissingFieldException("overWorkCoefficient")
            }
            WorkerType.COMMISSION_WORKERS -> {
                if (employee.payment.saleAmount == null) throwMissingFieldException("saleAmount")
                if (employee.payment.salePercent == null) throwMissingFieldException("salePercent")
            }
            WorkerType.SALARIED_COMMISSION -> {
                if (employee.payment.monthPayment == null) throwMissingFieldException("monthPayment")
                if (employee.payment.saleAmount == null) throwMissingFieldException("saleAmount")
                if (employee.payment.salePercent == null) throwMissingFieldException("salePercent")
            }
        }

        return employeeRepository.save(
            EmployeeModel(
                id = 0,
                name = employee.name,
                type = employee.type,
                payment = PaymentModel(
                    monthPayment = employee.payment.monthPayment,
                    workedHours = employee.payment.workedHours,
                    hourPayment = employee.payment.hourPayment,
                    overWorkHours = employee.payment.overWorkHours,
                    overWorkCoefficient = employee.payment.overWorkCoefficient,
                    saleAmount = employee.payment.saleAmount,
                    salePercent = employee.payment.salePercent
                )
            )
        ).toDTO()
    }


    fun getAllEmployees() = employeeRepository.findAll().map {
        it.toDTO()
    }

    fun getSalaryDetailsByEmployeeId(employeeId: Long) =
        (employeeRepository.findByIdOrNull(employeeId) ?: throw EmployeeNotFoundByIdException(employeeId))
            .toDTO()

    @Transactional
    fun getTotalSalaryPaymentsData(): TotalSalaryPayments {
        val fixedSalaryWorkers = employeeRepository.findAllByType(WorkerType.FIXED_SALARY)
            .map { it.toDTO() }
        val hourlyWorkers = employeeRepository.findAllByType(WorkerType.HOURLY_PAYMENTS)
            .map { it.toDTO() }
        val commissionWorkers = employeeRepository.findAllByType(WorkerType.COMMISSION_WORKERS)
            .map { it.toDTO() }
        val salariedCommissionWorkers = employeeRepository.findAllByType(WorkerType.SALARIED_COMMISSION)
            .map { it.toDTO() }

        return TotalSalaryPayments(
            totalPayment = getAllEmployees().sumOf { it.getSalaryDetails() },
            fixedSalaryWorkers = fixedSalaryWorkers.size,
            totalFixedSalaryWorkersPayment = fixedSalaryWorkers.sumOf { it.getSalaryDetails() },
            hourlyWorkers = hourlyWorkers.size,
            totalHourlyWorkersPayment = hourlyWorkers.sumOf { it.getSalaryDetails() },
            commissionWorkers = commissionWorkers.size,
            totalCommissionWorkersPayment = commissionWorkers.sumOf { it.getSalaryDetails() },
            salariedCommissionWorkers = salariedCommissionWorkers.size,
            totalSalariedCommissionWorkersPayment = salariedCommissionWorkers.sumOf { it.getSalaryDetails() }
        )
    }

}
