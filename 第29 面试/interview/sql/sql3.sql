-- 1.列出直接向King汇报的员工 (通过King找到编号,再通过编号关联到mgr进行查询就可以了)
SELECT * from emp WHERE mgr = (SELECT empno FROM emp WHERE ename = 'King')

SELECT e.* from emp e,(SELECT empno FROM emp WHERE ename = 'King') k WHERE e.mgr = k.empno
-- 2.列出公司所有员工的工龄,并倒序排列 (emp.*, 显示基础信息。order by 对已有结果进行排序)
-- SELECT DATE_FORMAT(NOW(),"%Y/%m/%d")
SELECT emp.*, DATE_FORMAT(NOW(),"%Y") -  DATE_FORMAT(hiredate,"%Y") FROM emp ORDER BY DATE_FORMAT(NOW(),"%Y") -  DATE_FORMAT(hiredate,"%Y") DESC
-- 简写
SELECT * FROM(
SELECT emp.*, DATE_FORMAT(NOW(),"%Y") -  DATE_FORMAT(hiredate,"%Y") yg FROM emp) d ORDER BY d.yg asc

-- 3.计算管理员与基层员工平均薪资差额 (MANAGER和PRESIDENT是管理者)
SELECT a_avg - b_avg from
(SELECT avg(sal) a_avg from emp where job='MANAGER' or job='PRESIDENT') a,
(SELECT AVG(sal) b_avg from emp WHERE job in ('CLERK','SALESMAN','ANALYST')) b

