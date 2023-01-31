-- 1.按部门编号升序、,,
SELECT * FROM `emp` ORDER BY deptno asc,sal DESC
-- 2.列出deptno=30的部门名称及员工
SELECT emp.*,dept.dname FROM emp,dept WHERE emp.deptno=dept.deptno and dept.deptno=30 
-- 3.列出每个部门最高、最低及平均工资 (考验分组的使用与聚合函数)
SELECT deptno,MAX(sal),MIN(sal),AVG(sal),COUNT(*) from emp GROUP BY deptno