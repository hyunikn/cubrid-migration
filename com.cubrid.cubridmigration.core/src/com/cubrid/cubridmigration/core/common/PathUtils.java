/*
 * Copyright (C) 2008 Search Solution Corporation.
 * Copyright (C) 2016 CUBRID Corporation.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *
 * - Redistributions of source code must retain the above copyright notice,
 *   this list of conditions and the following disclaimer.
 *
 * - Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * - Neither the name of the <ORGANIZATION> nor the names of its contributors
 *   may be used to endorse or promote products derived from this software without
 *   specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
 * BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA,
 * OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY
 * OF SUCH DAMAGE.
 *
 */
package com.cubrid.cubridmigration.core.common;

import com.cubrid.cubridmigration.core.engine.config.MigrationConfiguration;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;

/**
 * The path utility can get resource file from core jar and can get installation dir and can get
 * temp dir. It provides the directories which will be used in the application.
 *
 * <p>Note:the temp dir and installation must be installed when application start
 *
 * <p>Install path/ jdbc/ handlers/ workspace/ temp/ report/ scripts/ schemacache/ log/ history/
 *
 * @author pangqiren caoyilin
 * @version 1.0 - 2010-1-11 created by pangqiren caoyilin
 */
public final class PathUtils {
    // private static final Logger LOG = LogUtil.getLogger(PathUtils.class);
    private static String installLocation = null;
    private static String jdbcLibDir = null;
    private static String tempDir = null;
    private static String workspace = null;

    private static String cmtWorkspace = null;

    private static String logDir;
    private static String reportDir;
    private static String scriptDir;
    private static String historyDir;
    private static String schemaCacheDir;
    private static String errorsDir;
    private static String handlersDir;

    /**
     * make sure path exist
     *
     * @param parentFile File
     * @return boolean
     */
    public static boolean checkPathExist(File parentFile) {
        if (parentFile.exists()) {
            return true;
        }

        if (parentFile.mkdirs()) {
            return true;
        }

        return false;
    }

    /**
     * Checks if a directory is empty
     *
     * @param directory the directory to check
     * @return true if the directory is empty or does not contain any files, false otherwise
     */
    public static boolean checkPathEmpty(File directory) {
        if (directory.isDirectory()) {
            String[] files = directory.list();
            return files == null || files.length == 0;
        }
        return false;
    }

    /**
     * Create file
     *
     * @param file File
     * @throws IOException when error
     */
    public static void createFile(File file) throws IOException {
        File parentFile = file.getAbsoluteFile().getParentFile();
        if (!parentFile.exists() && !parentFile.mkdirs()) {
            throw new IOException("Create dictionary failed:" + parentFile.getParent());
        }
        if (!file.createNewFile()) {
            throw new IOException("Create file failed:" + file.getAbsolutePath());
        }
    }

    /**
     * Delete file
     *
     * @param file File
     */
    public static void deleteFile(File file) {
        if (!file.delete()) {
            // LOG.debug("Delete file failed:" + file.getName());
        }
    }

    /**
     * Extract file's extend name.
     *
     * @param fileName String
     * @return ext name
     */
    public static String extracFileExt(String fileName) {
        if (StringUtils.isBlank(fileName)) {
            return "";
        }
        int idx = fileName.lastIndexOf('.');
        if (idx < 0 || idx == fileName.length() - 1) {
            return "";
        }
        return fileName.substring(idx + 1);
    }

    /**
     * get Temp Dir
     *
     * @return String
     */
    public static String getBaseTempDir() {
        return tempDir;
    }

    public static String getCMTWorkspace() {
        return cmtWorkspace;
    }

    /**
     * get default Temporary Directory's full path
     *
     * @return String
     */
    public static String getDefaultBaseTempDir() {
        return getCMTWorkspace() + "temp" + File.separator;
    }

    /**
     * Get Error files directory
     *
     * @return Error files directory
     */
    public static String getErrorsDir() {
        return errorsDir;
    }

    /**
     * Retrieves the file size with KB format
     *
     * @param fsize file size
     * @return xxx,xxx KB
     */
    public static String getFileKBSize(long fsize) {
        if (fsize < 0) {
            throw new IllegalArgumentException("File size can't be nagetive.");
        }
        long size = fsize / 1000;
        long li = fsize % 1000;
        if (li > 0) {
            size++;
        }
        return NumberFormat.getInstance().format(size) + " KB";
    }

