package com.augustomesquita.billsapp.domain;

import io.swagger.annotations.ApiModel;
import java.math.BigDecimal;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Augusto
 */
@ApiModel("BillRequest")
public class BillRequest {

    @NotBlank
    @Size(min = 1, max = 255)
    private String name;

    @NotNull(message = "Please enter due date")
    private Long dueDate;

    @NotNull(message = "Please enter payment date")
    private Long paymentDate;

    @DecimalMin(value = "0.00", message = "Please enter a valid originalValue >= 0.00")
    private BigDecimal originalValue;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getDueDate() {
        return dueDate;
    }

    public void setDueDate(Long dueDate) {
        this.dueDate = dueDate;
    }

    public Long getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Long paymentDate) {
        this.paymentDate = paymentDate;
    }

    public BigDecimal getOriginalValue() {
        return originalValue;
    }

    public void setOriginalValue(BigDecimal originalValue) {
        this.originalValue = originalValue;
    }

}
