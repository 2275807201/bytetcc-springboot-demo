package com.liangh.bytetcc.demo.provider.controller;

import com.liangh.bytetcc.demo.provider.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.bytesoft.compensable.Compensable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Compensable(interfaceClass = AccountService.class,confirmableKey = "accountServiceConfirm",cancellableKey = "accountServiceCancel")
@RestController
public class ProviderController {

	private int i = 0;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@ResponseBody
	@RequestMapping(value = "/increase/{acctId}/{amount}")
	@Transactional
	public String increaseAmount(@PathVariable("acctId") String acctId, @PathVariable("amount") double amount) {
		log.info("xxxxxxxxxxxxxxxxxxxx---------try increaseAmount");
		String sql = "UPDATE `tb_account_two` SET `frozen` = frozen + ? WHERE `acct_id` = ? " ;
		jdbcTemplate.update(sql,amount,acctId);
		return "ok";
	}

}
