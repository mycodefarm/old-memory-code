
--秒杀数据库
CREATE DATABASE killone;
use killone;

CREATE TABLE killone(
  `kill_id` bigint not null auto_increment comment '品库存id',
  `name` VARCHAR(120) not null comment '商品名称',
  `number` int not null comment '库存数量',
  `start_time` TIMESTAMP NOT NULL COMMENT '秒杀开始时间',
  `end_time` TIMESTAMP NOT NULL COMMENT '秒杀结束时间',
  `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (kill_id),
  /*建立索引，方便查找*/
  KEY idx_start_time (start_time),
  KEY idx_end_time(end_time),
  KEY idx_create_time(create_time)
)ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 comment '秒杀表';

--初试化数据
INSERT INTO killone(name,number,start_time,end_time) VALUES
  ('一元秒杀鸡蛋10个',100,'2017-05-21 00:00:00','2017-05-22 00:00:00'),
  ('100元秒杀鸡iphone8',200,'2017-05-21 00:00:00','2017-05-22 00:00:00'),
  ('1000元秒杀小明',100,'2017-05-21 00:00:00','2017-05-22 00:00:00'),
  ('一元秒杀洗发露',1000,'2017-05-21 00:00:00','2017-05-22 00:00:00')

--秒杀成功明细记录
CREATE TABLE success_killed (
  `kill_id` bigint not NULL comment '秒杀商品id',
  `user_phone` bigint NOT NULL comment '用户手机号',
  `state` tinyint NOT NULL DEFAULT -1 comment '状态：-1:无效，0：成功，1：已付款',
  `create_time` TIMESTAMP NOT NULL comment '创建时间',
  PRIMARY KEY (kill_id,user_phone) /*联合主键*/,
  KEY idx_create_time(create_time)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '秒杀成功明细表'

