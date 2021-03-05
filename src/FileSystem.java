import java.util.ArrayList;
import java.util.List;

public class FileSystem {
    private final Directory root;
    public FileSystem() {
        root = new Directory("/", null);
    }

    public List<Entry> resolve(String path) {
        assert path.startsWith("/");
        String[] components = path.substring(1).split("/");
        List<Entry> entries = new ArrayList<>(components.length + 1);
        entries.add(root);
        Entry entry = root;
        for (String component : components) {
            if (entry == null || !(entry instanceof Directory)) {
                throw new IllegalArgumentException("Invalid path:" + path);
            }
            if (!component.isEmpty()) {
                entry = ((Directory) entry).getChild(component);
                entries.add(entry);
            }
        }
        return entries;
    }
    public void mkdir(String path) {
        List<Entry> entries = resolve(path);
        if (entries.get(entries.size() - 1) != null) {
            throw new IllegalArgumentException("Directory already exists: " + path);
        }
        String[] components = path.split("/");
        String dirName = components[components.length - 1];
        Directory parent = (Directory) entries.get(entries.size() - 2);
        Directory newDir = new Directory(dirName, parent);
        parent.addEntry(newDir);
    }

    public void createFile(String path) {
        assert !path.endsWith("/");
        List<Entry> entries = resolve(path);
        if (entries.get(entries.size() - 1) != null) {
            throw new IllegalArgumentException("File already exists: " + path);
        }
        String fileName = path.substring(path.lastIndexOf("/") + 1);
        Directory parent = (Directory) entries.get(entries.size() - 2);
        File newFile = new File(fileName, parent, 0);
        parent.addEntry(newFile);
    }

    public void delete(String path) {
        List<Entry> entries = resolve(path);
        if (entries.get(entries.size() - 1) == null) {
            throw new IllegalArgumentException("File does not exists: " + path);
        }
        Directory parent = (Directory) entries.get(entries.size() - 2);
        parent.deleteEntry(entries.get(entries.size() - 1));
    }

    public List<Entry> list(String path) {
        // list all the immediate children of the directory specified by the given paths
        assert path.endsWith("/");
        List<Entry> entries = resolve(path);
        if (entries.get(entries.size() - 1) == null) {
            throw new IllegalArgumentException("Directory does not exists: " + path);
        }
        Directory parent = (Directory) entries.get(entries.size() - 1);
        return parent.getContents();
    }

    public int count() {
        return root.numberOfFiles();
    }

}