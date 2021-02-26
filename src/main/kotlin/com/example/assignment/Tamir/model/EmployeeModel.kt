package com.example.assignment.Tamir.model

import com.example.assignment.Tamir.dto.Employee
import com.example.assignment.Tamir.dto.Payment
import org.hibernate.annotations.DynamicUpdate
import javax.persistence.*

@Entity
@Table(name = "employee")
@DynamicUpdate
class EmployeeModel(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long,

    @Column
    var name: String,

    @Enumerated
    @Column
    var type: WorkerType,

    @Embedded
    var payment: PaymentModel
) {

    fun toDTO() = Employee(
        id = id,
        name = name,
        type = type,
        payment = payment.toDTO()
    )
}

@Embeddable
class PaymentModel(
    @Column var monthPayment: Double?,
    @Column var workedHours: Double?,
    @Column var hourPayment: Double?,
    @Column var overWorkHours: Double?,
    @Column var overWorkCoefficient: Double?,
    @Column var saleAmount: Double?,
    @Column var salePercent: Double?
) {

    fun toDTO() = Payment(
        monthPayment = monthPayment,
        workedHours = workedHours,
        hourPayment = hourPayment,
        overWorkHours = overWorkHours,
        overWorkCoefficient = overWorkCoefficient,
        saleAmount = saleAmount,
        salePercent = salePercent
    )
}
