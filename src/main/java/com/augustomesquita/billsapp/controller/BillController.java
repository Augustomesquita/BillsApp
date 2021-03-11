package com.augustomesquita.billsapp.controller;

import com.augustomesquita.billsapp.domain.Bill;
import com.augustomesquita.billsapp.domain.BillRequest;
import com.augustomesquita.billsapp.service.BillService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 *
 * @author Augusto
 */
@RestController
@Api(tags = {"Bill"})
public class BillController {

    private static final Logger LOG = LoggerFactory.getLogger(BillController.class);

    @Autowired
    private BillService service;

    @GetMapping("/v1/bills")
    @ApiOperation(value = "Returns a list with all bills.")
    public Page<Bill> getAllBill(Pageable pageable) {
        LOG.info("Getting all bills...");
        Page<Bill> result = service.getAllBills(pageable);
        LOG.info("The bills were returned successfully.");
        return result;
    }

    @PostMapping("/v1/bills")
    @ApiOperation(value = "Creates a bill.")
    public ResponseEntity<Bill> postBill(@Valid @RequestBody BillRequest request) {
        LOG.info("Creating a bill...");
        final Bill saved = service.save(request);
        LOG.info("The Bill with ID [{}] was saved successfully.", saved.getId());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(saved);
    }

}
