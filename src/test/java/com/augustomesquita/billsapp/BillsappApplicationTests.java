package com.augustomesquita.billsapp;

import com.augustomesquita.billsapp.domain.Bill;
import com.augustomesquita.billsapp.domain.BillRepository;
import com.augustomesquita.billsapp.domain.BillRequest;
import com.augustomesquita.billsapp.service.BillService;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BillsappApplicationTests {

    @InjectMocks
    BillService billService;

    @Mock
    BillRepository billRepository;

    @Mock
    private Pageable pageableMock;

    @Mock
    private Bill billMock;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getAllBills() {

        // Given
        List<Bill> bills = new ArrayList<>();

        bills.add(new Bill.Builder()
                .withName("Conta de Luz 1 / Pagamento 1 dia atrasado")
                .withDueDate(1635044400000L)
                .withPaymentDate(1635130800000L)
                .withDelayedDays(1)
                .withOriginalValue(new BigDecimal(100.00))
                .withNewValue(new BigDecimal(102.10199999999999))
                .withRule("[(Original Value * 1.02) * 1.001^1]")
                .build());

        bills.add(new Bill.Builder()
                .withName("Conta de Luz 2 / Pagamento 4 dias atrasado")
                .withDueDate(1635044400000L)
                .withPaymentDate(1635390000000L)
                .withDelayedDays(4)
                .withOriginalValue(new BigDecimal(100.00))
                .withNewValue(new BigDecimal(103.82647529764802))
                .withRule("[(Original Value * 1.03) * 1.002^4]")
                .build());

        bills.add(new Bill.Builder()
                .withName("Conta de Luz 3 / Pagamento 6 dias atrasado")
                .withDueDate(1635044400000L)
                .withPaymentDate(1635562800000L)
                .withDelayedDays(6)
                .withOriginalValue(new BigDecimal(100.00))
                .withNewValue(new BigDecimal(106.90423182772811))
                .withRule("[(Original Value * 1.05) * 1.003^6]")
                .build());

        Page<Bill> pagedBills = new PageImpl(bills);

        // When
        when(billRepository.findAll(pageableMock)).thenReturn(pagedBills);
        Page<Bill> allBills = billService.getAllBills(pageableMock);

        // Then
        assertEquals(3, allBills.getTotalElements());
        verify(billRepository, times(1)).findAll(pageableMock);
    }
    
    @Test
    public void createBillWithDelaydPayment_1Days() {

        // Given
        BillRequest billRequest = new BillRequest();
        billRequest.setName("Conta de Luz 1 / Pagamento 1 dia atrasado");
        billRequest.setDueDate(1635044400000L);
        billRequest.setPaymentDate(1635130800000L);
        billRequest.setOriginalValue(new BigDecimal(100));

        // When
        when(billRepository.save(billMock)).thenReturn(null);
        billService.save(billRequest);

        // Then
        verify(billRepository, times(1)).save(
                new Bill.Builder()
                .withName("Conta de Luz 1 / Pagamento 1 dia atrasado")
                .withDueDate(1635044400000L)
                .withPaymentDate(1635130800000L)
                .withDelayedDays(1)
                .withOriginalValue(new BigDecimal(100.00))
                .withNewValue(new BigDecimal(102.10199999999999))
                .withRule("[(Original Value * 1.02) * 1.001^1]")
                .build()
        );
    }

    @Test
    public void createBillWithDelaydPayment_4Days() {

        // Given
        BillRequest billRequest = new BillRequest();
        billRequest.setName("Conta de Luz 2 / Pagamento 4 dias atrasado");
        billRequest.setDueDate(1635044400000L);
        billRequest.setPaymentDate(1635390000000L);
        billRequest.setOriginalValue(new BigDecimal(100));

        // When
        when(billRepository.save(billMock)).thenReturn(null);
        billService.save(billRequest);

        // Then
        verify(billRepository, times(1)).save(
                new Bill.Builder()
                .withName("Conta de Luz 2 / Pagamento 4 dias atrasado")
                .withDueDate(1635044400000L)
                .withPaymentDate(1635390000000L)
                .withDelayedDays(4)
                .withOriginalValue(new BigDecimal(100.00))
                .withNewValue(new BigDecimal(103.82647529764802))
                .withRule("[(Original Value * 1.03) * 1.002^4]")
                .build()
        );
    }

    @Test
    public void createBillWithDelaydPayment_6Days() {

        // Given
        BillRequest billRequest = new BillRequest();
        billRequest.setName("Conta de Luz 3 / Pagamento 6 dias atrasado");
        billRequest.setDueDate(1635044400000L);
        billRequest.setPaymentDate(1635562800000L);
        billRequest.setOriginalValue(new BigDecimal(100));

        // When
        when(billRepository.save(billMock)).thenReturn(null);
        billService.save(billRequest);

        // Then
        verify(billRepository, times(1)).save(
                new Bill.Builder()
                .withName("Conta de Luz 3 / Pagamento 6 dias atrasado")
                .withDueDate(1635044400000L)
                .withPaymentDate(1635562800000L)
                .withDelayedDays(6)
                .withOriginalValue(new BigDecimal(100.00))
                .withNewValue(new BigDecimal(106.90423182772811))
                .withRule("[(Original Value * 1.05) * 1.003^6]")
                .build()
        );
    }
    
    @Test
    public void createBillWithNormalPayment_1() {

        // Given
        BillRequest billRequest = new BillRequest();
        billRequest.setName("Conta de Luz / Pagamento em dia");
        billRequest.setDueDate(1635044400000L);
        billRequest.setPaymentDate(1634958000000L);
        billRequest.setOriginalValue(new BigDecimal(100));

        // When
        when(billRepository.save(billMock)).thenReturn(null);
        billService.save(billRequest);

        // Then
        verify(billRepository, times(1)).save(
                new Bill.Builder()
                .withName("Conta de Luz / Pagamento em dia")
                .withDueDate(1635044400000L)
                .withPaymentDate(1634958000000L)
                .withDelayedDays(0)
                .withOriginalValue(new BigDecimal(100.00))
                .withNewValue(new BigDecimal(100.00))
                .withRule("None")
                .build()
        );
    }
    
    @Test
    public void createBillWithNormalPayment_2() {

        // Given
        BillRequest billRequest = new BillRequest();
        billRequest.setName("Conta de Luz / Pagamento em dia");
        billRequest.setDueDate(1635044400000L);
        billRequest.setPaymentDate(1635080400000L);
        billRequest.setOriginalValue(new BigDecimal(100));

        // When
        when(billRepository.save(billMock)).thenReturn(null);
        billService.save(billRequest);

        // Then
        verify(billRepository, times(1)).save(
                new Bill.Builder()
                .withName("Conta de Luz / Pagamento em dia")
                .withDueDate(1635044400000L)
                .withPaymentDate(1635080400000L)
                .withDelayedDays(0)
                .withOriginalValue(new BigDecimal(100.00))
                .withNewValue(new BigDecimal(100.00))
                .withRule("None")
                .build()
        );
    }
    
    @Test
    public void createBillWithNormalPayment_3() {

        // Given
        BillRequest billRequest = new BillRequest();
        billRequest.setName("Conta de Luz / Pagamento em dia");
        billRequest.setDueDate(1635044400000L);
        billRequest.setPaymentDate(1635044400000L);
        billRequest.setOriginalValue(new BigDecimal(100));

        // When
        when(billRepository.save(billMock)).thenReturn(null);
        billService.save(billRequest);

        // Then
        verify(billRepository, times(1)).save(
                new Bill.Builder()
                .withName("Conta de Luz / Pagamento em dia")
                .withDueDate(1635044400000L)
                .withPaymentDate(1635044400000L)
                .withDelayedDays(0)
                .withOriginalValue(new BigDecimal(100.00))
                .withNewValue(new BigDecimal(100.00))
                .withRule("None")
                .build()
        );
    }

}
