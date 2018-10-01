{
    dataSource : {
        type : "com.alibaba.druid.pool.DruidDataSource",
            events : {
            depose : 'close'
        },
        fields : {
            url : "jdbc:mysql://119.29.87.183:3306/kezhan_app",
                username : "kezhan_app",
                password : "Rh2c53M7G6X7sBti",
                maxWait: 15000, // 若不配置此项,如果数据库未启动,druid会一直等可用连接,卡住启动过程
                defaultAutoCommit : false // 提高fastInsert的性能
        }
    }
}