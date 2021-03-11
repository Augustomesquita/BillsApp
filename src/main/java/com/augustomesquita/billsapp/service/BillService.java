package com.augustomesquita.billsapp.service;

import com.augustomesquita.billsapp.domain.Bill;
import com.augustomesquita.billsapp.domain.BillRepository;
import com.augustomesquita.billsapp.domain.BillRequest;
import static java.lang.Math.pow;
import java.math.BigDecimal;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 *
 * @author Augusto
 */
@Service
public class BillService {

    private static final Logger LOG = LoggerFactory.getLogger(BillService.class);

    @Autowired
    private BillRepository repository;

    public Page<Bill> getAllBills(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Bill save(BillRequest request) {
        Bill bill = convertBillRequest(request);
        int delayedDays = getDelayedDays(bill);

        if (delayedDays > 0) {
            updatePayment(bill, delayedDays);
        }
        
        return repository.save(bill);
    }

    private int getDelayedDays(Bill bill) {
        DateTime dueDate = new DateTime(bill.getDueDate());
        DateTime paymentDate = new DateTime(bill.getPaymentDate());
        int days = Days.daysBetween(dueDate, paymentDate).getDays();
        return days;
    }

    private Bill convertBillRequest(BillRequest request) {
        return new Bill.Builder()
                .withName(request.getName())
                .withDueDate(request.getDueDate())
                .withPaymentDate(request.getPaymentDate())
                .withDelayedDays(0)
                .withOriginalValue(request.getOriginalValue())
                .withNewValue(request.getOriginalValue())
                .withRule("None")
                .build();
    }

    private Bill createDelayedPayment(BillRequest request) {

        return new Bill.Builder()
                .withName(request.getName())
                .withDueDate(request.getDueDate())
                .withPaymentDate(request.getPaymentDate())
                .withDelayedDays(0)
                .withOriginalValue(request.getOriginalValue())
                .withNewValue(request.getOriginalValue())
                .withRule("None")
                .build();
    }

    private void updatePayment(Bill bill, int delayedDays) {
        bill.setDelayedDays(delayedDays);
        if (delayedDays > 5) {
            applyPenalty(bill, delayedDays, 1.003, 1.05);
        } else if (delayedDays > 3) {
            applyPenalty(bill, delayedDays, 1.002, 1.03);
        } else {
            applyPenalty(bill, delayedDays, 1.001, 1.02);
        }
    }

    private void applyPenalty(Bill bill, int delayedDays, Double interestPerDay, Double penalty) {
        BigDecimal newValue = bill.getOriginalValue().multiply(new BigDecimal(penalty));
        bill.setNewValue(newValue.multiply(new BigDecimal(pow(interestPerDay, delayedDays))));
        bill.setRule("[(Original Value * " + penalty + ") * " + interestPerDay + "^" + delayedDays + "]");
    }

}
