package com.seven.mybatisplus.dto;

import java.util.Arrays;

/**
 * @className: ConfigDto
 * @author: wanyang
 * @date: 2025/6/18 11:33
 * @version: 1.0
 * @description: TODO
 */
public class ConfigDto {

    private Datasource datasource;

    private Generator generator;

    @Override
    public String toString() {
        return "ConfigDto{" +
                "datasource=" + datasource +
                ", generator=" + generator +
                '}';
    }

    public ConfigDto() {
    }

    public ConfigDto(Datasource datasource, Generator generator) {
        this.datasource = datasource;
        this.generator = generator;
    }

    public Datasource getDatasource() {
        return datasource;
    }

    public void setDatasource(Datasource datasource) {
        this.datasource = datasource;
    }

    public Generator getGenerator() {
        return generator;
    }

    public void setGenerator(Generator generator) {
        this.generator = generator;
    }

    public static class Datasource {

        private String url;

        private String username;

        private String password;

        private String driverClassName;

        public Datasource() {
        }

        public Datasource(String url, String username, String password, String driverClassName) {
            this.url = url;
            this.username = username;
            this.password = password;
            this.driverClassName = driverClassName;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getDriverClassName() {
            return driverClassName;
        }

        public void setDriverClassName(String driverClassName) {
            this.driverClassName = driverClassName;
        }

        @Override
        public String toString() {
            return "Datasource{" +
                    "url='" + url + '\'' +
                    ", username='" + username + '\'' +
                    ", password='" + password + '\'' +
                    ", driverClassName='" + driverClassName + '\'' +
                    '}';
        }
    }

    public static class Generator {

        private String outputDir;

        private String author;

        private String packageName;

        private String[] tableNames;

        public Generator() {
        }

        public Generator(String outputDir, String author, String packageName, String[] tableNames) {
            this.outputDir = outputDir;
            this.author = author;
            this.packageName = packageName;
            this.tableNames = tableNames;
        }

        public String getOutputDir() {
            return outputDir;
        }

        public void setOutputDir(String outputDir) {
            this.outputDir = outputDir;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getPackageName() {
            return packageName;
        }

        public void setPackageName(String packageName) {
            this.packageName = packageName;
        }

        public String[] getTableNames() {
            return tableNames;
        }

        public void setTableNames(String[] tableNames) {
            this.tableNames = tableNames;
        }

        @Override
        public String toString() {
            return "Generator{" +
                    "outputDir='" + outputDir + '\'' +
                    ", author='" + author + '\'' +
                    ", packageName='" + packageName + '\'' +
                    ", tableNames=" + Arrays.toString(tableNames) +
                    '}';
        }
    }
}
