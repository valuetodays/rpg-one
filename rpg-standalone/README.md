使用java3d可以加载并显示（旋转、放大等操作）obj模型，但无法显示纹理贴图。（见j3d.obj.JavaModelObjLoaderSimpleWithKeyControl）
使用autodeskfbxreview可以查看到obj/squirrel/songshu.obj的纹理贴图，但使用j3d.obj.JavaModelObjLoaderSimpleWithKeyControl就不能看到，哎……


可以显示3d模型的贴图了！！！【windows平台下 win7 x64】
new ObjFileReader(path)的参数中path若是"F:\\aaa\\bbb.obj"就能成功显示，而改成"F:/aaa/bbb.obj"就不能访问！
原因就在于ObjectFile的basePath属性，而该属性是由com.sun.j3d.loaders.objectfile.ObjectFile.setBasePathFromFilename赋值的，见http://eblog.doyourealizethattheimportantisdifficult.cn/article/getDetail.do?id=40

```text
    private void setBasePathFromFilename(String var1) {
        if(var1.lastIndexOf(File.separator) == -1) {
            this.setBasePath("." + File.separator);
        } else {
            this.setBasePath(var1.substring(0, var1.lastIndexOf(File.separator)));
        }
    }
```



2018-12-07