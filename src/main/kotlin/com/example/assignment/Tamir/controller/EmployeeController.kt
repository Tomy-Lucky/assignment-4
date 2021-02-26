package com.example.assignment.Tamir.controller

import com.example.assignment.Tamir.dto.Employee
import com.example.assignment.Tamir.dto.Payment
import com.example.assignment.Tamir.model.WorkerType
import com.example.assignment.Tamir.service.EmployeeService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class EmployeeController(
    private val employeeService: EmployeeService
) {

    @GetMapping("/all-employees")
    fun getAllEmployees() = employeeService.getAllEmployees()

    @GetMapping("/add-employee")
    fun addEmployee(
        @RequestParam("name") name: String,
        @RequestParam("type") type: WorkerType,
        @RequestParam("monthPayment", required = false) monthPayment: Double,
        @RequestParam("workedHours", required = false) workedHours: Double,
        @RequestParam("hourPayment", required = false) hourPayment: Double,
        @RequestParam("overWorkHours", required = false) overWorkHours: Double,
        @RequestParam("overWorkCommission", required = false) overWorkCommission: Double,
        @RequestParam("saleAmount", required = false) saleAmount: Double,
        @RequestParam("salePercent", required = false) salePercent: Double,
    ) = employeeService.addEmployee(
        employee = Employee(
            id = 0,
            name = name,
            type = type,
            payment = Payment(
                monthPayment = monthPayment,
                workedHours = workedHours,
                hourPayment = hourPayment,
                overWorkHours = overWorkHours,
                overWorkCoefficient = overWorkCommission,
                saleAmount = saleAmount,
                salePercent = salePercent
            )
        )
    )

    @GetMapping("/employee-by-id")
    fun getEmployeeById(
        @RequestParam("id") id: Long
    ) = employeeService.getSalaryDetailsByEmployeeId(id)

    @GetMapping("/total-salary-payments")
    fun getTotalSalaryPaymentsData() = employeeService.getTotalSalaryPaymentsData()
}
