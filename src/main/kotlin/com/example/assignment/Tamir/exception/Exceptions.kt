package com.example.assignment.Tamir.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

fun throwMissingFieldException(fieldName: String): Nothing = throw RuntimeException("No such $fieldName data")

@ResponseStatus(value = HttpStatus.NOT_FOUND)
class EmployeeNotFoundByIdException(employeeId: Long) : RuntimeException("No such employee with id: $employeeId")
