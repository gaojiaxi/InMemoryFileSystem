public class TestFSSolution {
    public void testFileSystem() throws Exception {
        FileSystem fs = new FileSystem();
        fs.mkdir("/foo");
        fs.mkdir("/foo/bar");
        fs.createFile("/bar");
        fs.createFile("/foo/foo");
        fs.createFile("/foo/bar/bar");
        check(fs.count() == 5);
        check(fs.list("/").size() == 2);
        fs.delete("/foo/bar");
        check(fs.count() == 3);
        check(fs.list("/").size() == 2);
    }
    public void check(boolean pass) {
        if (!pass) throw new RuntimeException("Test failed");
    }

    public static void main(String[] args) throws Exception {
        TestFSSolution test = new TestFSSolution();
        test.testFileSystem();
    }
}

