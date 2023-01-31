-- 1.列出市场部(SALES)及研发部(RESEARCH)的员工
SELECT * from dept d,emp e WHERE d.deptno = e.deptno and (d.dname='SALES' or d.dname='RESEARCH')
-- 2.列出人数超过3人的部门(WHERE是对原始的数据进行筛选,在group by之前执行的。而对于having关键字,它的作用是对分组的结果进行二次筛选,在group by之后执行。group by通过部门名字分组)
SELECT d.dname,COUNT(*) FROM dept d, emp e WHERE d.deptno = e.deptno GROUP BY d.dname HAVING COUNT(*)>3
-- 3.计算miller年薪比smith高多少 (考察子查询的使用)
SELECT a_sal,b_sal, a_sal- b_sal from 
(SELECT sal * 12 a_sal FROM	emp WHERE ename ='MILLER') a,
(SELECT sal * 12 b_sal FROM	emp WHERE ename ='SMITH') b