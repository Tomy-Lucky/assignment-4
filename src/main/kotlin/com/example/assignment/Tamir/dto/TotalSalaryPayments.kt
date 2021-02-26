package com.example.assignment.Tamir.dto

data class TotalSalaryPayments(
    val totalPayment: Double,
    val fixedSalaryWorkers: Int,
    val totalFixedSalaryWorkersPayment: Double,
    val hourlyWorkers: Int,
    val totalHourlyWorkersPayment: Double,
    val commissionWorkers: Int,
    val totalCommissionWorkersPayment: Double,
    val salariedCommissionWorkers: Int,
    val totalSalariedCommissionWorkersPayment: Double
)
