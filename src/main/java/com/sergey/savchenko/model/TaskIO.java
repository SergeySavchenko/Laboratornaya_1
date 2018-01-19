package com.sergey.savchenko.model;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * class "TaskIO", defines methods for input and output list of tasks
 *
 * Created by SergeySavchenko on 10.11.2017.
 */
public class TaskIO {
    /**
     * method for writing list of tasks to byte stream
     *
     * @param tasks list of tasks
     * @param out byte stream
     */
    public static void write(TaskList tasks, OutputStream out) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(out)) {
            dos.writeInt(tasks.size());
            for (int i = 0; i < tasks.size(); i++) {
                dos.writeInt(tasks.getTask(i).getTitle().length());
                dos.writeUTF(tasks.getTask(i).getTitle());
                if (tasks.getTask(i).isActive()) {
                    dos.write(1);
                } else {
                    dos.write(0);
                }
                dos.writeInt(tasks.getTask(i).getRepeatInterval());
                if (tasks.getTask(i).isRepeated()) {
                    dos.writeLong(tasks.getTask(i).getStartTime().getTime());
                    dos.writeLong(tasks.getTask(i).getEndTime().getTime());
                } else {
                    dos.writeLong(tasks.getTask(i).getTime().getTime());
                }
            }
        } catch (IOException e) {
            throw new IOException("Error writing object");
        }
    }

    /**
     * method for getting list of tasks from byte stream
     *
     * @param tasks list of tasks
     * @param in byte stream
     */
    public static void read(TaskList tasks, InputStream in) throws IOException {
        Task task = null;
        int numberOfTasks = 0;
        String title = null;
        boolean active = false;
        int interval = 0;
        try (DataInputStream dis = new DataInputStream(in)) {
            numberOfTasks = dis.readInt();
            for (int i = 0; i < numberOfTasks; i++) {
                dis.readInt();
                title = dis.readUTF();
                active = (dis.readByte() == 1 ? true : false);
                interval = dis.readInt();
                if (interval != 0) {
                    task = new Task(title, new Date(dis.readLong()),
                            new Date(dis.readLong()), interval);
                } else {
                    task = new Task(title, new Date(dis.readLong()));
                }
                task.setActive(active);
                tasks.add(task);
            }
        } catch (IOException e) {
            throw new IOException("Error reading object");
        }
    }

    /**
     * method for writing list of tasks to file
     *
     * @param tasks list of tasks
     * @param file file on disk
     */
    public static void writeBinary(TaskList tasks, File file) throws IOException {
        try (OutputStream out = new FileOutputStream(file)) {
            TaskIO.write(tasks, out);
            out.flush();
        } catch (IOException e) {
            throw new IOException("Error writing object");
        }
    }

    /**
     * method for getting list of tasks from file
     *
     * @param tasks list of tasks
     * @param file file on disk
     */
    public static void readBinary(TaskList tasks, File file) throws IOException {
        try (InputStream inStream = new BufferedInputStream(
                new FileInputStream(file))) {
            TaskIO.read(tasks, inStream);
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("No file");
        } catch (IOException e) {
            throw new IOException("Error reading object");
        }
    }

    /**
     * method for writing list of tasks to character stream
     *
     * @param tasks list of tasks
     * @param out character stream
     */
    public static void write(TaskList tasks, Writer out) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(out)) {
            for (int i = 0; i < tasks.size(); i++) {
                if (i < (tasks.size() - 1)) {
                    writer.write(tasks.getTask(i).toString() + ";");
                    writer.newLine();
                } else {
                    writer.write(tasks.getTask(i).toString() + ".");
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            throw new IOException("Error writing characters");
        }
    }

    /**
     * method for getting list of tasks from character stream
     *
     * @param tasks list of tasks
     * @param in character stream
     */
    public static void read(TaskList tasks, Reader in) throws ParseException, IOException {
        try (BufferedReader reader = new BufferedReader(in)) {
            String line;
            while ((line = reader.readLine()) != null) {
                tasks.add(doParse(line));
            }
        } catch (IOException e) {
            throw new IOException("Error reading characters");
        }
    }

    /**
     * method for writing list of tasks to file
     *
     * @param tasks list of tasks
     * @param file file on disk
     */
    public static void writeText(TaskList tasks, File file) throws IOException {
        try (Writer writer = new FileWriter(file)) {
            write(tasks, writer);
        } catch (IOException e) {
            throw new IOException("Error writing characters");
        }
    }

    /**
     * method for getting list of tasks from file
     *
     * @param tasks list of tasks
     * @param file file on disk
     */
    public static void readText(TaskList tasks, File file) throws ParseException, IOException {
        try (Reader reader = new FileReader(file)) {
            read(tasks, reader);
        } catch (IOException e) {
            throw new IOException("Error reading characters");
        }
    }

    /**
     * method for interpreting a string to the task
     *
     * @param line text string
     * @return task
     */
    private static Task doParse(String line) throws ParseException {
        String title = "";
        int interval = 0;
        Date time = new Date();
        Date start = new Date();
        Date end = new Date();
        Pattern pattern = Pattern.compile("\".+\"");
        Matcher matcher = pattern.matcher(line);
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        if (matcher.find()) {
            title = line.substring(matcher.start() + 1, matcher.end() - 1);
        }
        pattern = Pattern.compile("every \\[");
        matcher = pattern.matcher(line);
        if (matcher.find()) {
            String stringInterval = line.substring(matcher.end()
                    , line.length());
            pattern = Pattern.compile(" second");
            matcher = pattern.matcher(stringInterval);
            if (matcher.find()) {
                interval = interval + Integer.parseInt(stringInterval.
                        split(" second")[0].split(", ")[stringInterval.
                        split(" second")[0].split(", ").length - 1]);
            }
            pattern = Pattern.compile(" minute");
            matcher = pattern.matcher(stringInterval);
            if (matcher.find()) {
                interval = interval + Integer.parseInt(stringInterval.
                        split(" minute")[0].
                        split(", ")[stringInterval.split(" minute")[0].
                        split(", ").length - 1]) * 60;
            }
            pattern = Pattern.compile(" hour");
            matcher = pattern.matcher(stringInterval);
            if (matcher.find()) {
                interval = interval + Integer.parseInt(stringInterval.
                        split(" hour")[0].
                        split(", ")[stringInterval.split(" hour")[0].
                        split(", ").length - 1]) * 60 * 60;
            }
            pattern = Pattern.compile(" day");
            matcher = pattern.matcher(stringInterval);
            if (matcher.find()) {
                interval = interval + Integer.parseInt(stringInterval.
                        split(" day")[0].
                        split(", ")[stringInterval.split(" day")[0].
                        split(", ").length - 1]) * 60 * 60 * 24;
            }
        }
        pattern = Pattern.compile("from");
        matcher = pattern.matcher(line);
        if (matcher.find()) {
            time = new Date(0);
            pattern = Pattern.compile("\\[[^to]+\\]");
            matcher = pattern.matcher(line);
            int i = 0;
            while (matcher.find()) {
                if (i == 0) {
                    start = sf.parse(line.substring(matcher.start()
                            + 1, matcher.end() - 1));
                    i++;
                } else {
                    end = sf.parse(line.substring(matcher.start()
                            + 1, matcher.end() - 1));
                }
            }
            pattern = Pattern.compile(" inactive");
            matcher = pattern.matcher(line);
            Task task = new Task(title, start, end, (int) interval);
            if (!matcher.find()) {
                task.setActive(true);
            }
            return task;
        } else {
            pattern = Pattern.compile("\\[.+\\]");
            matcher = pattern.matcher(line);
            if (matcher.find()) {
                time = sf.parse(line.substring(matcher.start() + 1
                        , matcher.end() - 1));
            }
            pattern = Pattern.compile(" inactive");
            matcher = pattern.matcher(line);
            Task task = new Task(title, time);
            if (!matcher.find()) {
                task.setActive(true);
            }
            return task;
        }
    }
}
