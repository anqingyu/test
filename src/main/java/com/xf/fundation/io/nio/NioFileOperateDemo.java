package com.xf.fundation.io.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;
import java.util.stream.Stream;

/**
 * NIO文件操作
 *  注意：FileChannel是一个连接到文件的通道。可以通过文件通道读写文件。FileChannel无法设置为非阻塞模式，它总是运行在阻塞模式下,因此不能被注册到 Selector 上。
 *      因为UNIX不支持非阻塞的文件I / O。由于Java应该（至少尝试在所有平台上）提供相同的行为，因此fileChannel不会实现SelectableChannel。
 */
public class NioFileOperateDemo {

    /**
     * 使用NIO的FileChannel读取
     * @param file
     * @param newFile
     */
    public static void fileChannelRead(File file , File newFile){
        //1.23GB文件
        //每次读取1M，耗时：8947ms
        //每次读取5M，耗时：2976ms
        //每次读取10M，耗时：1802ms
        //每次读取20M，耗时：1279ms
        long d1 = System.currentTimeMillis();
        FileInputStream in = null;
        FileOutputStream output = null;
        FileChannel fic = null;
        FileChannel  foc = null;
        try {
            in = new FileInputStream(file);
            output = new FileOutputStream(newFile);
            fic = in.getChannel();
            foc = output.getChannel();

            //fic.transferTo(0, fic.size(), foc);
            // 每次读取字节20480
            ByteBuffer buf = ByteBuffer.allocate(20480);
            while(fic.read(buf) != -1){
                // 把Buffer切换到读取数据模式
                buf.flip();
                // 将缓冲区的数据写入通道中
                foc.write(buf);
                // 清空缓冲区
                buf.clear();
            }

            long d2 = System.currentTimeMillis();
            System.out.println("fileChannelRead读取完成，耗时：" + (d2 - d1));
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            try {
                if(in != null){
                    in.close();
                }
                if(output != null){
                    output.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 创建文件夹
     *   Path接口是在java7加入到NIO的,位于java.nio.file包,它的实例代表了文件系统里的一个路径,可以指向文件或文件夹
     *   Files类位于nio.file包中，提供了几个熟练的文件操作
     */
    public void createDirectory() {
        Path path = Paths.get("D:/dir");
        try {
            Path newDir = Files.createDirectory(path);
        } catch (FileAlreadyExistsException e) {
            // the directory already exists.
        } catch (IOException e) {
            //something else went wrong
            e.printStackTrace();
        }
    }

    /**
     * 拷贝文件
     */
    public void copyFile() {
        // Path接口是在java7加入到NIO的,位于java.nio.file包,它的实例代表了文件系统里的一个路径,可以指向文件或文件夹
        Path sourcePath = Paths.get("data/logging.properties");
        Path destinationPath = Paths.get("data/logging-copy.properties");

        try {
            // Files类位于nio.file包中，提供了几个熟练的文件操作
            Files.copy(sourcePath, destinationPath);
        } catch (FileAlreadyExistsException e) {
            //destination file already exists
        } catch (IOException e) {
            //something else went wrong
            e.printStackTrace();
        }
    }

    /**
     * 覆盖文件
     */
    public void overwritingExistingFiles(){
        Path sourcePath      = Paths.get("data/logging.properties");
        Path destinationPath = Paths.get("data/logging-copy.properties");

        try {
            Files.copy(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
        } catch(FileAlreadyExistsException e) {
            //destination file already exists
        } catch (IOException e) {
            //something else went wrong
            e.printStackTrace();
        }
    }

    /**
     * 移动文件: Files.move()
     */
    public void filesMove() {
        Path sourcePath = Paths.get("data/logging-copy.properties");
        Path destinationPath = Paths.get("data/subdir/logging-moved.properties");

        try {
            Files.move(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            //moving file failed.
            e.printStackTrace();
        }
    }

    /**
     * 删除文件：Files.delete()
     */
    public void filesDelete() {
        Path path = Paths.get("data/subdir/logging-moved.properties");

        try {
            Files.delete(path);
        } catch (IOException e) {
            //deleting file failed
            e.printStackTrace();
        }
    }

    /**
     * 遍历文件夹: Files.walkFileTree()
     */
    public void filesWaltFileTree() throws IOException {
        Path path = Paths.get("D:/test_temp");
        Files.walkFileTree(path, new FileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
                System.out.println("pre visit dir:" + dir);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                System.out.println("visit file: " + file);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFileFailed(Path file, IOException exc) {
                System.out.println("visit file failed: " + file);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) {
                System.out.println("post visit directory: " + dir);
                return FileVisitResult.CONTINUE;
            }
        });
    }

    /**
     * 查找文件: Files.walkFileTree()
     */
    public void searchingFile() {
        Path rootPath = Paths.get("D:/test_temp");
        String fileToFind = File.separator + "README.txt";

        try {
            Files.walkFileTree(rootPath, new SimpleFileVisitor<Path>() {

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                    String fileString = file.toAbsolutePath().toString();
                    //System.out.println("pathString = " + fileString);

                    if (fileString.endsWith(fileToFind)) {
                        System.out.println("file found at path: " + file.toAbsolutePath());
                        return FileVisitResult.TERMINATE;
                    }
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 循环删除文件
     */
    public void deletingDirectoriesRecursively(){
        Path rootPath = Paths.get("data/to-delete");

        try {
            Files.walkFileTree(rootPath, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    System.out.println("delete file: " + file.toString());
                    Files.delete(file);
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                    Files.delete(dir);
                    System.out.println("delete dir: " + dir.toString());
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    /**
     * 生成超链接
     */
    public void createSymbolicLink() throws IOException {
        //连接路径
        Path p = Paths.get("D:/test_temp/a.txt");
        //目标路径
        Path target = Paths.get("C:/Users/zuiwu/Desktop");
        //生成指向目标路径的超链接,返回连接路径p
        Path p3 = Files.createSymbolicLink(p,target);
    }

    /**
     * 生成超链接
     */
    public void filesReadAllLines() throws IOException {
        //读取每行数据放入List中
        List<String> lines = Files.readAllLines(Paths.get("D:/test_temp/a.txt"));
        //读取文件内容放入流中
        Stream<String> lines1 = Files.lines(Paths.get("D:/test_temp/a.txt"));
    }


}