    /**
     * Retrieves the user defined handlers directory
     *
     * @return .../handlers/
     */
    public static String getHandlersDir() {
        return handlersDir;
    }

    /**
     * get file path of a URL
     *
     * @return String
     */
    public static String getInstallPath() {
        return installLocation;
    }

    /**
     * get JDBC lib directory
     *
     * @return String
     */
    public static String getJDBCLibDir() {
        return jdbcLibDir;
    }

    /**
     * format path to localhost path
     *
     * @param path String
     * @return String
     */
    public static String getLocalHostFilePath(final String path) {
        final String fileSeparator = System.getProperty("file.separator");
        return path.replace("\\", fileSeparator).replace("/", fileSeparator);
    }

    /**
     * Log4j directory
     *
     * @return Log4j directory
     */
    public static String getLogDir() {
        return logDir;
    }

    /**
     * getMonitorHistoryDir
     *
     * @return File
     */
    public static String getMonitorHistoryDir() {
        return historyDir;
    }

    /**
     * Retrieves the migration report files' directory
     *
     * @return directory of migration report files
     */
    public static String getReportDir() {
        return reportDir;
    }

    /**
     * Retrieves the migration report files' directory
     *
     * @return directory of migration report files
     */
    public static String getSchemaCacheDir() {
        return schemaCacheDir;
    }

    /**
     * Retrieves the directory where the migration application saved migration scripts.
     *
     * @return path of migration scripts
     */
    public static String getScriptDir() {
        return scriptDir;
    }

    /**
     * get file path of a URL
     *
     * @param url URL
     * @return String
     */
    public static String getURLFilePath(URL url) {
        return CUBRIDIOUtils.IS_OS_WINDOWS ? url.getPath().substring(1) : url.getPath();
    }

    public static String getWorkspace() {
        return workspace;
    }

    /**
     * Initialize the common paths to be used in the CMT. This method should be called when
     * application started or plugin started only once.
     */
    public static void initPaths() {
        initPaths(null, null, null);
    }

    /**
     * Initialize the common paths to be used in the CMT. This method should be called when
     * application started or plugin started only once.
     *
     * @param installDir String
     * @param tmpDir String
     * @param wsDir String
     */
    public static void initPaths(String installDir, String tmpDir, String wsDir) {
        String tid = installDir;
        if (StringUtils.isBlank(tid)) {
            tid = System.getProperty("user.dir");
        }
        if (StringUtils.isBlank(tid)) {
            throw new IllegalArgumentException("Install path is invalid:" + tid);
        }
        String urlFilePath = toCanonicalPath(tid);
        urlFilePath = urlFilePath == null ? "" : urlFilePath;
        urlFilePath =
                urlFilePath.endsWith(File.separator) ? urlFilePath : urlFilePath + File.separator;
        PathUtils.installLocation = urlFilePath;
        PathUtils.jdbcLibDir = PathUtils.installLocation + "jdbc" + File.separatorChar;

        if (StringUtils.isNotBlank(tmpDir)) {
            final File ftdir = new File(tmpDir);
            if (!ftdir.exists()) {
                ftdir.mkdirs();
            }
            tempDir = ftdir.getAbsolutePath();
            if (!tempDir.endsWith(File.separator)) {
                tempDir = tempDir + File.separator;
            }
        }
        setWorkspace(wsDir);
        String[] paths =
                new String[] {
                    jdbcLibDir,
                    cmtWorkspace,
                    logDir,
                    reportDir,
                    scriptDir,
                    historyDir,
                    schemaCacheDir,
                    errorsDir,
                    handlersDir,
                    tempDir
                };
        for (String pt : paths) {
            File file = new File(pt);
            if (!file.exists()) {
                file.mkdirs();
            }
        }
    }

    /**
     * Default KRB5 ticket cache file
     *
     * @return Default KRB5 ticket cache file
     */
    public static String getDefaultTicketFile() {
        return PathUtils.getUserHomeDir() + "krb5cc_" + System.getProperty("user.name");
    }

    /** @return getDefaultKrbConfigFile */
    public static String getDefaultKrbConfigFile() {
        return CUBRIDIOUtils.IS_OS_WINDOWS ? "c:\\Windows\\krb5.ini" : "/etc/krb5.conf";
    }

