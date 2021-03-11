package com.augustomesquita.billsapp.domain;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Objects;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

@Entity
@Table
public class Bill {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotBlank
    @Size(min = 1, max = 255)
    private String name;

    @NotNull
    private Long dueDate;

    @NotNull
    private Long paymentDate;

    @DecimalMin(value = "0.00")
    private BigDecimal originalValue;

    @DecimalMin(value = "0.00")
    private BigDecimal newValue;

    @NotBlank
    private String rule;

    @NotNull
    private Integer delayedDays;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public BigDecimal getNewValue() {
        return newValue;
    }

    public void setNewValue(BigDecimal newValue) {
        this.newValue = newValue;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public Integer getDelayedDays() {
        return delayedDays;
    }

    public void setDelayedDays(Integer delayedDays) {
        this.delayedDays = delayedDays;
    }

    public static class Builder {

        Bill bill = new Bill();

        public Builder withId(Long id) {
            this.bill.setId(id);
            return this;
        }

        public Builder withName(String name) {
            this.bill.setName(name);
            return this;
        }

        public Builder withDueDate(Long dueDate) {
            this.bill.setDueDate(dueDate);
            return this;
        }

        public Builder withPaymentDate(Long paymentDate) {
            this.bill.setPaymentDate(paymentDate);
            return this;
        }

        public Builder withOriginalValue(BigDecimal originalValue) {
            this.bill.setOriginalValue(originalValue);
            return this;
        }

        public Builder withNewValue(BigDecimal newValue) {
            this.bill.setNewValue(newValue);
            return this;
        }

        public Builder withRule(String rule) {
            this.bill.setRule(rule);
            return this;
        }

        public Builder withDelayedDays(Integer delayedDays) {
            this.bill.setDelayedDays(delayedDays);
            return this;
        }

        public Bill build() {
            return this.bill;
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Bill other = (Bill) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.dueDate, other.dueDate)) {
            return false;
        }
        if (!Objects.equals(this.paymentDate, other.paymentDate)) {
            return false;
        }
        if (!Objects.equals(this.originalValue.doubleValue(), other.originalValue.doubleValue())) {
            return false;
        }
        if (!Objects.equals(this.newValue.doubleValue(), other.newValue.doubleValue())) {
            return false;
        }
        if (!Objects.equals(this.rule, other.rule)) {
            return false;
        }
        if (!Objects.equals(this.delayedDays, other.delayedDays)) {
            return false;
        }
        return true;
    }

}
