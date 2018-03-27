{
    utils: {
        type: 'cn.clate.kezhan.utils.PathUtils',
            fields: {
            sc: {
                app: '$servlet'
            }   // 将 ServletContext 对象注入 MyUtils
        }
    },

    tmpFilePool: {
        type: 'org.nutz.filepool.NutFilePool',
            args: [{java: '$utils.getPath("/WEB-INF/tmp")'}, 1000]   // 调用 MyUtils.getPath 函数
    },

    uploadFileContext : {
        type : 'org.nutz.mvc.upload.UploadingContext',
            singleton:false,
            args:[{refer: 'tmpFilePool'}],
            fields:{
            // 是否忽略空文件, 默认为 false
            ignoreNull : true,
                // 单个文件最大尺寸(大约的值，单位为字节，即 1048576 为 1M)
                maxFileSize: 1048576,
                // 正则表达式匹配可以支持的文件名
                nameFilter: '^(.+[.])(gif|jpg|png)$'
        }
    },

    myUpload : {
        type : 'org.nutz.mvc.upload.UploadAdaptor',
            singleton: false,
            args: [{refer: 'uploadFileContext'}]
    }
}