    /** @return getGSSLoginFile */
    public static String getGSSLoginFile() {
        return installLocation + "gsslogin.conf";
    }

    /**
     * Merge two path to one path
     *
     * @param path1 in the head
     * @param path2 in the back
     * @return full path
     */
    public static String mergePath(String path1, String path2) {
        String hp = path1 == null ? "" : path1;
        String hb = path2 == null ? "" : path2;

        String splider1 = "";
        if (hp.endsWith("/") || hp.endsWith("\\")) {
            splider1 = hp.substring(hp.length() - 1, hp.length());
            hp = hp.substring(0, hp.length() - 1);
        }
        if (hb.startsWith("/") || hb.startsWith("\\")) {
            splider1 = hb.substring(0, 1);
            hb = hb.substring(1, hb.length());
        }
        if (splider1.length() == 0) {
            splider1 = "/";
        }
        return hp + splider1 + hb;
    }

    /**
     * set temp directory
     *
     * @param dir String
     */
    public static void setBaseTempDir(final String dir) {
        if (StringUtils.isBlank(dir)) {
            return;
        }
        tempDir = dir.endsWith(File.separator) ? dir : (dir + File.separator);
    }

    /**
     * Set workspace directory
     *
     * @param ws String
     */
    public static void setWorkspace(String ws) {
        if (StringUtils.isBlank(ws)) {
            // Default work space directory
            PathUtils.workspace = PathUtils.installLocation + "workspace" + File.separator;
        } else {
            PathUtils.workspace = ws.endsWith(File.separator) ? ws : (ws + File.separator);
        }
        cmtWorkspace = workspace + "cmt" + File.separator;
        logDir = cmtWorkspace + "log" + File.separator;
        reportDir = cmtWorkspace + "report" + File.separator;
        scriptDir = cmtWorkspace + "script" + File.separator;
        historyDir = cmtWorkspace + "history" + File.separator;
        schemaCacheDir = cmtWorkspace + "schemacache" + File.separator;
        errorsDir = cmtWorkspace + "errors" + File.separator;
        handlersDir = getInstallPath() + "handlers" + File.separator;

        if (tempDir == null) {
            tempDir = cmtWorkspace + "temp" + File.separator;
        }
    }

