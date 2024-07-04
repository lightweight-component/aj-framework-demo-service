package com.foo.common;

import java.io.File;

public class Utils {
    public static File getTestDir() {
        return new File(new File("."), "derby-test-db");
    }

   public static String mkdir() {
        File newDirectory = getTestDir();

//        if (newDirectory.mkdir())
//            System.out.println("新目录创建成功：" + newDirectory.getAbsolutePath());
//        else
//            System.out.println("新目录创建失败，可能目录已存在或没有权限。");

        // 先删除
        deleteDirectory(newDirectory);

        return newDirectory.getAbsolutePath().replace("\\.", "");
    }

    public static String CREATE_TABLE = "CREATE TABLE Employees (\n" +
            "    id INT PRIMARY KEY,\n" +
            "    name VARCHAR(50),\n" +
            "    birthday DATE,\n" +
            "    hire_date DATE,\n" +
            "    department VARCHAR(50)\n" +
            ")";

    /**
     * 删除目录及其所有内容的递归方法。
     *
     * @param directory 要删除的目录对象
     * @return 删除成功返回true，否则返回false
     */
    public static boolean deleteDirectory(File directory) {
        // 检查目录是否存在
        if (!directory.exists())
            return false;

        // 删除目录中的所有文件和子目录
        File[] files = directory.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isDirectory())
                    // 如果是子目录，则递归调用删除方法
                    deleteDirectory(file);
                else
                    // 如果是文件，直接删除
                    file.delete();
            }
        }

        // 删除空目录
        return directory.delete();
    }
}
