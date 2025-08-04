

你的 SQL Server 是 2019 版本，理论上完全支持 OFFSET ... FETCH NEXT 语法。但你这条语句报错的原因可能是缺少了 ORDER BY 子句 ——这是分页语法的前提条件。