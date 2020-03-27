package cn.yhsb.cjb.database;

import org.junit.Test;

import cn.yhsb.cjb.database.Jzfp2020.FPHistoryData;

public class DatabaseTest {
    @Test
    public void testJzfp2020() {
        Jzfp2020.use(session -> {
            var builder = session.getCriteriaBuilder();
            var query = builder.createQuery(FPHistoryData.class);
            var root = query.from(FPHistoryData.class);
            query.select(root)
                .where(builder.equal(root.get("idcard"), "430302200101155040"));
            var q = session.createQuery(query);
            var data = q.getSingleResult();
            System.out.println(data.getName());
        });
    }
}