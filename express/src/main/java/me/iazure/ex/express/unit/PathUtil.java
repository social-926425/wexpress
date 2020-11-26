package me.iazure.ex.express.unit;

public class PathUtil {
    //获取文件分割符
    private static String seperator = System.getProperty("file.separator");
    public static String getImgBasePath(){
        String os = System.getProperty("os.name");
        String basePath = "";
        if (os.toLowerCase().startsWith("win")){
            basePath="E:/Project/java/springbootpackage/projectdev/image/";
        }else {
            basePath="/home/projectdev/image/";
        }
        basePath = basePath.replace("/",seperator);
        return basePath;
    }
    public static String getArticleImagePath(int articleId){
        String imagePath = "upload/item/shop/"+articleId+"/";
        return imagePath.replace("/",seperator);
    }
}
