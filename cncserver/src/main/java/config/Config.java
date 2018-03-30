package config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Config {
    HashMap<WorkpieceID, List<Task>> config = new HashMap<>();

    public Config(HashMap<WorkpieceID, List<Task>> config) {

        this.config = config;

    }

    public Config(File configFile) throws IOException {

        BufferedReader in = new BufferedReader(new FileReader("data.txt"));
        String line = null;
        for (int value = 0; value <= 5; value++) {
            line = in.readLine();
            List<Task> list = new ArrayList<>();
            String code = line.substring(0, 5);
            try {
                new WorkpieceID(code);
            } catch (Exception e) {
                continue;
            }
            String lineNoCode = line.substring(6);
            String[] tasks = lineNoCode.split("#");
            for (int i = 0; i < tasks.length; i++) {
                String[] singleTask = tasks[i].substring(2).split(",");
                if (tasks[i].startsWith("B")) {
                    for (int i1 = 0; i1 < singleTask.length; i1++) {
                        String[] taskpart = singleTask[i1].split(" ");
                        Drill drill = new Drill(
                                Integer.parseInt(taskpart[0]),
                                Integer.parseInt(taskpart[1]),
                                Integer.parseInt(taskpart[2]),
                                Integer.parseInt(taskpart[3]));
                        list.add(drill);
                    }

                }
                if (tasks[i].startsWith("F")) {
                    List<String> mill = new ArrayList<>();
                    for (int z = 0; z < singleTask.length; z++) {
                        mill.add(singleTask[z]);
                    }
                    list.add(new Mill(mill));
                }
            }
            config.put(new WorkpieceID(code), list);
        }

    }

    public HashMap<WorkpieceID, List<Task>> getConfig(){
        return new HashMap<>(config);
    }

    public void save() {

    }
        public List<Task> getTasks(WorkpieceID WpID) {
        List<Task> returntasks = new ArrayList<>();
        List<Task> test = config.get(WpID);
        return test;
    }
}

