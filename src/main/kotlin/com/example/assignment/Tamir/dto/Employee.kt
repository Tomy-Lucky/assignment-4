package com.example.assignment.Tamir.dto

import com.example.assignment.Tamir.exception.throwMissingFieldException
import com.example.assignment.Tamir.model.WorkerType

data class Employee(
  val id: Long,
  val name: String,
  val type: WorkerType,
  val payment: Payment
) {

  fun getSalaryDetails() = when(type) {
    WorkerType.FIXED_SALARY -> payment.monthPayment ?: throwMissingFieldException("monthPayment")
    WorkerType.HOURLY_PAYMENTS -> {
      val hours = payment.workedHours ?: throwMissingFieldException("workedHours")
      val hourPayment = payment.hourPayment ?: throwMissingFieldException("workedHours")
      val overWorkHours = payment.overWorkHours ?: throwMissingFieldException("overWorkHours")
      val overWorkCommission = payment.overWorkCoefficient ?: throwMissingFieldException("overWorkCommission")
      if (hours > overWorkCommission)
        overWorkHours * hourPayment + (hours - overWorkHours) * overWorkHours * overWorkCommission
      else
        hours * hourPayment
    }
    WorkerType.COMMISSION_WORKERS -> {
      val saleAmount = payment.saleAmount ?: throwMissingFieldException("saleAmount")
      val salePercent = payment.salePercent ?: throwMissingFieldException("salePercent")
      saleAmount * salePercent
    }
    WorkerType.SALARIED_COMMISSION -> {
      val monthPayment = payment.monthPayment ?: throwMissingFieldException("monthPayment")
      val saleAmount = payment.saleAmount ?: throwMissingFieldException("saleAmount")
      val salePercent = payment.salePercent ?: throwMissingFieldException("salePercent")
      monthPayment + saleAmount * salePercent
    }
  }
}

data class Payment(
  val monthPayment: Double?,
  val workedHours: Double?,
  val hourPayment: Double?,
  val overWorkHours: Double?,
  val overWorkCoefficient: Double?,
  val saleAmount: Double?,
  val salePercent: Double?
)
