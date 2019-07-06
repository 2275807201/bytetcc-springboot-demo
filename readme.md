### springboot  + bytetcc 实现springmvc的分布式事物。

目标：
springboot  + bytetcc 实现springmvc的分布式事物。



https://github.com/liuyangming/ByteTCC-sample

注意点:

1. 在每个参与tcc事务的数据库中创建bytejta表（ddl见bytetcc-supports.jar/bytetcc.sql）；

业务场景：

A向B转账1元

```
Try:	
	//A的变化
	A: {
		 amount = amount -1;
		 frozen = frozen + 1;
	}
	
	//B的变化
	B: {
		 	frozen = frozen + 1;
	}
	
Confirm:
	//A的变化
	A: {
		 frozen = frozen - 1;
	}
	
	//B的变化
	B: {
		 	frozen = frozen - 1;
		 	amount = amount + 1;
	}
	
Cancel:
	//A的变化
	A: {
		 amount = amount +1;
		 frozen = frozen - 1;
	}
	
	//B的变化
	B: {
		 	frozen = frozen - 1;
	}
```



准备工作：

sql脚本：

```my
-- 数据库实例inst01
CREATE TABLE tb_account_one (
  acct_id varchar(16),
  amount double(10, 2),
  frozen double(10, 2),
  PRIMARY KEY (acct_id)
) ENGINE=InnoDB;

insert into tb_account_one (acct_id, amount, frozen) values('1001', 100, 0.00);

-- 数据库实例inst02
CREATE TABLE tb_account_two (
  acct_id varchar(16),
  amount double(10, 2),
  frozen double(10, 2),
  PRIMARY KEY (acct_id)
) ENGINE=InnoDB;

insert into tb_account_two (acct_id, amount, frozen) values('2001', 10000.00, 0.00);


```

