delimiter $$ --在命令行使用$$替代;

CREATE PROCEDURE `killone`.`exec_killone`
(in p_kill_id bigint ,in p_user_phone bigint,
in p_kill_time TIMESTAMP ,out result int)
BEGIN
  DECLARE insert_count int DEFAULT 0;
  start TRANSACTION;
  INSERT ignore into success_killed(kill_id,user_phone,create_time)
  VALUES (p_kill_id,p_user_phone,p_kill_time);
  SELECT ROW_COUNT() INTO insert_count;
  if (insert_count = 0)THEN
    ROLLBACK ;
    SET result = -2; --重复
  elseif (insert_count < 0)THEN
    ROLLBACK ;
    SET result = -1;--内部错误
  ELSE
    UPDATE killone SET
    number = number - 1
    WHERE kill_id=p_kill_id
    AND start_time<p_kill_time
    AND end_time>p_kill_time
    and number>0;
    select row_count() into insert_count;
    if(insert_count=0)THEN
      ROLLBACK ;
      SET result = 0;--秒杀结束
    elseif(insert_count<0)THEN
      ROLLBACK ;
      set result = -1;
    ELSE
      COMMIT ;
      set result = 1;
    end if;
  END if;
END;
$$
--存储过程结束

delimiter ; --又定义回来

set @result = -2;
--执行
call exec_killone(1001,11011011011,now(),@result);

select @result;