    /**
     * Transfer file name to Canonical Path
     *
     * @param file String
     * @return CanonicalPath
     */
    public static String toCanonicalPath(String file) {
        try {
            return new File(file).getCanonicalPath();
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * Transform a string to a valid file name.
     *
     * @param str a string
     * @return a valid file name
     */
    public static String transStr2FileName(String str) {
        if (StringUtils.isBlank(str)) {
            throw new IllegalArgumentException("File name cant be empty.");
        }
        return str.replaceAll("[:|\\s]+", "_");
    }

    /**
     * Get User home directory
     *
     * @return User home directory
     */
    public static String getUserHomeDir() {
        String uh = System.getProperty("user.home");
        return uh.endsWith(File.separator) ? uh : uh + File.separator;
    }

    /**
     * Retrieves the file name without extend name: Exp: /home/xxx/file.xml >> /home/xxx/file
     *
     * @param fileName to be extracted
     * @return the name without extend name.
     */
    public static String getFileNameWithoutExtendName(String fileName) {
        if (fileName == null) {
            return null;
        }
        int lastDotPosition = fileName.lastIndexOf(".");
        if (lastDotPosition < 0) {
            return fileName;
        }
        if (lastDotPosition == 0) {
            return "";
        }
        return fileName.substring(0, lastDotPosition);
    }

    /** Clear temp directory. */
    public static void clearTempDir() {
        File file = new File(PathUtils.getBaseTempDir());
        File[] files = file.listFiles();
        if (files == null) {
            return;
        }
        for (File ff : files) {
            CUBRIDIOUtils.clearFileOrDir(ff);
        }
    }

    /** Change the script name part of the local file path */
    public static void changeLocalFilePath(MigrationConfiguration config) {
        String fileRootPath = config.getFileRepositroyPath();
        String oldName = config.getOldName();
        String newName = config.getName();

        if (oldName == null) {
            return;
        }

        // schema
        config.setTargetSchemaFileName(
                changeOldNameToNewName(
                        config.getTargetSchemaFileName(), fileRootPath, oldName, newName));

        // class
        config.setTargetTableFileName(
                changeOldNameToNewName(
                        config.getTargetTableFileName(), fileRootPath, oldName, newName));

        // vclass
        config.setTargetViewFileName(
                changeOldNameToNewName(
                        config.getTargetViewFileName(), fileRootPath, oldName, newName));

        // vclass_query_spec
        config.setTargetViewQuerySpecFileName(
                changeOldNameToNewName(
                        config.getTargetViewQuerySpecFileName(), fileRootPath, oldName, newName));

        // objects
        config.setTargetDataFileName(
                changeOldNameToNewName(
                        config.getTargetDataFileName(), fileRootPath, oldName, newName));

        // index
        config.setTargetIndexFileName(
                changeOldNameToNewName(
                        config.getTargetIndexFileName(), fileRootPath, oldName, newName));

        // pk
        config.setTargetPkFileName(
                changeOldNameToNewName(
                        config.getTargetPkFileName(), fileRootPath, oldName, newName));

        // fk
        config.setTargetFkFileName(
                changeOldNameToNewName(
                        config.getTargetFkFileName(), fileRootPath, oldName, newName));

        // serial
        config.setTargetSerialFileName(
                changeOldNameToNewName(
                        config.getTargetSerialFileName(), fileRootPath, oldName, newName));

        // synonym
        config.setTargetSynonymFileName(
                changeOldNameToNewName(
                        config.getTargetSynonymFileName(), fileRootPath, oldName, newName));

        // grant
        Map<String, Map<String, String>> newGrantFilePathMap = new HashMap<>();
        Map<String, Map<String, String>> grantFilePathMap = config.getTargetGrantFileName();
        for (String schemaName : grantFilePathMap.keySet()) {
            Map<String, String> grantFilePath = grantFilePathMap.get(schemaName);
            if (grantFilePath != null) {
                newGrantFilePathMap.put(
                        schemaName,
                        changeOldNameToNewName(grantFilePath, fileRootPath, oldName, newName));
            }
        }
        config.setTargetGrantFileName(newGrantFilePathMap);

        // updatestatistic
        config.setTargetUpdateStatisticFileName(
                changeOldNameToNewName(
                        config.getTargetUpdateStatisticFileName(), fileRootPath, oldName, newName));

        // info
        config.setTargetSchemaFileListName(
                changeOldNameToNewName(
                        config.getTargetSchemaFileListName(), fileRootPath, oldName, newName));

        // table data file
        Map<String, List<String>> newTableDataFilePath = new HashMap<>();
        config.getTargetTableDataFileName()
                .forEach(
                        (schemaName, tableDataFilePathList) -> {
                            newTableDataFilePath.put(
                                    schemaName,
                                    changeOldNameToNewName(
                                            tableDataFilePathList, fileRootPath, oldName, newName));
                        });
        config.setTargetTableDataFileName(newTableDataFilePath);
    }

    /** change directory path */
    private static List<String> changeOldNameToNewName(
            List<String> filePath, String fileRootPath, String oldName, String newName) {
        List<String> newFilePath = new ArrayList<String>();
        filePath.forEach(
                path -> {
                    String newPath = removeRootPath(path, fileRootPath).replace(oldName, newName);
                    newFilePath.add(addRootPath(newPath, fileRootPath));
                });
        return newFilePath;
    }

    /** change directory path */
    private static Map<String, String> changeOldNameToNewName(
            Map<String, String> filePath, String fileRootPath, String oldName, String newName) {
        Map<String, String> newFilePath = new HashMap<>();
        filePath.forEach(
                (schemaName, path) -> {
                    String newPath = removeRootPath(path, fileRootPath).replace(oldName, newName);
                    newFilePath.put(schemaName, addRootPath(newPath, fileRootPath));
                });
        return newFilePath;
    }

    /** add file root path */
    private static String addRootPath(String filePath, String fileRootPath) {
        if (filePath.startsWith(fileRootPath)) {
            return filePath;
        }
        return fileRootPath + filePath;
    }

    /** remove file root path */
    private static String removeRootPath(String fileFullPath, String fileRootPath) {
        if (fileFullPath.startsWith(fileRootPath)) {
            return fileFullPath.substring(fileRootPath.length());
        }
        return fileFullPath;
    }

    // Constructor
    private PathUtils() {
        // do nothing
    }
}
